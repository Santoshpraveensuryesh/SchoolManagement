package com.viewmodel.app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit






object RitrofitClient {
    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60*60, TimeUnit.SECONDS)
            .writeTimeout(60*60, TimeUnit.SECONDS)
            .readTimeout(60*60, TimeUnit.SECONDS)
            .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
    fun getClient2(baseUrl: String): Retrofit? {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60*60, TimeUnit.SECONDS)
            .writeTimeout(60*60, TimeUnit.SECONDS)
            .readTimeout(60*60, TimeUnit.SECONDS)
            .build()
        if (retrofit2 == null) {
            retrofit2 = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit2
    }
}
