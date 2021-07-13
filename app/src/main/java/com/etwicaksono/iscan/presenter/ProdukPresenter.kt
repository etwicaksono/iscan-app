package com.etwicaksono.iscan.presenter

import android.util.Log
import com.etwicaksono.iscan.api.NetworkConfig
import com.etwicaksono.iscan.api.WrappedResponse
import com.etwicaksono.iscan.model.ProdukModel
import com.etwicaksono.iscan.model.TokoModel
import com.etwicaksono.iscan.views.ILoadingView
import com.etwicaksono.iscan.views.IProdukView
import com.etwicaksono.iscan.views.ITokoView
import retrofit2.Call
import retrofit2.Response

class ProdukPresenter(
    val iProdukView: IProdukView,
    val type: String,
    val id_toko: String?,
    private val barcode: String?,
    private val loading: ILoadingView
) {

    //    fungsi get data
    fun getData() {
        loading.isLoading()
        NetworkConfig.getProdukService().getDataProduk(type, id_toko, barcode)
            .enqueue(object : retrofit2.Callback<WrappedResponse<ProdukModel>> {
                override fun onResponse(
                    call: Call<WrappedResponse<ProdukModel>>,
                    response: Response<WrappedResponse<ProdukModel>>
                ) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status
                        if (status == 200) {
                            val data = response.body()?.data
                            iProdukView.onSuccessGet(data)
                        }
                        loading.hideLoading()
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<ProdukModel>>, t: Throwable) {
                    iProdukView.onFailedGet(t.localizedMessage)
                    Log.d("Error", "Error data toko")
                    loading.hideLoading()
                }

            })
    }
}