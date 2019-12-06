package com.app.schoolerpapp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.session.MySharedPreferences
import java.util.ArrayList
import javax.inject.Inject


class SplashActivity : BaseActivity() {
    private val SPLASH_DELAY: Long = 2000
    private val mHandler = Handler()
    private val mLauncher = Launcher()
    val permission = arrayOf(
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET
        , Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
//       , Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.CALL_PHONE
    )
    val MY_PERMISSIONS_REQUESINT = 123
    //    @Inject
//    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    private inner class Launcher : Runnable {
        override fun run() {

            var userData =
                mySharedPreferences.getUser()
            if (userData != null && !userData.id.equals("")) {
                intent = Intent(this@SplashActivity, DashBoardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).getComponent().inject(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val requestpermission = ArrayList<String>()

            for (s in permission) {
                if (!hasPermission(s)) {
                    requestpermission.add(s)
                }
            }
            if (requestpermission.size > 0) {
                var stockArr = arrayOfNulls<String>(requestpermission.size)
                stockArr = requestpermission.toTypedArray<String?>()
                requestPermissions(stockArr, MY_PERMISSIONS_REQUESINT)
            } else {
                launch()
            }
        } else {
            launch()
        }


    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUESINT -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launch()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val requestPermission = ArrayList<String>()
                    for (s in permission) {
                        if (!hasPermission(s)) {
                            requestPermission.add(s)
                        }
                    }
                    if (requestPermission.size > 0) {
                        var stockArr = arrayOfNulls<String>(requestPermission.size)
                        stockArr = requestPermission.toTypedArray<String?>()
                        requestPermissions(stockArr, MY_PERMISSIONS_REQUESINT)
                    } else {
                        launch()
                    }
                } else {
                    launch()
                }
            }
        }
    }

    private fun launch() {
        mHandler.postDelayed(mLauncher, SPLASH_DELAY)
    }

}