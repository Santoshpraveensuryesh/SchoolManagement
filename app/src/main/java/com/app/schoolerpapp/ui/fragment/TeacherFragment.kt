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
import com.app.schoolerpapp.databinding.FragmentTeacherBinding
import com.app.schoolerpapp.model.Teacher
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.TeacherAdapter
import com.app.schoolerpapp.viewmodel.TeacherViewModel
import kotlinx.android.synthetic.main.fragment_teacher.view.*
import kotlinx.android.synthetic.main.toolbar.*

class TeacherFragment : Fragment() {
    private lateinit var txtTitle: TextView
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentTeacherBinding: FragmentTeacherBinding
    private lateinit var teacherViewModel: TeacherViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var teacherAdapter: TeacherAdapter
    private var teacherList = ArrayList<Teacher>()
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
        fragmentTeacherBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_teacher, container, false)
        teacherViewModel = ViewModelProviders.of(this).get(TeacherViewModel::class.java)
        fragmentTeacherBinding.teacherViewModel = teacherViewModel
        setView(fragmentTeacherBinding.root)
        return fragmentTeacherBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_teacherList.setHasFixedSize(true)
        root.rv_teacherList.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var teacher = Teacher()
            teacher.teacherName = "Teacher " + i
            teacher.teacheremail = "teacher@gmail.com"
            teacher.teacherdob = "10-12_1970"
            teacher.teacherphone = "9982381292"
            teacherList.add(teacher)
        }


        teacherAdapter = TeacherAdapter(
            activity,
            teacherList,
            teacherViewModel,
            object : TeacherAdapter.OnClickTeacherListener {
                override fun onTeacherClick(pos: Int) {

                }
            })
        root.rv_teacherList.adapter = teacherAdapter


    }

    override fun onResume() {
        super.onResume()
        activity.txt_title.setText(activity.resources.getString(R.string.teachers))

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

                teacherAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
}