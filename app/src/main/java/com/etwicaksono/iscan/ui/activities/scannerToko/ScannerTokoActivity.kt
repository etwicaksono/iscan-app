package com.etwicaksono.iscan.ui.activities.scannerToko

import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityScannerTokoBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.utils.UserPref

class ScannerTokoActivity : BaseActivity() {

    private lateinit var binding: ActivityScannerTokoBinding
    lateinit var userPref: UserPref

    companion object {
        const val EXTRAS_DATA = "extras_data"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerTokoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPref = UserPref(this)

    }
}