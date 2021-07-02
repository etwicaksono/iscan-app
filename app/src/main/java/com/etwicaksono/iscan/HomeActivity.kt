package com.etwicaksono.iscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.etwicaksono.iscan.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanToko.setOnClickListener({
            var intentScannerToko = Intent(this@HomeActivity,ScannerTokoActivity::class.java)
            startActivity(intentScannerToko)
        })


    }
}