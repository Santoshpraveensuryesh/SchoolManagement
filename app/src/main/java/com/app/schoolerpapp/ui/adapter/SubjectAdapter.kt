package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.SubjectItemBinding
import com.app.schoolerpapp.model.Subject
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.SubjectViewModel

class SubjectAdapter() : RecyclerView.Adapter<SubjectAdapter.ViewHolder>(), Filterable {


    private lateinit var activity: DashBoardActivity
    private lateinit var subjectList: ArrayList<Subject>
    private lateinit var mFilteredList: ArrayList<Subject>
    private lateinit var onClickListener: OnClickSubjectListener
    private lateinit var subjectViewModel: SubjectViewModel

    constructor(
        activity: DashBoardActivity,
        subjectList: ArrayList<Subject>,
        subjectViewModel: SubjectViewModel,
        onClickListener: OnClickSubjectListener
    ) : this() {
        this.activity = activity
        this.subjectViewModel = subjectViewModel
        this.subjectList = subjectList
        this.mFilteredList = subjectList
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<SubjectItemBinding>(
            LayoutInflater.from(activity),
            R.layout.subject_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var subject = mFilteredList.get(position)
        holder.subjectItemBinding.subject = subject
        holder.subjectItemBinding.subjectViewModel = subjectViewModel

    }

    class ViewHolder(itemView: SubjectItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var subjectItemBinding: SubjectItemBinding

        init {
            this.subjectItemBinding = itemView
        }
    }

    interface OnClickSubjectListener {
        fun onSubjectClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = subjectList
                } else {
                    val filteredList = ArrayList<Subject>()
                    for (item in subjectList) {
                        if (item is Subject) {
                            if (item.subjectName.toLowerCase().contains(charString)
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
                mFilteredList = filterResults.values as ArrayList<Subject>
                notifyDataSetChanged()
            }
        }
    }
}