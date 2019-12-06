package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentBookDetailsBinding
import com.app.schoolerpapp.databinding.FragmentFeeDetailsBinding
import com.app.schoolerpapp.databinding.FragmentHomeworkDetailsBinding
import com.app.schoolerpapp.model.Book
import com.app.schoolerpapp.model.Homework
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.LibraryViewModel
import com.app.schoolerpapp.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.toolbar.*

class HomeWorkDetailsFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var homeWork: Homework
    private lateinit var homeworkDetailsBinding: FragmentHomeworkDetailsBinding
    private lateinit var studentViewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        if (arguments != null) {
            homeWork = arguments!!.getParcelable<Homework>("homework")!!
        }

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
        homeworkDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_homework_details, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        homeworkDetailsBinding.studentViewModel = studentViewModel
        homeworkDetailsBinding.homeWork = homeWork
        return homeworkDetailsBinding.root
    }

    override fun onResume() {
        super.onResume()
        activity.hideMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.homework_details))
    }
}