package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentAttendanceBinding
import com.app.schoolerpapp.model.StudentAttendance
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.custom.CalendarCustomView
import kotlinx.android.synthetic.main.fragment_attendance.view.*
import kotlinx.android.synthetic.main.toolbar.*

class AttendanceFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentAttendanceBinding: FragmentAttendanceBinding
    private var attendaceList = ArrayList<StudentAttendance>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
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
        fragmentAttendanceBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_attendance,
            container,
            false
        )
        setView(fragmentAttendanceBinding.root)
        return fragmentAttendanceBinding.root
    }

    private fun setView(root: View) {
        for (i in 1..30) {
            var studentAttendance = StudentAttendance()
            if (i == 10) {
                studentAttendance.date = "10-11-2019"
                studentAttendance.attendanceType = "Abstent"
            } else if (i == 15) {
                studentAttendance.date = "15-11-2019"
                studentAttendance.attendanceType = "Late"
            } else if (i == 25) {
                studentAttendance.date = "25-11-2019"
                studentAttendance.attendanceType = "Half-day"
            } else if (i == 30) {
                studentAttendance.date = "30-11-2019"
                studentAttendance.attendanceType = "Holiday"
            } else {
                studentAttendance.date = "28-11-2019"
                studentAttendance.attendanceType = "Present"
            }
            attendaceList.add(studentAttendance)
        }

        root.custom_calendar.setListener(object : CalendarCustomView.DateItemClick {
            override fun onDateClicked(year: Int, month: Int, date: Int) {


            }
        })
        root.custom_calendar.setList(attendaceList)
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.attendance))
//        activity.hideMenuIcon()
    }
}