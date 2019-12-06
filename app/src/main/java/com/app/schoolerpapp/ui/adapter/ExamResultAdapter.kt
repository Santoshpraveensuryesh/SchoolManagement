package com.app.schoolerpapp.ui.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.ExamItemBinding
import com.app.schoolerpapp.databinding.FeeItemBinding
import com.app.schoolerpapp.databinding.SubjectResultItemBinding
import com.app.schoolerpapp.model.Exam
import com.app.schoolerpapp.model.ExamResult
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel

class ExamResultAdapter() : RecyclerView.Adapter<ExamResultAdapter.ViewHolder>() {
    private lateinit var activity: DashBoardActivity
    private lateinit var examResultList: ArrayList<ExamResult>
    private lateinit var studentViewModel: StudentViewModel

    constructor(
        activity: DashBoardActivity, examResultList: ArrayList<ExamResult>,
        studentViewModel: StudentViewModel
    ) : this() {
        this.activity = activity
        this.studentViewModel = studentViewModel
        this.examResultList = examResultList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<SubjectResultItemBinding>(
            LayoutInflater.from(activity),
            R.layout.subject_result_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return examResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var examResult = examResultList.get(position)
        holder.resultItemBinding.examResult = examResult
        holder.resultItemBinding.studentViewModel = studentViewModel

        if (examResult.getMarks < examResult.passMark) {
            holder.resultItemBinding.txtResultMarks.getBackground().setColorFilter(
                ContextCompat.getColor(
                    activity,
                    R.color.green
                ), PorterDuff.Mode.SRC_OVER
            )
            holder.resultItemBinding.txtResultMarks.setText(activity.resources.getString(R.string.fail))
        }
        else {
            holder.resultItemBinding.txtResultMarks.getBackground().setColorFilter(
                ContextCompat.getColor(activity, R.color.red),
                PorterDuff.Mode.SRC_OVER
            )
            holder.resultItemBinding.txtResultMarks.setText(activity.resources.getString(R.string.pass))
        }
    }

    class ViewHolder(itemView: SubjectResultItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var resultItemBinding: SubjectResultItemBinding

        init {
            this.resultItemBinding = itemView
        }
    }

}