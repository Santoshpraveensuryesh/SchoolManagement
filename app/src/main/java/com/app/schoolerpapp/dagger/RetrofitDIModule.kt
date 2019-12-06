package com.app.schoolerpapp.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.viewmodel.app.api.APIService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class RetrofitDIModule {
    var context: Context

    constructor(context: Context) {
        this.context = context;
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    /*
    * The method returns the Gson object
    * */
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }


    /*
     * The method returns the Cache object
     * */
    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }


    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        return httpClient.build()
    }


    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        var retrofit: Retrofit? = null

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://school.demo.ibook12.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!

    }

    /*
     * We need the MovieApiService module.
     * For this, We need the Retrofit object, Gson, Cache and OkHttpClient .
     * So we will define the providers for these objects here in this module.
     *
     * */

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}