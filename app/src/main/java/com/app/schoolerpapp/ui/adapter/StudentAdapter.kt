package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.StudentItemBinding
import com.app.schoolerpapp.model.Student
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel

class StudentAdapter() : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private lateinit var activity: DashBoardActivity
    private lateinit var studentList: ArrayList<Student>
    private lateinit var onClickStudent: OnClickStudent
    private lateinit var studentViewModel: StudentViewModel

    constructor(
        activity: DashBoardActivity,
        studentList: ArrayList<Student>,
        studentViewModel:StudentViewModel,
        onClickStudent: OnClickStudent
    ) : this() {
        this.activity = activity
        this.studentList = studentList
        this.studentViewModel = studentViewModel
        this.onClickStudent = onClickStudent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var studentItemBinding = DataBindingUtil.inflate<StudentItemBinding>(
            LayoutInflater.from(activity), R.layout.student_item,
            parent,
            false
        )
        return ViewHolder(studentItemBinding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var student = studentList.get(position)
        holder.studentItemBinding.studentViewModel=studentViewModel
        holder.studentItemBinding.student=student
    }

    class ViewHolder(studentItemBinding: StudentItemBinding) :
        RecyclerView.ViewHolder(studentItemBinding.root) {
        var studentItemBinding: StudentItemBinding

        init {
            this.studentItemBinding = studentItemBinding
        }
    }

    public interface OnClickStudent {
        fun onClick(pos: Int)
    }
}