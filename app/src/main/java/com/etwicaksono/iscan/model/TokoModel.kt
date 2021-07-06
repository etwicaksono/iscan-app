package com.etwicaksono.iscan.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TokoModel : Serializable {
    @field:SerializedName("id")
    val id: String? = null

    @field:SerializedName("kode")
    val kode: String? = null

    @field:SerializedName("nama")
    val nama: String? = null

    @field:SerializedName("alamat")
    val alamat: String? = null

    @field:SerializedName("foto")
    val foto: String? = null

}