package com.etwicaksono.iscan.ui.activities.scannerToko

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.databinding.ActivityScannerTokoBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.scannerProduk.ScannerProdukActivity
import com.etwicaksono.iscan.utils.UserPref
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.zxing.BarcodeFormat
import org.json.JSONArray

class ScannerTokoActivity : BaseActivity() {

    private lateinit var binding: ActivityScannerTokoBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: ScannerTokoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerTokoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBarcodeValue.text = getString(R.string.empty_barcode_value)

        init()
        scanner()
        codeScanner.startPreview()
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this ,
            ViewModelProvider.NewInstanceFactory()
        )[ScannerTokoViewModel::class.java]
        viewModel.getState().observer(this , {
            handlerUIState(it)
        })

    }

    private fun handlerUIState(it: ScannerTokoState?) {
        when (it) {
            is ScannerTokoState.IsLoading -> showLoading(binding.root , it.state)
            is ScannerTokoState.IsSuccess -> isSuccess(it.status , it.msg , it.data)
            is ScannerTokoState.Error -> showToast(it.err , false , isShort = false)
        }
    }

    private fun isSuccess(status: Int? , msg: String? , data: TokoEntity?) {
        when (status) {
            404 -> {
                showToast(msg , false)
                codeScanner.startPreview()
            }
            200 -> {
                UserPref(this).getValues("recent_store")?.let {
                    val id = data?.id
                    val gson = GsonBuilder().create()
                    var recentToko = gson.fromJson<ArrayList<String>>(it ,
                        object : TypeToken<ArrayList<String>>() {}.type)

                    if (recentToko.isNullOrEmpty())recentToko= arrayListOf()

                    val index = recentToko.indexOf(id)
                    if (index >= 0) {
                        recentToko.removeAt(index)
                    }

                    if (id != null) {
                        recentToko.add(id)
                    }
                    UserPref(this).setValue("recent_store" , JSONArray(recentToko).toString())
                }
                startActivity(Intent(this , ScannerProdukActivity::class.java).apply {
                    putExtra(ScannerProdukActivity.EXTRAS_DATA , data)
                })
            }
        }
    }

    private fun scanner() {
        codeScanner = CodeScanner(this , binding.scannerView)

//        Parameters default
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = listOf(BarcodeFormat.CODE_128)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

//        callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                binding.tvBarcodeValue.text = it.text
                viewModel.scanToko(it.text)
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                showToast("Scanner error : ${it.message}" , false)
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onBackPressed() {
        finish()
    }

}