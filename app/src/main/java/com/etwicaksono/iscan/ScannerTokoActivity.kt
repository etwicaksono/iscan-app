package com.etwicaksono.iscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityScannerTokoBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScannerTokoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerTokoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerTokoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        IntentIntegrator(this@ScannerTokoActivity).initiateScan()
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