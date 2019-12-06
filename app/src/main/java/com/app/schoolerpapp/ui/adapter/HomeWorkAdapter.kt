package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FeeItemBinding
import com.app.schoolerpapp.databinding.HomeWorkItemBinding
import com.app.schoolerpapp.model.Book
import com.app.schoolerpapp.model.Homework
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel

class HomeWorkAdapter() : RecyclerView.Adapter<HomeWorkAdapter.ViewHolder>(), Filterable {


    private lateinit var activity: DashBoardActivity
    private lateinit var homeWorkList: ArrayList<Homework>
    private lateinit var mFilteredList: ArrayList<Homework>
    private lateinit var onClickItemListener: OnClickItemListener
    private lateinit var studentViewModel: StudentViewModel

    constructor(
        activity: DashBoardActivity, homeWorkList: ArrayList<Homework>,
        studentViewModel: StudentViewModel,
        onClickItemListener: OnClickItemListener
    ) : this() {
        this.activity = activity
        this.studentViewModel = studentViewModel
        this.homeWorkList = homeWorkList
        this.mFilteredList = homeWorkList
        this.onClickItemListener = onClickItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<HomeWorkItemBinding>(
            LayoutInflater.from(activity),
            R.layout.home_work_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var homework = mFilteredList.get(position)
        holder.workItemBinding.homework = homework
        holder.workItemBinding.studentViewModel = studentViewModel

        holder.workItemBinding.root.setOnClickListener { onClickItemListener.onItemClick(position) }
        holder.workItemBinding.btnComplete.setOnClickListener {
            onClickItemListener.onWorkCompleteClick(position)
        }
    }

    class ViewHolder(itemView: HomeWorkItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var workItemBinding: HomeWorkItemBinding

        init {
            this.workItemBinding = itemView
        }
    }

    interface OnClickItemListener {
        fun onItemClick(pos: Int)
        fun onWorkCompleteClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = homeWorkList
                } else {
                    val filteredList = ArrayList<Homework>()
                    for (item in homeWorkList) {
                        if (item is Homework) {
                            if (item.subject.toLowerCase().contains(charString)
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
                mFilteredList = filterResults.values as ArrayList<Homework>
                notifyDataSetChanged()
            }
        }
    }


}