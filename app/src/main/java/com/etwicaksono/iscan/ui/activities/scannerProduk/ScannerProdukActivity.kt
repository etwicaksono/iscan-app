package com.etwicaksono.iscan.ui.activities.scannerProduk

import android.content.Intent
import android.os.Bundle
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.databinding.ActivityScannerProdukBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.home.HomeActivity
import com.etwicaksono.iscan.utils.UserPref

class ScannerProdukActivity : BaseActivity() {

    private lateinit var binding: ActivityScannerProdukBinding

    companion object {
        const val EXTRAS_DATA = "extras_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extra = intent.getParcelableExtra<TokoEntity>(EXTRAS_DATA)
        binding.tvNamaToko.text = extra?.nama
    }

    override fun onBackPressed() {
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

}