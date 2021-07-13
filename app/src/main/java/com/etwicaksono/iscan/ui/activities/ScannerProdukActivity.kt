package com.etwicaksono.iscan.ui.activities

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.databinding.ActivityScannerProdukBinding
import com.etwicaksono.iscan.model.ProdukModel
import com.etwicaksono.iscan.presenter.ProdukPresenter
import com.etwicaksono.iscan.utils.Preferences
import com.etwicaksono.iscan.views.ILoadingView
import com.etwicaksono.iscan.views.IProdukView
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import java.util.*

class ScannerProdukActivity : AppCompatActivity(), IProdukView, ILoadingView {

    private lateinit var preferences: Preferences

    private lateinit var binding: ActivityScannerProdukBinding
    private lateinit var captureManager: CaptureManager
    private var scanContinuousState: Boolean = false
    private lateinit var scanContinuousBG: Drawable
    lateinit var beepManager: BeepManager
    private var lastScan = Date()
    private var torchState: Boolean = false
    private lateinit var presenter: ProdukPresenter
    private var progressDialog: ProgressDialog? = null
    private var hasResult: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences(this)

        binding.tvNamaToko.text = preferences.getValues("nama_toko")

        val barcodeView = binding.barcodeScanner
        val btnScanContinuous = binding.btnReset
        val tvBarcodeValue = binding.tvBarcodeValue

        progressDialog = ProgressDialog(this)

        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        beepManager = BeepManager(this)
        beepManager.isVibrateEnabled = true

        scanContinuousBG = btnScanContinuous.background

        var callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    val current = Date()
                    val diff = current.time - lastScan.time
                    if (diff >= 1000) {
                        tvBarcodeValue.text = it.text
                        lastScan = current
                        beepManager.playBeepSoundAndVibrate()
                        animateBackground()

//                        cek database
                        presenter = ProdukPresenter(
                            this@ScannerProdukActivity,
                            "single",
                            preferences?.getValues("id_toko"),
                            it.text,
                            this@ScannerProdukActivity
                        )
                        presenter.getData()
                    }
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            }
        }

        if (!scanContinuousState) {
            scanContinuousState = !scanContinuousState
            tvBarcodeValue.text = getString(R.string.scanning)
            barcodeView.decodeContinuous(callback)
        } else {
            scanContinuousState = !scanContinuousState
            barcodeView.barcodeView.stopDecoding()
        }

        binding.btnTorch.setOnClickListener {
            if (torchState) {
                torchState = false
                barcodeView.setTorchOff()
                it.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_flash_off_24))
            } else {
                torchState = true
                barcodeView.setTorchOn()
                it.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_flash_on_24))

            }
        }
    }

    private fun animateBackground() {
        val colorFrom = resources.getColor(R.color.colorAccent)
        val colorTo = resources.getColor(R.color.colorPrimary)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 250

        colorAnimation.addUpdateListener { animator ->
            binding.tvBarcodeValue.setBackgroundColor(
                animator.animatedValue as Int
            )
        }
        colorAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    override fun onSuccessGet(data: ProdukModel?) {
        val intentDetailProduk =
            Intent(this@ScannerProdukActivity, DetailProdukActivity::class.java)

        if (data != null) {
            intentDetailProduk.putExtra(DetailProdukActivity.NAMA_PRODUK,data.nama)
            intentDetailProduk.putExtra(DetailProdukActivity.HARGA_PRODUK,data.harga)
            intentDetailProduk.putExtra(DetailProdukActivity.BARCODE_PRODUK,data.kode)
            intentDetailProduk.putExtra(DetailProdukActivity.DESKRIPSI_PRODUK,data.deskripsi)
            intentDetailProduk.putExtra(DetailProdukActivity.FOTO_PRODUK,data.foto)
        }

        if (!hasResult) {
            hasResult = true
            startActivity(intentDetailProduk)
        }
    }

    override fun onFailedGet(msg: String) {
        Toast.makeText(this, "Error : $msg", Toast.LENGTH_LONG).show()
        Log.d("Error", "Error data produk :$msg")
    }

    override fun isLoading() {
        progressDialog?.let {
            it.setMessage(getText(R.string.please_wait))
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }
}