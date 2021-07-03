package com.etwicaksono.iscan.ui.activities

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.databinding.ActivityScannerTokoBinding
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import java.util.*

class ScannerTokoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerTokoBinding
    private lateinit var captureManager: CaptureManager
    private var scanContinuousState: Boolean = false
    private lateinit var scanContinuousBG: Drawable
    lateinit var beepManager: BeepManager
    private var lastScan = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerTokoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcodeView = binding.barcodeScanner
        val btnScanContinuous = binding.btnReset
        val tvBarcodeValue = binding.tvBarcodeValue

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
                    }
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            }
        }

        fun startScan() {
            if (!scanContinuousState) {
                scanContinuousState = !scanContinuousState
                tvBarcodeValue.text = "Scanning...."
                barcodeView.decodeContinuous(callback)
            } else {
                scanContinuousState = !scanContinuousState
                barcodeView.barcodeView.stopDecoding()
            }
        }

        btnScanContinuous.setOnClickListener(View.OnClickListener {
            tvBarcodeValue.text = "Scanning...."
        })

        startScan()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents != null) {
                binding.tvBarcodeValue.text = result.contents
            } else {
                binding.tvBarcodeValue.text = "....................."
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}