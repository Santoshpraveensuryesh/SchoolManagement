package com.app.schoolerpapp.session

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import com.google.gson.Gson


class MySharedPreferences {

    private var mSharedPreferences: SharedPreferences
    private val USERDATA = "userdata"

    @Inject
    constructor(mSharedPreferences: SharedPreferences) {
        this.mSharedPreferences = mSharedPreferences
    }

    fun setUser(userData: com.app.schoolerpapp.model.UserData?) {
        val gson = Gson()
        val json = gson.toJson(userData)
        var sharedPreferencesEditor = mSharedPreferences.edit()
        sharedPreferencesEditor.putString(USERDATA, json)
        sharedPreferencesEditor.commit()
    }

    fun getUser(): com.app.schoolerpapp.model.UserData {
        val gson = Gson()
        val json = mSharedPreferences.getString(USERDATA, "")
        if (json.equals("")) {
            return com.app.schoolerpapp.model.UserData()
        }
        return gson.fromJson(json, com.app.schoolerpapp.model.UserData::class.java)
    }
    fun setSharedPreferenceString(context: Context, key: String, value: String) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSharedPreferenceString(key: String, defValue: String): String {
        return mSharedPreferences.getString(key, defValue)!!
    }

    fun clear() {
        var sharedPreferencesEditor = mSharedPreferences.edit()
        sharedPreferencesEditor.clear()
        sharedPreferencesEditor.commit()
    }
}