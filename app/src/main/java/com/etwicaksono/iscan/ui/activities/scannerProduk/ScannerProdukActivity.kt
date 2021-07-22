package com.etwicaksono.iscan.ui.activities.scannerProduk

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.data.ProdukEntity
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.databinding.ActivityScannerProdukBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.detail.DetailProdukActivity
import com.etwicaksono.iscan.ui.activities.home.HomeActivity
import com.google.zxing.BarcodeFormat

class ScannerProdukActivity : BaseActivity() {

    private lateinit var binding: ActivityScannerProdukBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: ScannerProdukViewModel
    private var datatoko: TokoEntity? = null

    companion object {
        const val EXTRAS_DATA = "extras_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBarcodeValue.text = getText(R.string.empty_barcode_value)

        datatoko = intent.getParcelableExtra(EXTRAS_DATA)
        binding.tvNamaToko.text = datatoko?.nama

        init()
        scanner()
        codeScanner.startPreview()
    }


    private fun init() {
        viewModel = ViewModelProvider(
            this ,
            ViewModelProvider.NewInstanceFactory()
        )[ScannerProdukViewModel::class.java]
        viewModel.getState().observer(this , {
            handlerUIState(it)
        })
    }

    private fun handlerUIState(it: ScannerProdukState?) {
        when (it) {
            is ScannerProdukState.IsLoading -> showLoading(binding.root , it.state)
            is ScannerProdukState.IsSuccess -> isSuccess(it.status , it.msg , it.data)
            is ScannerProdukState.Error -> showToast(it.err , false , isShort = false)
        }
    }

    private fun isSuccess(status: Int? , msg: String? , data: ProdukEntity?) {
        when (status) {
            404 -> {
                showToast(msg , false)
                codeScanner.startPreview()
            }
            200 -> {
                startActivity(Intent(this , DetailProdukActivity::class.java).apply {
                    putExtra(DetailProdukActivity.EXTRAS_DATA , data)
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
                viewModel.scanProduk(datatoko?.id , it.text)
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


    override fun onBackPressed() {
        startActivity(Intent(this , HomeActivity::class.java))
        finish()
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}