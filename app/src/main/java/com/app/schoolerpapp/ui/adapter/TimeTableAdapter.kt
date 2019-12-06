//package com.app.schoolerpapp.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Filter
//import android.widget.Filterable
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.app.schoolerpapp.R
//import com.app.schoolerpapp.databinding.LibraryBookItemBinding
//import com.app.schoolerpapp.model.Book
//import com.app.schoolerpapp.model.Teacher
//import com.app.schoolerpapp.ui.activity.DashBoardActivity
//import com.app.schoolerpapp.viewmodel.LibraryViewModel
//
//class TimeTableAdapter() : RecyclerView.Adapter<TimeTableAdapter.ViewHolder>(), Filterable {
//
//
//    private lateinit var activity: DashBoardActivity
//    private lateinit var bookList: ArrayList<Book>
//    private lateinit var mFilteredList: ArrayList<Book>
//    private lateinit var onClickListener: OnClickBookListener
//    private lateinit var libraryViewModel: LibraryViewModel
//
//    constructor(
//        activity: DashBoardActivity,
//        bookList: ArrayList<Book>,
//        libraryViewModel: LibraryViewModel,
//        onClickListener: OnClickBookListener
//    ) : this() {
//        this.activity = activity
//        this.libraryViewModel = libraryViewModel
//        this.bookList = bookList
//        this.mFilteredList = bookList
//        this.onClickListener = onClickListener
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        var view = DataBindingUtil.inflate<LibraryBookItemBinding>(
//            LayoutInflater.from(activity),
//            R.layout.library_book_item,
//            parent,
//            false
//        )
//
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return mFilteredList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var book = mFilteredList.get(position)
//        holder.bookItemBinding.book = book
//        holder.bookItemBinding.libraryViewModel = libraryViewModel
//        holder.bookItemBinding.root.setOnClickListener { onClickListener.onBookClick(position)}
//    }
//
//    class ViewHolder(itemView: LibraryBookItemBinding) : RecyclerView.ViewHolder(itemView.root) {
//        var bookItemBinding: LibraryBookItemBinding
//
//        init {
//            this.bookItemBinding = itemView
//        }
//    }
//
//    interface OnClickBookListener {
//        fun onBookClick(pos: Int)
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//
//                val charString = charSequence.toString()
//
//                if (charString.isEmpty()) {
//
//                    mFilteredList = bookList
//                } else {
//                    val filteredList = ArrayList<Book>()
//                    for (item in bookList) {
//                        if (item is Book) {
//                            if (item.bookTitle.toLowerCase().contains(charString)
//                            ) {
//                                filteredList.add(item)
//                            }
//                        }
//                    }
//                    mFilteredList = filteredList
//                }
//
//                val filterResults = FilterResults()
//                filterResults.values = mFilteredList
//                return filterResults
//            }
//
//            override fun publishResults(
//                charSequence: CharSequence,
//                filterResults: Filter.FilterResults
//            ) {
//                mFilteredList = filterResults.values as ArrayList<Book>
//                notifyDataSetChanged()
//            }
//        }
//    }
//}