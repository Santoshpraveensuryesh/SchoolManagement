package com.viewmodel.app.api

import android.content.Context


object ApiUtils {

    var Base_URl = "https://school.demo.ibook12.com/"
    fun getApiService(): APIService {
        return RitrofitClient.getClient(Base_URl)!!.create(APIService::class.java)
    }
}