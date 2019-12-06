package com.app.schoolerpapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.schoolerpapp.model.*
import com.app.schoolerpapp.ui.fragment.ExamFragment
import com.app.schoolerpapp.ui.fragment.FeesFragment
import com.app.schoolerpapp.ui.fragment.ProfileFragment
import com.app.schoolerpapp.ui.fragment.TimeLineFragment
import kotlin.collections.ArrayList

class StudentDetailsAdapter : FragmentStatePagerAdapter {
    private var mTabCount: Int = 0
    private var myChildDetails = ArrayList<Student>()
    private var timeLineList = ArrayList<TimeLine>()
    private var examList = ArrayList<Exam>()
    private var dueFeeList = ArrayList<StudentFees>()

    constructor(
        fragmentManager: FragmentManager,
        myChildDetails: ArrayList<Student>,
        timeLineList: ArrayList<TimeLine>,
        examList: ArrayList<Exam>
    ) : super(fragmentManager) {
        this.mTabCount = 0
        this.myChildDetails=myChildDetails
        this.timeLineList=timeLineList
        this.examList=examList
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment? = null

        when (position) {
            0 -> {
                val profileFragment = ProfileFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("myChildDetails", myChildDetails)
                profileFragment.arguments = bundle
                return profileFragment
            }

            1 -> {
                val feesFragment = FeesFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("dueFeeList", dueFeeList)
                feesFragment.arguments = bundle
                return feesFragment
            }

            2 -> {
                val examFragment = ExamFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("examList", examList)
                examFragment.arguments = bundle
                return examFragment
            }

            3 -> {
                val timeLineFragment = TimeLineFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("timeLineList", timeLineList)
                timeLineFragment.arguments = bundle
                return timeLineFragment
            }


        }

        return fragment!!
    }

    override fun getCount(): Int {
        return mTabCount
    }

    fun setCount(count: Int) {
        mTabCount = count
    }

    fun addData(
        myChildDetails: ArrayList<Student>,
        timeLineList: ArrayList<TimeLine>,
        examList: ArrayList<Exam>
    ) {
        this.myChildDetails = myChildDetails
        this.timeLineList = timeLineList
        this.examList = examList
    }
}