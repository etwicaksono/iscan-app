package com.etwicaksono.iscan.views

import com.etwicaksono.iscan.data.TokoEntity

interface ITokoView {
//    untuk get data
    fun onSuccessGet(data:TokoEntity?)
    fun onFailedGet(msg:String)
}