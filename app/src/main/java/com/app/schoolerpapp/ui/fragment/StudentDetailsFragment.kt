package com.app.schoolerpapp.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.R
import com.app.schoolerpapp.api.data.DataResponse
import com.app.schoolerpapp.databinding.FragmentStudentDetailsBinding
import com.app.schoolerpapp.model.Exam
import com.app.schoolerpapp.model.Student
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.model.TimeLine
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.StudentDetailsAdapter
import com.app.schoolerpapp.viewmodel.LoginViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.viewmodel.app.utility.AppUtils
import kotlinx.android.synthetic.main.fragment_student_details.*
import kotlinx.android.synthetic.main.fragment_student_details.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class StudentDetailsFragment : Fragment(), View.OnClickListener {


    private lateinit var mPagerAdapter: StudentDetailsAdapter
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentStudentDetailsBinding: FragmentStudentDetailsBinding
    private lateinit var loginViewModel: LoginViewModel
    private var childId: String = ""
    private var myChildDetails = ArrayList<Student>()
    private var timeLineList = ArrayList<TimeLine>()
    private var examList = ArrayList<Exam>()
    private var dueFeeList = ArrayList<StudentFees>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        if (arguments!!.containsKey("childid"))
            childId = arguments!!.getString("childid", "")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var param: RelativeLayout.LayoutParams =
            activity.txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginEnd = 0
        param.marginStart = 0
        activity.txt_title.layoutParams = param
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_student_details, container, false)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.childId="14"
        fragmentStudentDetailsBinding.loginViewModel = loginViewModel
        setView(fragmentStudentDetailsBinding.root)
        return fragmentStudentDetailsBinding.root
    }

    private fun setView(root: View) {

        mPagerAdapter = StudentDetailsAdapter(childFragmentManager,myChildDetails,timeLineList,examList)
        mPagerAdapter.setCount(4)

        root.tabLayout.removeAllTabs()

        root.viewpager.setAdapter(mPagerAdapter)
        root.viewpager.setCurrentItem(0)
        root.tabLayout.addTab(root.tabLayout.newTab().setCustomView(getTabView(0)))
        root.tabLayout.addTab(root.tabLayout.newTab().setCustomView(getTabView(1)))
        root.tabLayout.addTab(root.tabLayout.newTab().setCustomView(getTabView(2)))
        root.tabLayout.addTab(root.tabLayout.newTab().setCustomView(getTabView(3)))

        root.viewpager.setOffscreenPageLimit(mPagerAdapter.getCount())
        root.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(root.tabLayout))

        root.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager.setCurrentItem(tab!!.getPosition())
            }

        })
        if (!childId.equals("")) {
            showProgress()
            checkNetwork()
            getChildDetails()
        }
//        root.img_profile.setOnClickListener(this)
    }
    private fun checkNetwork() {
        loginViewModel.netWorkDataReq.observe(this, Observer { netWorkDataRes ->
            AppUtils.showBar(cons_student_details, netWorkDataRes, activity)
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
    private fun getChildDetails() {
        loginViewModel.myChildsDetails.observe(this, Observer { dataResponse ->
            if (dataResponse.response == 1) {
                setDatainPager(dataResponse)
            } else {
                AppUtils.showBar(cons_student_details, dataResponse.message, activity)
            }
        })
    }

    private fun setDatainPager(childRes: DataResponse) {
        mPagerAdapter = StudentDetailsAdapter(childFragmentManager,childRes.myChildDetails,childRes.timeLineList,childRes.examList)
        mPagerAdapter.setCount(4)

        tabLayout.removeAllTabs()

        viewpager.setAdapter(mPagerAdapter)
        viewpager.setCurrentItem(0)
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(0)))
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(1)))
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(2)))
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(3)))

        viewpager.setOffscreenPageLimit(mPagerAdapter.getCount())
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager.setCurrentItem(tab!!.getPosition())
            }

        })
    }


    fun getTabView(position: Int): View {
        val stringResId = intArrayOf(
            R.string.profile,
            R.string.fees,
            R.string.exam,
            R.string.timeline
        )
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        val v = LayoutInflater.from(activity).inflate(R.layout.custom_tab_2, null)
        val tv = v.findViewById(R.id.tab) as TextView
        val frameLayout = v.findViewById(R.id.tab_bg) as FrameLayout
        tv.setText(stringResId[position])
        tv.setTextColor(Color.parseColor("#d90746"))
        return v
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.student_info))
//        activity.hideMenuIcon()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_profile -> {
                AppUtils.selectImage(activity)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result code is RESULT_OK only if the user captures an Image
        if (requestCode == AppUtils.CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK)
                try {
                    val uri = Uri.parse(AppUtils.cameraFilePath)

                    val thumbnail = AppUtils.compressImage(uri, activity)
//                    val byte1 = ByteArrayOutputStream()
//                    thumbnail?.compress(Bitmap.CompressFormat.JPEG, 90, byte1)
//                    val b = byte1.toByteArray()
//                    val encodedImage = android.util.Base64.encodeToString(b, android.util.Base64.NO_WRAP)
//                    adminData.photo = encodedImage

//                    if (!adminData.photo.equals("")) {
                    Glide.with(activity)
                        .load(thumbnail)
                        .into(img_profile)
//                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
        } else if (requestCode == AppUtils.GALLERY_REQUEST_CODE) {
            // Result code is RESULT_OK only if the user captures an Image
            if (resultCode == Activity.RESULT_OK) {
                val uri = data!!.getData()

                val thumbnail = AppUtils.compressImage(uri!!, activity)
                val byte1 = ByteArrayOutputStream()
                thumbnail?.compress(Bitmap.CompressFormat.JPEG, 90, byte1)
                val b = byte1.toByteArray()
                val encodedImage = Base64.encodeToString(b, android.util.Base64.NO_WRAP)
//                adminData.photo = encodedImage

//                if (!adminData.photo.equals("")) {
                Glide.with(activity)
                    .load(thumbnail)
                    .into(img_profile)
//                }
            }
        }
    }
}