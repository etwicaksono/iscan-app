package com.etwicaksono.iscan.ui.activities.scannerProduk

import androidx.lifecycle.ViewModel
import com.etwicaksono.iscan.api.ApiConfig
import com.etwicaksono.iscan.data.ProdukEntity
import com.etwicaksono.iscan.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ScannerProdukViewModel : ViewModel() {
    private var produkState: SingleLiveEvent<ScannerProdukState> = SingleLiveEvent()
    private var api = ApiConfig.instance()

    fun scanProduk(id_toko: String? , barcode: String?) {
        produkState.value = ScannerProdukState.IsLoading(true)
        CompositeDisposable().add(
            api.getProdukByBarcode(id_toko , barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when(it.status){
                        200->produkState.value=ScannerProdukState.IsSuccess(it.status,it.message,it.data)
                        404->produkState.value=ScannerProdukState.IsSuccess(it.status,it.message,it.data)
                        else->
                            produkState.value=ScannerProdukState.Error(it.message)
                    }
                    produkState.value=ScannerProdukState.IsLoading()
                },{
                    produkState.value=ScannerProdukState.Error(it.message)
                    produkState.value=ScannerProdukState.IsLoading()
                })
        )
    }

    fun getState()=produkState

}

sealed class ScannerProdukState {
    data class IsLoading(var state: Boolean = false) : ScannerProdukState()
    data class IsSuccess(var status: Int? , var msg: String? , var data: ProdukEntity?) :
        ScannerProdukState()

    data class Error(var err: String?) : ScannerProdukState()
}
