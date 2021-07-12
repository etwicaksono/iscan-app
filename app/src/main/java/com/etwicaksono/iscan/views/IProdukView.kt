package com.etwicaksono.iscan.views

import android.content.Context
import android.widget.Toast
import com.etwicaksono.iscan.model.ProdukModel
import com.etwicaksono.iscan.model.TokoModel

interface IProdukView {
//    untuk get data
    fun onSuccessGet(data:ProdukModel?)
    fun onFailedGet(msg:String)
}