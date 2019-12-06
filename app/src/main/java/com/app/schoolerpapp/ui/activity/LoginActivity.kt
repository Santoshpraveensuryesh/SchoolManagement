package com.app.schoolerpapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ActivityLogin2Binding
import com.app.schoolerpapp.model.UserData
import com.app.schoolerpapp.session.MySharedPreferences
import com.app.schoolerpapp.utility.Constant
import com.app.schoolerpapp.viewmodel.LoginViewModel
import com.viewmodel.app.utility.AppUtils
import com.viewmodel.app.utility.InternetConnection
import kotlinx.android.synthetic.main.activity_login2.*
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private var userData: UserData? = null
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        val binding =
            DataBindingUtil.setContentView<ActivityLogin2Binding>(this, R.layout.activity_login2)
        (application as MyApplication).getComponent().inject(this)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        userData = UserData()
        userData!!.deviceId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        userData!!.fcmToken = mySharedPreferences.getSharedPreferenceString(Constant.fcmtoken, "")
        binding.userData = userData
        InternetConnection.setIntance(this)
        callOtpObserver()
        callLoginObserver()
        validateData()
        checkNetwork()
        showProgress()
        setupView()
    }

    private fun setupView() {
        ccp!!.setCountryForPhoneCode(91)
        userData!!.countryCode = ccp.selectedCountryCodeWithPlus
        ccp.setOnCountryChangeListener {
            userData!!.countryCode = ccp.selectedCountryCodeWithPlus
        }
    }

    private fun checkNetwork() {
        loginViewModel.netWorkDataReq.observe(this, Observer { netWorkDataRes ->
            AppUtils.showBar(constraintLay, netWorkDataRes, this)
        })
    }

    private fun showProgress() {
        loginViewModel.loadProgessReq.observe(this, Observer { loadProgessRes ->
            if (loadProgessRes) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun validateData() {
        loginViewModel.validateData.observe(this, Observer { validateDataRes ->
            AppUtils.showBar(constraintLay, validateDataRes, this)
        })
    }

    private fun callLoginObserver() {
        loginViewModel.loginDataReq2.observe(this, Observer { dataResponse ->
            if (dataResponse.response == 1) {
                var intent = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                userData!!.id = dataResponse.parentDetails.get(0).id
                userData!!.type = dataResponse.parentDetails.get(0).type
                userData!!.userName = dataResponse.parentDetails.get(0).userName
                userData!!.mobile = dataResponse.parentDetails.get(0).mobile
                userData!!.photo = dataResponse.parentDetails.get(0).photo
                intent.putExtra("userData", userData)
                mySharedPreferences.setUser(userData)
                startActivity(intent)
                finish()
            } else {
                AppUtils.showBar(constraintLay, dataResponse.message, this)
            }
        })
    }

    private fun callOtpObserver() {
        loginViewModel.otpDataReq.observe(this, Observer { dataResponse ->
            var intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("userData", userData)
            startActivity(intent)
        })
    }

}