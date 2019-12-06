package com.app.schoolerpapp.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.viewmodel.app.api.APIService

import java.io.File
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created on : Feb 09, 2019
 * Author     : AndroidWave
 */
@Module
class AppModule {
    lateinit var context: Context
    internal val PREF_FILE = "PREF"
    lateinit var sharedPreferencesEditor:SharedPreferences.Editor

//    private val application: Application

    constructor(context: Context){
        this.context = context;
    }
    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }


//    @Singleton
//    @Provides
//    fun provideSharedPreferenceHelper(sharedPreferences: SharedPreferences): SharedPreferenceHelper {
//        return SharedPreferenceHelper(context)
//    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Singleton
    @Provides
    fun provideSharedEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences!!.edit()
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
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }


    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .build()
    }

    /*
     * We need the MovieApiService module.
     * For this, We need the Retrofit object, Gson, Cache and OkHttpClient .
     * So we will define the providers for these objects here in this module.
     *
     * */

    @Provides
    @Singleton
    internal fun provideMovieApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

}
