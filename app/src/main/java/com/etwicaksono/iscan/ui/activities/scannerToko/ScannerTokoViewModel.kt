package com.etwicaksono.iscan.ui.activities.scannerToko

import androidx.lifecycle.ViewModel
import com.etwicaksono.iscan.api.ApiConfig
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ScannerTokoViewModel : ViewModel() {
    private var tokoState: SingleLiveEvent<ScannerTokoState> = SingleLiveEvent()
    private var api = ApiConfig.instance()

    fun scanToko(barcode: String?) {
        tokoState.value = ScannerTokoState.IsLoading(true)
        CompositeDisposable().add(
            api.getTokoByBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> tokoState.value =
                            ScannerTokoState.IsSuccess(it.status , it.message , it.data)
                        404 -> tokoState.value =
                            ScannerTokoState.IsSuccess(it.status , it.message , it.data)
                        else -> tokoState.value = ScannerTokoState.Error(it.message)
                    }
                    tokoState.value = ScannerTokoState.IsLoading()
                } , {
                    tokoState.value = ScannerTokoState.Error(it.message)
                    tokoState.value = ScannerTokoState.IsLoading()
                })
        )
    }

    fun getState() = tokoState

}

sealed class ScannerTokoState() {
    data class IsLoading(var state: Boolean = false) : ScannerTokoState()
    data class IsSuccess(var status: Int? , var msg: String? , var data: TokoEntity?) :
        ScannerTokoState()

    data class Error(var err: String?) : ScannerTokoState()

}
