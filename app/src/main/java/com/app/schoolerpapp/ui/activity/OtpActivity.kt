package com.app.schoolerpapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ActivityOtpBinding
import com.app.schoolerpapp.model.UserData
import com.app.schoolerpapp.session.MySharedPreferences
import com.app.schoolerpapp.viewmodel.LoginViewModel
import com.viewmodel.app.utility.AppUtils
import com.viewmodel.app.utility.InternetConnection
import kotlinx.android.synthetic.main.activity_otp.*
import javax.inject.Inject

class OtpActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private var userData: UserData? = null
    //    @Inject
//    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null) {
            userData = intent!!.getParcelableExtra("userData")
        }
        var binding =
            DataBindingUtil.setContentView<ActivityOtpBinding>(this, R.layout.activity_otp)
        (application as MyApplication).getComponent().inject(this)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.userData = userData
        InternetConnection.setIntance(this)

        callLoginObserver()
        validateData()
        checkNetwork()

        img_back.setOnClickListener { onBackPressed() }
    }

    private fun checkNetwork() {
        loginViewModel.netWorkDataReq.observe(this, Observer { netWorkDataRes ->
            AppUtils.showBar(constraintotpLay, netWorkDataRes, this)
        })
    }

    private fun validateData() {
        loginViewModel.validateData.observe(this, Observer { validateDataRes ->
            AppUtils.showBar(constraintotpLay, validateDataRes, this)
        })
    }

    private fun callLoginObserver() {
        loginViewModel.loginDataReq.observe(this, Observer { dataResponse ->
            var intent = Intent(this, DashBoardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            intent.putExtra("userData", userData)

            mySharedPreferences.setUser(userData)
            startActivity(intent)
            finish()
        })
    }
}