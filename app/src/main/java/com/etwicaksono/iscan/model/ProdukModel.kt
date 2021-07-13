package com.etwicaksono.iscan.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProdukModel : Serializable {
    @field:SerializedName("id")
    val id: String? = null

    @field:SerializedName("id_toko")
    val id_toko: String? = null

    @field:SerializedName("kode")
    val kode: String? = null

    @field:SerializedName("nama")
    val nama: String? = null

    @field:SerializedName("harga")
    val harga: String? = null

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null

    @field:SerializedName("foto")
    val foto: String? = null

}