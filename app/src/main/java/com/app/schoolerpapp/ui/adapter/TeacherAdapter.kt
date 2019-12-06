package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentTeacherBinding
import com.app.schoolerpapp.databinding.TeacherItemBinding
import com.app.schoolerpapp.model.Teacher
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.TeacherViewModel

class TeacherAdapter() : RecyclerView.Adapter<TeacherAdapter.ViewHolder>(), Filterable {


    private lateinit var activity: DashBoardActivity
    private lateinit var teacherList: ArrayList<Teacher>
    private lateinit var mFilteredList: ArrayList<Teacher>
    private lateinit var onClickListener: OnClickTeacherListener
    private lateinit var teacherViewModel: TeacherViewModel

    constructor(
        activity: DashBoardActivity,
        teacherList: ArrayList<Teacher>,
        teacherViewModel: TeacherViewModel,
        onClickListener: OnClickTeacherListener
    ) : this() {
        this.activity = activity
        this.teacherViewModel = teacherViewModel
        this.teacherList = teacherList
        this.mFilteredList = teacherList
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<TeacherItemBinding>(
            LayoutInflater.from(activity),
            R.layout.teacher_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var teacher = mFilteredList.get(position)
        holder.teacherItemBinding.teacher = teacher
        holder.teacherItemBinding.teacherViewModel = teacherViewModel

    }

    class ViewHolder(itemView: TeacherItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var teacherItemBinding: TeacherItemBinding

        init {
            this.teacherItemBinding = itemView
        }
    }

    interface OnClickTeacherListener {
        fun onTeacherClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = teacherList
                } else {
                    val filteredList = ArrayList<Teacher>()
                    for (item in teacherList) {
                        if (item is Teacher) {
                            if (item.teacherName.toLowerCase().contains(charString)
                            ) {
                                filteredList.add(item)
                            }
                        }
                    }
                    mFilteredList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                mFilteredList = filterResults.values as ArrayList<Teacher>
                notifyDataSetChanged()
            }
        }
    }
}