package com.etwicaksono.iscan.api

import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.data.responses.WrappedListResponses
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    //    GET RECENT TOKO
    @GET("toko")
    fun getRecentToko(
        @Query("list_toko") listToko: String? ,
        @Query("type") type: String = "some" ,
    ): Observable<WrappedListResponses<TokoEntity>>

}
