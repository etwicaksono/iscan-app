package com.etwicaksono.iscan.api

import com.etwicaksono.iscan.data.ProdukEntity
import com.etwicaksono.iscan.data.TokoEntity
import com.etwicaksono.iscan.data.responses.WrappedListResponses
import com.etwicaksono.iscan.data.responses.WrappedResponses
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    //    GET RECENT TOKO
    @GET("toko")
    fun getRecentToko(
        @Query("list_toko") listToko: String? ,
        @Query("type") type: String = "some" ,
    ): Observable<WrappedListResponses<TokoEntity>>

    //    GET TOKO BY BARCODE
    @GET("toko")
    fun getTokoByBarcode(
        @Query("barcode") barcode: String? ,
        @Query("type") type: String = "single"
    ): Observable<WrappedResponses<TokoEntity>>

//PUT TOKO update scan_count
    @FormUrlEncoded
    @PUT("toko")
    fun updateScanCount(
    @Field("id")id:String?
    ):Observable<WrappedResponses<Boolean>>

    //    GET PRODUK BY BARCODE
    @GET("produk")
    fun getProdukByBarcode(
        @Query("id_toko") id_toko: String? ,
        @Query("barcode") barcode: String? ,
        @Query("type") type: String="single" ,
    ):Observable<WrappedResponses<ProdukEntity>>


}
