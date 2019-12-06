package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.HostelItemBinding
import com.app.schoolerpapp.databinding.SubjectItemBinding
import com.app.schoolerpapp.model.Hostel
import com.app.schoolerpapp.model.Subject
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.HostelViewModel

class HostelAdapter() : RecyclerView.Adapter<HostelAdapter.ViewHolder>(), Filterable {


    private lateinit var activity: DashBoardActivity
    private lateinit var hostelList: ArrayList<Hostel>
    private lateinit var mFilteredList: ArrayList<Hostel>
    private lateinit var onClickListener: OnClickHostelListener
    private lateinit var hostelViewModel: HostelViewModel

    constructor(
        activity: DashBoardActivity,
        hostelList: ArrayList<Hostel>,
        hostelViewModel: HostelViewModel,
        onClickListener: OnClickHostelListener
    ) : this() {
        this.activity = activity
        this.hostelViewModel = hostelViewModel
        this.hostelList = hostelList
        this.mFilteredList = hostelList
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<HostelItemBinding>(
            LayoutInflater.from(activity),
            R.layout.hostel_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var hostel = mFilteredList.get(position)
        holder.hostelItemBinding.hostel = hostel
        holder.hostelItemBinding.hostelViewModel = hostelViewModel

    }

    class ViewHolder(itemView: HostelItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var hostelItemBinding: HostelItemBinding

        init {
            this.hostelItemBinding = itemView
        }
    }

    interface OnClickHostelListener {
        fun onHostelClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = hostelList
                } else {
                    val filteredList = ArrayList<Hostel>()
                    for (item in hostelList) {
                        if (item is Hostel) {
                            if (item.hostelName.toLowerCase().contains(charString)
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
                mFilteredList = filterResults.values as ArrayList<Hostel>
                notifyDataSetChanged()
            }
        }
    }
}