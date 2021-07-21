package com.etwicaksono.iscan.views

import com.etwicaksono.iscan.data.ProdukEntity

interface IProdukView {
//    untuk get data
    fun onSuccessGet(data:ProdukEntity?)
    fun onFailedGet(msg:String)
}