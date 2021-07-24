package com.etwicaksono.iscan.ui.activities.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.data.ProdukEntity
import com.etwicaksono.iscan.databinding.ActivityDetailProdukBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.home.HomeActivity
import com.viewpagerindicator.CirclePageIndicator
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailProdukActivity : BaseActivity() , View.OnClickListener {

    private lateinit var binding: ActivityDetailProdukBinding
    private var dataProduk: ProdukEntity? = null
    private var viewPager: ViewPager? = null
    private var indicator: CirclePageIndicator? = null
    private var currentPage = 0

    companion object {
        const val EXTRAS_DATA = "extras_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        dataProduk = intent.getParcelableExtra(EXTRAS_DATA)
        binding.tvHargaProduk.text = dataProduk?.harga?.let { generate(it) }
        binding.tvBarcodeValue.text = dataProduk?.kode
        binding.tvNamaProduk.text = dataProduk?.nama
        binding.tvDetailProduk.text = dataProduk?.deskripsi

        binding.btnConfirm.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)

        viewPager = binding.vpFoto
        indicator = binding.indicator

        createImageSlider(dataProduk!!.foto)
    }

    private fun createImageSlider(foto: ArrayList<String?>?) {
        viewPager?.adapter = SliderAdapter(this , foto)
        indicator?.setViewPager(viewPager)
        val density = resources.displayMetrics.density

//        set circle indicator radius
        indicator?.radius = 5 * density

//        Pager listener over indicator
        indicator?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
//                code
            }

            override fun onPageScrolled(
                position: Int ,
                positionOffset: Float ,
                positionOffsetPixels: Int
            ) {
//                code
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })

    }

    private fun generate(nominal: String): String {
        val local = Locale("id" , "ID")
        val cursIndo = NumberFormat.getCurrencyInstance(local) as DecimalFormat
        val symbol = Currency.getInstance(local).getSymbol(local)
        cursIndo.positivePrefix = "$symbol. "

        return cursIndo.format(nominal.toDouble())
    }

    override fun onBackPressed() {
        super.onBackPressed();
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_confirm -> finish()
            R.id.btn_reset -> {
                startActivity(Intent(this , HomeActivity::class.java))
                finish()
            }
        }
    }

}