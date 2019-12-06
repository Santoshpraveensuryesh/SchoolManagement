package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentStudentListBinding
import com.app.schoolerpapp.model.Student
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.StudentAdapter
import com.app.schoolerpapp.viewmodel.StudentViewModel
import com.viewmodel.app.utility.AppUtils
import kotlinx.android.synthetic.main.fragment_student_list.view.*
import kotlinx.android.synthetic.main.toolbar.*

class StudentListFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentStudentListBinding: FragmentStudentListBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var studentAdapter: StudentAdapter
    private var studentList = ArrayList<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.img_back.setOnClickListener { activity.onBackPressed() }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_student_list, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        fragmentStudentListBinding.studentViewModel = studentViewModel
        setView(fragmentStudentListBinding.root)
        return fragmentStudentListBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.tv_student_list.hasFixedSize()
        root.tv_student_list.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var student = Student()
            student.admissionNum = "school " + i
            student.studentName = "student " + i
            studentList.add(student)
        }

        studentAdapter =
            StudentAdapter(activity, studentList,studentViewModel, object : StudentAdapter.OnClickStudent {
                override fun onClick(pos: Int) {

                }
            })
        root.tv_student_list.adapter = studentAdapter

        callStudentListObserver()
    }

    private fun callStudentListObserver() {
        studentViewModel.studentDataReq.observe(this, Observer { dataResponse ->
            AppUtils.replaceFragment(StudentDetailsFragment(), activity, R.id.container)
            activity.closeDrawer()
        })
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        var param: RelativeLayout.LayoutParams =
            activity.txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginStart = resources.getDimensionPixelOffset(R.dimen._50sdp)
        activity.txt_title.layoutParams = param
    }
}