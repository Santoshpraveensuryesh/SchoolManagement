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
import com.app.schoolerpapp.databinding.FragmentSubjectBinding
import com.app.schoolerpapp.model.Subject
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.SubjectAdapter
import com.app.schoolerpapp.viewmodel.SubjectViewModel
import kotlinx.android.synthetic.main.fragment_subject.view.*
import kotlinx.android.synthetic.main.toolbar.*

class SubjectFragment : Fragment() {
    private lateinit var txtTitle: TextView
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentSubjectBinding: FragmentSubjectBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var subjectAdapter: SubjectAdapter
    private var teacherList = ArrayList<Subject>()
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
        fragmentSubjectBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subject, container, false)
        subjectViewModel = ViewModelProviders.of(this).get(SubjectViewModel::class.java)
        fragmentSubjectBinding.subjectViewNodel = subjectViewModel
        setView(fragmentSubjectBinding.root)
        return fragmentSubjectBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_subjectList.setHasFixedSize(true)
        root.rv_subjectList.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var subject = Subject()
            subject.teacherName = "Teacher " + i
            subject.subjectName = "Subject" + i
            subject.type = "Theory"
            teacherList.add(subject)
        }


        subjectAdapter = SubjectAdapter(
            activity,
            teacherList,
            subjectViewModel,
            object : SubjectAdapter.OnClickSubjectListener {
                override fun onSubjectClick(pos: Int) {

                }

            })
        root.rv_subjectList.adapter = subjectAdapter


    }

    override fun onResume() {
        super.onResume()
        activity.txt_title.setText(activity.resources.getString(R.string.subject_list))

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

                subjectAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
}