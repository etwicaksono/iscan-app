package com.etwicaksono.iscan.ui.activities.scannerToko

import androidx.lifecycle.ViewModel
import com.etwicaksono.iscan.api.ApiConfig
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ScannerTokoViewModel:ViewModel() {
    private var state: SingleLiveEvent<ScannerState> = SingleLiveEvent()
    private var api = ApiConfig.instance()

    fun scanToko(barcode: String?) {
        state.value = ScannerState.IsLoading(true)
        CompositeDisposable().add(
            api.getTokoByBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> state.value =
                            ScannerState.IsSuccess(it.status , it.message , it.data)
                        404 -> state.value =
                            ScannerState.IsSuccess(it.status , it.message , it.data)
                        else -> state.value = ScannerState.Error(it.message)
                    }
                    state.value = ScannerState.IsLoading()
                } , {
                    state.value = ScannerState.Error(it.message)
                    state.value = ScannerState.IsLoading()
                })
        )
    }

    fun getState() = state

}

sealed class ScannerState() {
    data class IsLoading(var state: Boolean = false) : ScannerState()
    data class IsSuccess(var status: Int? , var msg: String? , var data: TokoEntity?) :
        ScannerState()

    data class Error(var err: String?) : ScannerState()

}
