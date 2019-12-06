package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ExamItemBinding
import com.app.schoolerpapp.model.Exam
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel

class ExamAdapter() : RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    private lateinit var activity: DashBoardActivity
    private lateinit var examList: ArrayList<Exam>
    private lateinit var studentViewModel: StudentViewModel

    constructor(
        activity: DashBoardActivity, examList: ArrayList<Exam>,
        studentViewModel: StudentViewModel
    ) : this() {
        this.activity = activity
        this.studentViewModel = studentViewModel
        this.examList = examList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<ExamItemBinding>(
            LayoutInflater.from(activity),
            R.layout.exam_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var exam = examList.get(position)
        holder.examItemBinding.exam = exam
        holder.examItemBinding.studentViewModel = studentViewModel

        var linearLayoutManager = LinearLayoutManager(activity)
        holder.examItemBinding.rvSubjectList.hasFixedSize()
        holder.examItemBinding.rvSubjectList.layoutManager = linearLayoutManager

        var examResultAdapter = ExamResultAdapter(activity, exam.examResult, studentViewModel)
        holder.examItemBinding.rvSubjectList.adapter = examResultAdapter
    }

    class ViewHolder(itemView: ExamItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var examItemBinding: ExamItemBinding

        init {
            this.examItemBinding = itemView
        }
    }

}