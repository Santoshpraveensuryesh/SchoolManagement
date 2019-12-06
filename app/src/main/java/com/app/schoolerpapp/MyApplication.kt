package com.app.schoolerpapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.app.schoolerpapp.dagger.AppComponent
import com.app.schoolerpapp.dagger.DaggerAppComponent
import com.app.schoolerpapp.dagger.SharedPreferencesModule
import com.app.schoolerpapp.language.LocaleManager

class MyApplication : Application() {
    private lateinit var component: AppComponent

    companion object {
        lateinit var localeManager: LocaleManager
        public val TAG = "App"
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    override fun onCreate() {
        super.onCreate()
        //needs to run once to generate it
        component = DaggerAppComponent.builder()
//            .appModule(AppModule(this))
            .sharedPreferencesModule(SharedPreferencesModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return component
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager!!.setLocale(this)
        Log.d(
            TAG,
            "onConfigurationChanged: " + newConfig.locale.language
        )
    }

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager!!.setLocale(base))
        Log.d(TAG, "attachBaseContext")
    }
}
