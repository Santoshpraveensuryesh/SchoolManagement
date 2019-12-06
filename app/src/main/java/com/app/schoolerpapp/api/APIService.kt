package com.viewmodel.app.api

import com.app.schoolerpapp.api.data.DataResponse
import com.app.schoolerpapp.paytm.Checksum
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET


/**
 * Created by impetrosys on 25/6/19.
 */
interface APIService {
    @FormUrlEncoded
    @POST("generateChecksum.php")
    abstract fun getChecksum(
        @Field("MID") mId: String,
        @Field("ORDER_ID") orderId: String,
        @Field("CUST_ID") custId: String,
        @Field("CHANNEL_ID") channelId: String,
        @Field("TXN_AMOUNT") txnAmount: String,
        @Field("WEBSITE") website: String,
        @Field("CALLBACK_URL") callbackUrl: String,
        @Field("INDUSTRY_TYPE_ID") industryTypeId: String
    ): Call<Checksum>

    @FormUrlEncoded
    @POST(ApiFunctions.LOGIN)
    abstract fun loginUser(
        @Field("username") email: String,
        @Field("password") password: String,
        @Field("devicetoken") devicetoken: String,
        @Field("devicetype") devicetype: String,
        @Field("deviceid") deviceid: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST(ApiFunctions.MYCHILD)
    abstract fun myChild(
        @Field("parentid") parentid: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST(ApiFunctions.MYCHILDDETAILS)
    abstract fun myChildDetails(
        @Field("childid") childid: String
    ): Call<DataResponse>
}