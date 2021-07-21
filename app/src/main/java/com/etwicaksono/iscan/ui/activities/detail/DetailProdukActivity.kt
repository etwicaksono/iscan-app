package com.etwicaksono.iscan.ui.activities.detail

import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityDetailProdukBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class DetailProdukActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun generate(nominal: String): String {
        val local = Locale("id" , "ID")
        val cursIndo = NumberFormat.getCurrencyInstance(local) as DecimalFormat
        val symbol = Currency.getInstance(local).getSymbol(local)
        cursIndo.positivePrefix = "$symbol. "

        return cursIndo.format(nominal.toDouble())
    }

}