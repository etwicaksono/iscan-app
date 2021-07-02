package com.etwicaksono.iscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityScannerProdukBinding

class ScannerProdukActivity : AppCompatActivity() {

    private lateinit var binding:ActivityScannerProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityScannerProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}