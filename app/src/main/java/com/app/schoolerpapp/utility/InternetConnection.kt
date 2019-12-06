package com.viewmodel.app.utility

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by my lenovo on 2/8/2017.
 */
class InternetConnection() {

    companion object {
        private lateinit var activity: Activity

        fun setIntance(activity: Activity) {
          this.activity =activity
        }
        fun isNetworkAvailable(): Boolean {
            val networkTypes =
                intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
            try {
                val connectivityManager =
                    activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                for (networkType in networkTypes) {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                        return true
                }
            } catch (e: Exception) {
                return false
            }

            return false
        }
    }


}