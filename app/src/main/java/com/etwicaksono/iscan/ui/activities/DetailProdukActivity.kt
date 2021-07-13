package com.etwicaksono.iscan.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwicaksono.iscan.databinding.ActivityDetailProdukBinding
import java.text.NumberFormat
import java.util.*

class DetailProdukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProdukBinding

    companion object {
        const val NAMA_PRODUK = "NAMA_PRODUK"
        const val HARGA_PRODUK = "HARGA_PRODUK"
        const val BARCODE_PRODUK = "BARCODE_PRODUK"
        const val DESKRIPSI_PRODUK = "DESKRIPSI_PRODUK"
        const val FOTO_PRODUK = "FOTO_PRODUK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val harga = intent.getStringExtra(HARGA_PRODUK)
        binding.tvHargaProduk.text = harga
        binding.tvBarcodeValue.text = intent.getStringExtra(BARCODE_PRODUK)
        binding.tvNamaProduk.text = intent.getStringExtra(NAMA_PRODUK)
        binding.tvDetailProduk.text = intent.getStringExtra(DESKRIPSI_PRODUK)

        binding.btnConfirm.setOnClickListener{
            val intent = Intent(this@DetailProdukActivity,ScannerProdukActivity::class.java)
            startActivity(intent)
        }

        binding.btnReset.setOnClickListener{
            val intent=Intent(this@DetailProdukActivity,HomeActivity::class.java)
            startActivity(intent)
        }

    }

}