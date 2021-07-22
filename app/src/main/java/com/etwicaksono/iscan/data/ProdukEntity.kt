package com.etwicaksono.iscan.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ProdukEntity(
    @field:SerializedName("id")
    val id: String? = null ,

    @field:SerializedName("id_toko")
    val id_toko: String? = null ,

    @field:SerializedName("kode")
    val kode: String? = null ,

    @field:SerializedName("nama")
    val nama: String? = null ,

    @field:SerializedName("harga")
    val harga: String? = null ,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null ,

    @field:SerializedName("foto")
    val foto: String? = null ,

    ):Parcelable