package com.app.schoolerpapp.viewmodel

import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.api.data.DataResponse
import com.app.schoolerpapp.model.UserData
import com.app.schoolerpapp.session.MySharedPreferences
import com.app.schoolerpapp.utility.Constant
import com.google.gson.Gson
import com.viewmodel.app.api.APIService
import com.viewmodel.app.api.ApiUtils
import com.viewmodel.app.utility.AppUtils
import com.viewmodel.app.utility.InternetConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class LoginViewModel : ViewModel() {
    private var busy: MutableLiveData<Int>? = null
    private var loadProgess: MutableLiveData<Boolean>? = null
    private var netWorkLiveData: MutableLiveData<String>? = null
    private var otpData: MutableLiveData<DataResponse>? = null
    private var loginData: MutableLiveData<DataResponse>? = null
    //validatation
    private var validateLiveData: MutableLiveData<String>? = null
    private var mAPIService: APIService? = null

    //login
    private var loginData2: MutableLiveData<DataResponse>? = null

    //My child
    private var myChildsLiveData: MutableLiveData<DataResponse>? = null

    //child Details
    private var childsDetailsLiveData: MutableLiveData<DataResponse>? = null

    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    var parentId = ""
    var childId = ""

    fun getBusy(): MutableLiveData<Int> {

        if (busy == null) {
            busy = MutableLiveData()
            busy!!.setValue(8)
        }

        return busy as MutableLiveData<Int>
    }

    val validateData: LiveData<String>
        get() {
            if (validateLiveData == null) {
                validateLiveData = MutableLiveData()
            }

            return validateLiveData as MutableLiveData<String>
        }

    val netWorkDataReq: LiveData<String>
        get() {
            if (netWorkLiveData == null) {
                netWorkLiveData = MutableLiveData()
            }

            return netWorkLiveData as MutableLiveData<String>
        }

    val loadProgessReq: LiveData<Boolean>
        get() {
            if (loadProgess == null) {
                loadProgess = MutableLiveData()
            }

            return loadProgess as MutableLiveData<Boolean>
        }
    val otpDataReq: LiveData<DataResponse>
        get() {
            if (otpData == null) {
                otpData = MutableLiveData()
            }

            return otpData as MutableLiveData<DataResponse>
        }

    val loginDataReq: LiveData<DataResponse>
        get() {
            if (loginData == null) {
                loginData = MutableLiveData()
            }

            return loginData as MutableLiveData<DataResponse>
        }

    val loginDataReq2: LiveData<DataResponse>
        get() {
            if (loginData2 == null) {
                loginData2 = MutableLiveData()
            }

            return loginData2 as MutableLiveData<DataResponse>
        }

    fun onSendOtp(userData: UserData) {
        if (AppUtils.notBlank(userData.mobile)) {
            var s = "Please Enter Mobile Number"
            validateLiveData!!.value = s
        } else if (!AppUtils.isValidMobile(userData.mobile)) {
            var s = "Please Enter valid phone"
            validateLiveData!!.value = s
        } else {
            if (InternetConnection.isNetworkAvailable()) {
//                callOtpAPI()
                otpData!!.value = DataResponse()
            } else {
                netWorkLiveData!!.value = "Please Check Internet Connection"
            }
        }
    }

    fun onLoginClicked(userData: UserData) {
//        if (AppUtils.notBlank(userData.otp)) {
//            var s = "Please Enter OTP"
//            validateLiveData!!.value = s
//        } else
        if (AppUtils.notBlank(userData.userName)) {
            var s = "Please Enter Username"
            validateLiveData!!.value = s
        } else if (AppUtils.notBlank(userData.password)) {
            var s = "Please Enter Password"
            validateLiveData!!.value = s
        } else {
            if (InternetConnection.isNetworkAvailable()) {
//                callOtpAPI()
                callLoginAPI(userData)
            } else {
                netWorkLiveData!!.value = "Please Check Internet Connection"
            }
        }
    }


    private fun callLoginAPI(userData: UserData) {
        mAPIService = ApiUtils.getApiService()
//        getBusy().value = 0

        loadProgess!!.value = true

        var call: Call<DataResponse>
        call = mAPIService!!.loginUser(
            userData.userName,
            userData.password,
            userData.fcmToken,
            "android",
            userData.deviceId
        )

        call.enqueue(object : Callback<DataResponse> {
            internal val gson = Gson()

            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false

                if (response.isSuccessful()) {
                    if (response.body()?.getResponse() == 1) {
                        loginData2!!.value = response.body()
                    } else {
                        loginData2!!.value = response.body()
                    }
                }

            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false

            }
        })

    }

    val myChilds: LiveData<DataResponse>
        get() {
            if (myChildsLiveData == null) {
                myChildsLiveData = MutableLiveData()
            }
            if (InternetConnection.isNetworkAvailable()) {
                getMyChild()
            } else {
                netWorkLiveData!!.value = "Please Check Internet Connection"
            }

            return myChildsLiveData as MutableLiveData<DataResponse>
        }

    private fun getMyChild() {
        mAPIService = ApiUtils.getApiService()
//        getBusy().value = 0

        loadProgess!!.value = true

        var call: Call<DataResponse>
        call = mAPIService!!.myChild(parentId)

        call.enqueue(object : Callback<DataResponse> {
            internal val gson = Gson()
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false
                if (response.isSuccessful()) {
                    if (response.body()?.getResponse() == 1) {
                        myChildsLiveData!!.value = response.body()
                    } else {
                        myChildsLiveData!!.value = response.body()
                    }
                }

            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false
            }
        })

    }


    val myChildsDetails: LiveData<DataResponse>
        get() {
            if (childsDetailsLiveData == null) {
                childsDetailsLiveData = MutableLiveData()
            }
            if (InternetConnection.isNetworkAvailable()) {
                getChildDetails()
            } else {
                netWorkLiveData!!.value = "Please Check Internet Connection"
            }
            return childsDetailsLiveData as MutableLiveData<DataResponse>
        }

    private fun getChildDetails() {
        mAPIService = ApiUtils.getApiService()
//        getBusy().value = 0
        loadProgess!!.value = true


        var call: Call<DataResponse>
        call = mAPIService!!.myChildDetails(
            childId
        )

        call.enqueue(object : Callback<DataResponse> {
            internal val gson = Gson()

            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false

                if (response.isSuccessful()) {
                    if (response.body()?.getResponse() == 1) {
                        childsDetailsLiveData!!.value = response.body()
                    } else {
                        childsDetailsLiveData!!.value = response.body()
                    }
                }

            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//                busy!!.setValue(8) //8 == View.GONE
                loadProgess!!.value = false

            }
        })

    }


}