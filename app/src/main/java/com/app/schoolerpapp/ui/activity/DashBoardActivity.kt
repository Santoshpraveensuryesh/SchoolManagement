package com.app.schoolerpapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.MyApplication
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ActivityDashBinding
import com.app.schoolerpapp.model.Student
import com.app.schoolerpapp.model.UserMenuItem
import com.app.schoolerpapp.model.UserSubMenuItem
import com.app.schoolerpapp.session.MySharedPreferences
import com.app.schoolerpapp.ui.fragment.*
import com.app.schoolerpapp.viewmodel.LoginViewModel
import com.viewmodel.app.ui.adapter.NavigationAdapter
import com.viewmodel.app.utility.AppUtils
import com.viewmodel.app.utility.InternetConnection
import kotlinx.android.synthetic.main.activity_dash.*
import kotlinx.android.synthetic.main.drawer_view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject


class DashBoardActivity : BaseActivity() {
    //    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var menuItemArray: ArrayList<UserMenuItem>
    private lateinit var navigationAdapter: NavigationAdapter
    private lateinit var activityDashBinding: ActivityDashBinding
    internal lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var loginViewModel: LoginViewModel

    private var clickPos = 0
    //    @Inject
//    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences
    //menu list
    private lateinit var menuList: Array<String>
    //menu icon list
    private val menuIconList = arrayOf<Int>(
        R.drawable.children,
        R.drawable.fees,
        R.drawable.time_table,
        R.drawable.home_work,
        R.drawable.download_center,
        R.drawable.attendance,
        R.drawable.exam,
        R.drawable.notice,
        R.drawable.subjects,
        R.drawable.teachers,
        R.drawable.library,
        R.drawable.hostel,
        R.drawable.settings,
        R.drawable.logout
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_dash)
        (application as MyApplication).getComponent().inject(this)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activityDashBinding.loginViewModel = loginViewModel

        InternetConnection.setIntance(this)

