package com.etwicaksono.iscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnScannerToko:Button = findViewById(R.id.btn_scan_toko)
        btnScannerToko.setOnClickListener({
            var intentScannerToko = Intent(this@HomeActivity,ScannerTokoActivity::class.java)
            startActivity(intentScannerToko)
        })
    }
}