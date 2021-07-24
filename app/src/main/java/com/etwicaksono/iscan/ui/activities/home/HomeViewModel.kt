package com.etwicaksono.iscan.ui.activities.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etwicaksono.iscan.api.ApiConfig
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel : ViewModel() {
    private var toko = MutableLiveData<List<TokoEntity>>()
    private var state: SingleLiveEvent<HomeState> = SingleLiveEvent()
    private var api = ApiConfig.instance()

    fun getRecentToko(listToko: String?) {
        state.value = HomeState.IsLoading(true)
        CompositeDisposable().add(
            api.getRecentToko(listToko)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> toko.postValue(it.data)
                        else -> state.value = HomeState.Error(it.message)
                    }
                    state.value = HomeState.IsLoading()
                } , {
                    state.value = HomeState.Error(it.message)
                    state.value = HomeState.IsLoading()
                })
        )
    }

    fun updateScanCount(idToko: String?) {
        state.value = HomeState.IsLoading(true)
        CompositeDisposable().add(
            api.updateScanCount(idToko).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (it.status != 200) state.value = HomeState.Error(it.message)
                    state.value = HomeState.IsLoading()
                } , {
                    state.value = HomeState.Error(it.message)
                    state.value = HomeState.IsLoading()
                })
        )
    }

    fun getToko() = toko
    fun getState() = state

}

sealed class HomeState() {
    data class IsLoading(var state: Boolean = false) : HomeState()
    data class Error(var err: String?) : HomeState()
}
