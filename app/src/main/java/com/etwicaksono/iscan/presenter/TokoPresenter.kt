package com.etwicaksono.iscan.presenter

import android.util.Log
import com.etwicaksono.iscan.api.NetworkConfig
import com.etwicaksono.iscan.api.WrappedResponse
import com.etwicaksono.iscan.model.TokoModel
import com.etwicaksono.iscan.views.ITokoView
import retrofit2.Call
import retrofit2.Response

class TokoPresenter (val iTokoView: ITokoView, val type:String,val barcode:String?){

//    fungsi get data
    fun getData(){
        NetworkConfig.getService().getDataToko(type,barcode)
            .enqueue(object :retrofit2.Callback<WrappedResponse<TokoModel>>{
                override fun onResponse(
                    call: Call<WrappedResponse<TokoModel>>,
                    response: Response<WrappedResponse<TokoModel>>
                ) {
                    if (response.isSuccessful){
                        val status = response.body()?.status
                        if(status==200){
                            val data=response.body()?.data
                            iTokoView.onSuccessGet(data)
                        }
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<TokoModel>>, t: Throwable) {
                    iTokoView.onFailedGet(t.localizedMessage)
                    Log.d("Error","Error data toko")
                }

            })
    }
}