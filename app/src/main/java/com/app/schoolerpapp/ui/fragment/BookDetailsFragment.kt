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
import com.app.schoolerpapp.model.Book
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.LibraryViewModel
import com.app.schoolerpapp.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.toolbar.*

class BookDetailsFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var book: Book
    private lateinit var fragmentBookDetailsBinding:FragmentBookDetailsBinding
    private lateinit var libraryViewModel: LibraryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity=getActivity() as DashBoardActivity
        if(arguments!=null){
            book= arguments!!.getParcelable<Book>("book")!!
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
        fragmentBookDetailsBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_book_details,container,false)
        libraryViewModel=ViewModelProviders.of(this).get(LibraryViewModel::class.java)
        fragmentBookDetailsBinding.libraryViewModel=libraryViewModel
        fragmentBookDetailsBinding.book=book
        return fragmentBookDetailsBinding.root
    }

    override fun onResume() {
        super.onResume()
        activity.hideMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.book_details))
    }
}