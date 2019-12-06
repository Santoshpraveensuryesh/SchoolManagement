package com.app.schoolerpapp.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ChangeLanguageBinding
import com.app.schoolerpapp.databinding.FragmentSettingBinding
import com.app.schoolerpapp.language.LocaleManager
import com.app.schoolerpapp.model.UserData
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.activity.SplashActivity
import com.app.schoolerpapp.viewmodel.UserDataViewModel
import com.viewmodel.app.utility.AppUtils
import com.viewmodel.app.utility.AppUtils2
import kotlinx.android.synthetic.main.change_language.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class SettingFragment : Fragment(), View.OnClickListener {
    private lateinit var dialog: Dialog
    private lateinit var rootView: View
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentSettingBinding: FragmentSettingBinding
    private lateinit var userDataViewModel: UserDataViewModel
    private lateinit var myLocale: Locale
    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        userData = activity.mySharedPreferences.getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSettingBinding = DataBindingUtil.inflate<FragmentSettingBinding>(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        fragmentSettingBinding.userDataViewModel = userDataViewModel
        fragmentSettingBinding.userData = activity.mySharedPreferences.getUser()
        setView(fragmentSettingBinding.root)
        return fragmentSettingBinding.root
    }

    private fun setView(root: View) {
        rootView = fragmentSettingBinding.root
//        root.btn_chnge_pass.setOnClickListener(this)
        root.btn_chnge_language.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.setting))

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
//            R.id.btn_chnge_pass -> {
//            }
            R.id.btn_chnge_language -> {
                openChangePassDialog()
            }
        }
    }

    private fun setNewLocale(
        language: String,
        restartProcess: Boolean
    ): Boolean {
        MyApplication.localeManager.setNewLocale(activity, language)
        val i = Intent(activity, SplashActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        if (restartProcess) {
            System.exit(0)
        } else {
            Toast.makeText(activity, "Activity restarted", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    var isOpen = false
    private fun openChangePassDialog() {
        dialog = Dialog(activity, R.style.dialog_slide_animation)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Include dialog.xml file
        var changePasswordBinding: ChangeLanguageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.change_language,
                null,
                false
            )
        dialog.setContentView(changePasswordBinding.root)

        changePasswordBinding.userDataViewModel = userDataViewModel
        changePasswordBinding.userData = userData
        dialog.img_cross.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppUtils2.applyRipple(dialog.img_cross, activity)
        } else {
            AppUtils.setEffectbelow21(activity, dialog.img_cross)
        }
        // Include dialog.xml file
        val width = activity.resources.displayMetrics.widthPixels
        dialog.window!!.setLayout(width - 80, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        dialog.rg_language.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->

            val pos = dialog.rg_language.indexOfChild(dialog.findViewById(checkedId))
            when (pos) {
                0 -> {
                    userData.language = activity.resources.getString(R.string.lan_1)
                    if (isOpen) {
                        activity.mySharedPreferences.setUser(userData)
                        fragmentSettingBinding.userData = userData
                        setNewLocale(LocaleManager.LANGUAGE_ENGLISH, true)
                    }
                }
                1 -> {
                    userData.language = activity.resources.getString(R.string.lan_2)
                    if (isOpen) {
                        activity.mySharedPreferences.setUser(userData)
                        fragmentSettingBinding.userData = userData
                        setNewLocale(LocaleManager.LANGUAGE_ARABIC, true)
                    }
                }
            }

            dialog.dismiss()
        })

        if (userData.language.equals(activity.getString(R.string.lan_1))) {
            dialog.radio_english.isChecked = true
        } else if (userData.language.equals(activity.getString(R.string.lan_2))) {
            dialog.radio_arabic.isChecked = true
        }
        dialog.setOnDismissListener {
            isOpen = false
        }
        dialog.setOnShowListener {
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setDimAmount(0.2f)
        dialog.show()
        isOpen = true

    }

}