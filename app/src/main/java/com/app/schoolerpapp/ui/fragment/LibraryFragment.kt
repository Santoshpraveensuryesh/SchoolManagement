package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentLibraryBookBinding
import com.app.schoolerpapp.model.Book
import com.app.schoolerpapp.model.Teacher
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.BookAdapter
import com.app.schoolerpapp.ui.adapter.TeacherAdapter
import com.app.schoolerpapp.viewmodel.LibraryViewModel
import com.app.schoolerpapp.viewmodel.TeacherViewModel
import com.viewmodel.app.utility.AppUtils
import kotlinx.android.synthetic.main.fragment_library_book.view.*
import kotlinx.android.synthetic.main.fragment_teacher.view.*
import kotlinx.android.synthetic.main.toolbar.*

class LibraryFragment : Fragment() {
    private lateinit var txtTitle: TextView
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentLibraryFragment: FragmentLibraryBookBinding
    private lateinit var libraryViewModel: LibraryViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var bookAdapter: BookAdapter
    private var bookList = ArrayList<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity

        setHasOptionsMenu(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtTitle = activity.txt_title
        var param: RelativeLayout.LayoutParams =
            txtTitle.layoutParams as RelativeLayout.LayoutParams
        param.marginStart = resources.getDimensionPixelOffset(R.dimen._50sdp)
        txtTitle.layoutParams = param

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLibraryFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_library_book, container, false)
        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)
        fragmentLibraryFragment.libraryViewModel = libraryViewModel
        setView(fragmentLibraryFragment.root)
        return fragmentLibraryFragment.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_libraryBookList.setHasFixedSize(true)
        root.rv_libraryBookList.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var book = Book()
            book.bookTitle = "Book " + i
            book.publisher = "Test"
            book.author = "One"
            book.subject = "Test"
            book.rackNumber = "4"
            book.qty = "4"
            book.bookPrice = "800.00"
            book.postDate = "11-11-2010"
            bookList.add(book)
        }


        bookAdapter = BookAdapter(
            activity,
            bookList,
            libraryViewModel,
            object : BookAdapter.OnClickBookListener {
                override fun onBookClick(pos: Int) {
                    var bookDetailsFragment = BookDetailsFragment()
                    var bundle = Bundle()
                    bundle.putParcelable("book", bookList.get(pos))
                    bookDetailsFragment.arguments = bundle
                    AppUtils.replaceFragment(bookDetailsFragment, activity, R.id.container)
                }

            })
        root.rv_libraryBookList.adapter = bookAdapter


    }

    override fun onResume() {
        super.onResume()
        activity.txt_title.setText(activity.resources.getString(R.string.library_book))
        activity.showMenuIcon()
        var param: RelativeLayout.LayoutParams =
            txtTitle.layoutParams as RelativeLayout.LayoutParams
        param.marginStart = resources.getDimensionPixelOffset(R.dimen._50sdp)
        txtTitle.layoutParams = param
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.search_menu, menu)
        val search = menu!!.findItem(R.id.search)
        search.setVisible(true)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        search(searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                bookAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}