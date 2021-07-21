package com.etwicaksono.iscan.ui.activities.scannerProduk

import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityScannerProdukBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.utils.UserPref

class ScannerProdukActivity : BaseActivity(){

    private lateinit var userPref: UserPref
    private lateinit var binding: ActivityScannerProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPref = UserPref(this)
    }

}