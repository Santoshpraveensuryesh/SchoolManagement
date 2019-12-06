package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentExamBinding
import com.app.schoolerpapp.model.Exam
import com.app.schoolerpapp.model.ExamResult
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.ExamAdapter
import com.app.schoolerpapp.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.fragment_exam.view.*
import kotlinx.android.synthetic.main.toolbar.*

class ExamFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentExamBinding: FragmentExamBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private var examList = ArrayList<Exam>()
    private lateinit var examAdapter: ExamAdapter
    private var fromMenu: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        if (arguments != null) {
            if (arguments!!.containsKey("fromMenu"))
                fromMenu = arguments!!.getString("fromMenu")!!
            examList = arguments!!.getParcelableArrayList<Exam>("examList")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentExamBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exam, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        fragmentExamBinding.studentViewModel = studentViewModel
        setView(fragmentExamBinding.root)
        return fragmentExamBinding.root
    }

    private fun setView(root: View) {
        layoutManager = LinearLayoutManager(activity)
        root.rv_exam_list.hasFixedSize()
        root.rv_exam_list.layoutManager = layoutManager
        if (examList.size <= 0) {
            for (i in 0..2) {
                var exam = Exam()
                if (i == 0)
                    exam.examType = "Quartely"
                if (i == 1)
                    exam.examType = "Half Yearly"
                if (i == 2)
                    exam.examType = "Yearly"
                for (i in 0..4) {
                    var examResult = ExamResult()
                    if (i == 0) {
                        examResult.subject = "Hindi(Th.)"
                        examResult.fullMark = "100"
                        examResult.passMark = "33"
                        examResult.getMarks = "70"
                    }
                    if (i == 1) {
                        examResult.subject = "English(Th.)"
                        examResult.fullMark = "100"
                        examResult.passMark = "33"
                        examResult.getMarks = "70"
                    }
                    if (i == 2) {
                        examResult.subject = "Math(Th.)"
                        examResult.fullMark = "100"
                        examResult.passMark = "33"
                        examResult.getMarks = "70"
                    }
                    if (i == 3) {
                        examResult.subject = "Science(Th.)"
                        examResult.fullMark = "100"
                        examResult.passMark = "33"
                        examResult.getMarks = "70"
                    }
                    if (i == 4) {
                        examResult.subject = "Social Science(Th.)"
                        examResult.fullMark = "100"
                        examResult.passMark = "33"
                        examResult.getMarks = "70"
                    }
                    exam.examResult.add(examResult)
                }
                exam.result = "Fail"
                exam.grandTotal = "165/500"
                exam.percentage = "33%"
                exam.grade = "D"
                examList.add(exam)
            }
        }
        examAdapter =
            ExamAdapter(activity, examList, studentViewModel)
        root.rv_exam_list.adapter = examAdapter
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        if (fromMenu.equals("fromMenu"))
            activity.txt_title.setText(activity.resources.getString(R.string.exam_list))
        else
            activity.txt_title.setText(activity.resources.getString(R.string.student_info))
    }
}