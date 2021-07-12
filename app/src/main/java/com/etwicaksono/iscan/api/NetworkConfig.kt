package com.etwicaksono.iscan.api

import com.etwicaksono.iscan.model.ProdukModel
import com.etwicaksono.iscan.model.TokoModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkConfig {
    const val baseurl:String = "http://192.168.3.104/project/server_iscan/api/"

    fun getInterceptor():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level=HttpLoggingInterceptor.Level.BODY

        val okHttpClient=OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return okHttpClient
    }

//    Retrofit
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTokoService()= getRetrofit().create(TokoService::class.java)
    fun getProdukService()= getRetrofit().create(ProdukService::class.java)
}

interface TokoService {

//    fungsi get data
    @GET("toko")
    fun getDataToko(
    @Query("type")type:String?,
    @Query("barcode")barcode:String?,
    ): Call<WrappedResponse<TokoModel>>
}

interface ProdukService{
//    fungsi get data
    @GET("produk")
    fun getDataProduk(
    @Query("type")type: String?,
    @Query("barcode")barcode: String?
    ):Call<WrappedResponse<ProdukModel>>
}
