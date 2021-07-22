package com.etwicaksono.iscan.data.responses

import com.google.gson.annotations.SerializedName

data class WrappedResponses<T>(
    @SerializedName("message") var message: String? = null ,
    @SerializedName("status") var status: Int? = null ,
    @SerializedName("data") var data: T? = null ,

    )
