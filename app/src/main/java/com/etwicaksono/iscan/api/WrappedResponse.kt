package com.etwicaksono.iscan.api

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T> (
    @SerializedName("status") var status:Int?=null,
    @SerializedName("message") var message:String?=null,
    @SerializedName("data") var data:T?=null,
)