package com.etwicaksono.iscan.api

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null
)