        var userData =
            mySharedPreferences.getUser()
//        sharedPreferenceHelper = SharedPreferenceHelper(this, sharedPreferences!!)
        try {
            setupView()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupView() {
        menuList = arrayOf<String>(
            resources.getString(R.string.my_children),
            resources.getString(R.string.fees),
            resources.getString(R.string.class_timetable),
            resources.getString(R.string.homework),
            resources.getString(R.string.download_center),
            resources.getString(R.string.attendance),
            resources.getString(R.string.examinations),
            resources.getString(R.string.notice_board),
            resources.getString(R.string.subjects),
            resources.getString(R.string.teachers),
            resources.getString(R.string.library_books),
            resources.getString(R.string.hostel),
            resources.getString(R.string.setting),
            resources.getString(R.string.logout)
        )
        menuItemArray = ArrayList<UserMenuItem>()
        addMenuItem(ArrayList<Student>())
        linearLayoutManager = LinearLayoutManager(this)
        rv_menu.setHasFixedSize(true)
        rv_menu.setLayoutManager(linearLayoutManager)

        navigationAdapter =
            NavigationAdapter(
                this,
                menuItemArray,
                object : NavigationAdapter.MenuItemCLickListener {
                    override fun onItemCLick(pos: Int, subPos: Int) {
                        when (pos) {
                            0 -> {
//                                if (clickPos != 0) {
//                                    clickPos = 0
                                AppUtils.loadFragment(
                                    StudentDetailsFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            1 -> {
//                                if (clickPos != 1) {
//                                    clickPos = 1
                                var feeFragment = FeesFragment()
                                var bundle = Bundle()
                                bundle.putString("fromMenu", "fromMenu")
                                feeFragment.arguments = bundle
                                AppUtils.loadFragment(
                                    feeFragment,
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            2 -> {
//                                if (clickPos != 2) {
//                                    clickPos = 2
                                AppUtils.loadFragment(
                                    ClassTimetableFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            3 -> {
//                                if (clickPos != 3) {
//                                    clickPos = 3
                                AppUtils.loadFragment(
                                    HomeWorkFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            4 -> {
//                                if (clickPos != 4) {
//                                    clickPos = 4
                                AppUtils.showBar(
                                    drawerLayout,
                                    "Under Developrment",
                                    this@DashBoardActivity
                                )
//                                }
                            }
                            5 -> {
//                                if (clickPos != 5) {
//                                    clickPos = 5
                                AppUtils.loadFragment(
                                    AttendanceFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            6 -> {
//                                if (clickPos != 6) {
//                                    clickPos = 6
                                var examFragment = ExamFragment()
                                var bundle = Bundle()
                                bundle.putString("fromMenu", "fromMenu")
                                examFragment.arguments = bundle
                                AppUtils.loadFragment(
                                    examFragment,
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            7 -> {
//                                if (clickPos != 7) {
//                                    clickPos = 7
                                AppUtils.loadFragment(
                                    NoticeBoardFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            8 -> {
//                                if (clickPos != 8) {
//                                    clickPos = 8
                                AppUtils.loadFragment(
                                    SubjectFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            9 -> {
//                                if (clickPos != 9) {
//                                    clickPos = 9
                                AppUtils.loadFragment(
                                    TeacherFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            10 -> {
//                                if (clickPos != 10) {
//                                    clickPos = 10
                                AppUtils.loadFragment(
                                    LibraryFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }

                            11 -> {
//                                if (clickPos != 11) {
//                                    clickPos = 11
                                AppUtils.loadFragment(
                                    HostelFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            12 -> {
//                                if (clickPos != 11) {
//                                    clickPos = 11
                                AppUtils.loadFragment(
                                    SettingFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }

                            13 -> {

                                AppUtils.confirmationDialog(
                                    this@DashBoardActivity,
                                    getString(R.string.logout_msg),
                                    object : AppUtils.Companion.ConfirmationListener {
                                        override fun yesClick() {
                                            mySharedPreferences!!.clear()
                                            // Clear all notification
//                              var nMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                              nMgr.cancelAll()
                                            val intent =
                                                Intent(
                                                    this@DashBoardActivity,
                                                    LoginActivity::class.java
                                                )
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                            finish()
                                        }
                                    })


                            }
                        }
                        closeDrawer()
                    }

                })
        rv_menu.adapter = navigationAdapter

        var studentDetailsFragment = StudentDetailsFragment()
        var bundle = Bundle()
        studentDetailsFragment.arguments = bundle
        AppUtils.loadFragment(studentDetailsFragment, this, R.id.container, "")

        setUpToolBar()

        //setToggel menu
        actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, activityDashBinding.toolView.toolBar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {
            @SuppressLint("NewApi")
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                navigationAdapter.notifyDataSetChanged()
            }
        }

        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.setHomeAsUpIndicator(null)
        actionBarDrawerToggle.syncState()
        activityDashBinding?.toolView?.toolBar?.setNavigationIcon(R.drawable.ic_menu_black_24dp)


        loginViewModel.parentId = mySharedPreferences.getUser().id
        showProgress()
        checkNetwork()
        getMyChild()

        txt_username.setText(mySharedPreferences.getUser().userName)
    }
    private fun checkNetwork() {
        loginViewModel.netWorkDataReq.observe(this, Observer { netWorkDataRes ->
            AppUtils.showBar(drawerLayout, netWorkDataRes, this)
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
    private fun getMyChild() {
        loginViewModel.myChilds.observe(this, Observer { dataResponse ->
            if (dataResponse.response == 1) {
                setUpNavigation(dataResponse.myChilds)
            } else {
                AppUtils.showBar(drawerLayout, dataResponse.message, this)
            }
        })
    }

    private fun setUpNavigation(myChilds: ArrayList<Student>) {
        addMenuItem(myChilds)
        linearLayoutManager = LinearLayoutManager(this)
        rv_menu.setHasFixedSize(true)
        rv_menu.setLayoutManager(linearLayoutManager)

        navigationAdapter =
            NavigationAdapter(
                this,
                menuItemArray,
                object : NavigationAdapter.MenuItemCLickListener {
                    override fun onItemCLick(pos: Int, subPos: Int) {
                        when (pos) {
                            0 -> {
//                                if (clickPos != 0) {
//                                    clickPos = 0
                                var studentDetailsFragment=StudentDetailsFragment()
                                var bundle = Bundle()
                                bundle.putString("childid", myChilds.get(0).id)
                                studentDetailsFragment.arguments = bundle
                                AppUtils.loadFragment(
                                    studentDetailsFragment,
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            1 -> {
//                                if (clickPos != 1) {
//                                    clickPos = 1
                                var feeFragment = FeesFragment()
                                var bundle = Bundle()
                                bundle.putString("fromMenu", "fromMenu")
                                feeFragment.arguments = bundle
                                AppUtils.loadFragment(
                                    feeFragment,
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            2 -> {
//                                if (clickPos != 2) {
//                                    clickPos = 2
                                AppUtils.loadFragment(
                                    ClassTimetableFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            3 -> {
//                                if (clickPos != 3) {
//                                    clickPos = 3
                                AppUtils.loadFragment(
                                    HomeWorkFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            4 -> {
//                                if (clickPos != 4) {
//                                    clickPos = 4
                                AppUtils.showBar(
                                    drawerLayout,
                                    "Under Developrment",
                                    this@DashBoardActivity
                                )
//                                }
                            }
                            5 -> {
//                                if (clickPos != 5) {
//                                    clickPos = 5
                                AppUtils.loadFragment(
                                    AttendanceFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            6 -> {
//                                if (clickPos != 6) {
//                                    clickPos = 6
                                var examFragment = ExamFragment()
                                var bundle = Bundle()
                                bundle.putString("fromMenu", "fromMenu")
                                examFragment.arguments = bundle
                                AppUtils.loadFragment(
                                    examFragment,
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            7 -> {
//                                if (clickPos != 7) {
//                                    clickPos = 7
                                AppUtils.loadFragment(
                                    NoticeBoardFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            8 -> {
//                                if (clickPos != 8) {
//                                    clickPos = 8
                                AppUtils.loadFragment(
                                    SubjectFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            9 -> {
//                                if (clickPos != 9) {
//                                    clickPos = 9
                                AppUtils.loadFragment(
                                    TeacherFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            10 -> {
//                                if (clickPos != 10) {
//                                    clickPos = 10
                                AppUtils.loadFragment(
                                    LibraryFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }

                            11 -> {
//                                if (clickPos != 11) {
//                                    clickPos = 11
                                AppUtils.loadFragment(
                                    HostelFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }
                            12 -> {
//                                if (clickPos != 11) {
//                                    clickPos = 11
                                AppUtils.loadFragment(
                                    SettingFragment(),
                                    this@DashBoardActivity,
                                    R.id.container,
                                    ""
                                )
//                                }
                            }

                            13 -> {

                                AppUtils.confirmationDialog(
                                    this@DashBoardActivity,
                                    getString(R.string.logout_msg),
                                    object : AppUtils.Companion.ConfirmationListener {
                                        override fun yesClick() {
                                            mySharedPreferences!!.clear()
                                            // Clear all notification
//                              var nMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                              nMgr.cancelAll()
                                            val intent =
                                                Intent(
                                                    this@DashBoardActivity,
                                                    LoginActivity::class.java
                                                )
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                            finish()
                                        }
                                    })


                            }
                        }
                        closeDrawer()
                    }

                })
        rv_menu.adapter = navigationAdapter

        var studentDetailsFragment = StudentDetailsFragment()
        var bundle = Bundle()
        bundle.putString("childid", myChilds.get(0).id)
        studentDetailsFragment.arguments = bundle
        AppUtils.loadFragment(studentDetailsFragment, this, R.id.container, "")

    }

    fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START) //CLOSE Nav Drawer!
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(activityDashBinding?.toolView?.toolBar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeButtonEnabled(true)
        activityDashBinding?.navigationView?.setItemIconTintList(null)
    }

    fun hideMenuIcon() {
        var param: RelativeLayout.LayoutParams =
            txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginEnd = 0
        param.marginStart = 0
        txt_title.layoutParams = param
        activityDashBinding?.toolView?.toolBar?.setNavigationIcon(null)
        img_back.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    fun showMenuIcon() {
        var param: RelativeLayout.LayoutParams =
            txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginEnd =
            resources.getDimensionPixelOffset(R.dimen._50sdp)
        txt_title.layoutParams = param
        activityDashBinding?.toolView?.toolBar?.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        img_back.visibility = View.INVISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    private fun addMenuItem(myChilds: ArrayList<Student>) {
        if (menuItemArray.size > 0) menuItemArray.clear()
        var i = 0
        for (String in menuList) {
            val userMenuItem = UserMenuItem()
            userMenuItem.menuName = menuList[i]
            userMenuItem.icon = menuIconList[i]
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 8) {

                for (student in myChilds) {
                    var userSubMenuItem = UserSubMenuItem()
                    userSubMenuItem.subMenuName = student.studentName
                    userSubMenuItem.id = student.id
                    userMenuItem.subCategory.add(userSubMenuItem)
                }
            }
            if (i == 4) {
                var userSubMenuItem = UserSubMenuItem()
                userSubMenuItem.subMenuName = "Assignments"
                userMenuItem.subCategory.add(userSubMenuItem)
                var userSubMenuItem2 = UserSubMenuItem()
                userSubMenuItem2.subMenuName = "Study Material"
                userMenuItem.subCategory.add(userSubMenuItem2)
                var userSubMenuItem3 = UserSubMenuItem()
                userSubMenuItem3.subMenuName = "Syllabus"
                userMenuItem.subCategory.add(userSubMenuItem3)
                var userSubMenuItem4 = UserSubMenuItem()
                userSubMenuItem4.subMenuName = "Other Downloads"
                userMenuItem.subCategory.add(userSubMenuItem4)
            }
            menuItemArray.add(userMenuItem)
            i++;
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val manager = supportFragmentManager
        var fragment = manager.findFragmentById(R.id.container)
        if (fragment is StudentDetailsFragment) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    internal var lastPressedTime: Long = 0
    internal val PERIOD = 2000
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            when (event.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (AppUtils.isAppBack(this)) {
                        if (event.downTime - lastPressedTime < PERIOD) {

                            finish()

                        } else {
                            Toast.makeText(
                                applicationContext, "Press again to exit.",
                                Toast.LENGTH_SHORT
                            ).show()

                            lastPressedTime = event.eventTime
                        }
                    } else {
                        super.onBackPressed()
                    }
                    return true
                }
            }
        }
        return false
    }
//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(LocaleHelper.onAttach(base))
//    }
}