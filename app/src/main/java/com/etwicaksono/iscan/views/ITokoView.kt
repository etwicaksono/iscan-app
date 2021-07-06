package com.etwicaksono.iscan.views

import com.etwicaksono.iscan.model.TokoModel

interface ITokoView {
//    untuk get data
    fun onSuccessGet(data:TokoModel?)
    fun onFailedGet(msg:String)
}