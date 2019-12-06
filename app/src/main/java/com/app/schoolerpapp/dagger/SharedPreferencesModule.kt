package com.app.schoolerpapp.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SharedPreferencesModule {
    var context: Context
    constructor(context: Context){
        this.context = context;
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    internal fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
    }
}