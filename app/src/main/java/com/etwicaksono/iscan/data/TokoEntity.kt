package com.etwicaksono.iscan.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokoEntity(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,
):Parcelable