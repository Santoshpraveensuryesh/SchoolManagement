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
import com.app.schoolerpapp.databinding.FragmentHomeWorkBinding
import com.app.schoolerpapp.model.Homework
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.HomeWorkAdapter
import com.app.schoolerpapp.viewmodel.StudentViewModel
import com.viewmodel.app.utility.AppUtils
import kotlinx.android.synthetic.main.fragment_home_work.view.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeWorkFragment : Fragment() {
    private lateinit var txtTitle: TextView
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentHomeWorkBinding: FragmentHomeWorkBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var homeWorkAdapter: HomeWorkAdapter
    private var homeWorkList = ArrayList<Homework>()
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
        param.leftMargin = resources.getDimensionPixelOffset(R.dimen._50sdp)
        txtTitle.layoutParams = param
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeWorkBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_work, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        fragmentHomeWorkBinding.studentViewModel = studentViewModel
        setView(fragmentHomeWorkBinding.root)
        return fragmentHomeWorkBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_homeworkList.setHasFixedSize(true)
        root.rv_homeworkList.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var homeWork = Homework()
            homeWork.studentClass = "Class " + i
            homeWork.section = "B"
            homeWork.subject = "Mathematics"
            homeWork.homeworkDate = "11-11-2019"
            homeWork.submissionDate = "21-11-2019"
            homeWork.evaluationDate = "26-11-2019"
            homeWork.examDate = "30-11-2019"
            homeWork.status = "Incomplete"
            homeWorkList.add(homeWork)
        }


        homeWorkAdapter = HomeWorkAdapter(
            activity,
            homeWorkList,
            studentViewModel,
            object : HomeWorkAdapter.OnClickItemListener {
                override fun onItemClick(pos: Int) {
                    var hwDetailsFragment = HomeWorkDetailsFragment()
                    var bundle = Bundle()
                    bundle.putParcelable("homework", homeWorkList.get(pos))
                    hwDetailsFragment.arguments = bundle
                    AppUtils.replaceFragment(hwDetailsFragment, activity, R.id.container)
                }

                override fun onWorkCompleteClick(pos: Int) {
                }
            })
        root.rv_homeworkList.adapter = homeWorkAdapter


    }

    override fun onResume() {
        super.onResume()
        txtTitle.setText(activity.resources.getString(R.string.homework))
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

                homeWorkAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}