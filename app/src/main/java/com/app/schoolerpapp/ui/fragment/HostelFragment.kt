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
import com.app.schoolerpapp.databinding.FragmentHostelBinding
import com.app.schoolerpapp.model.Hostel
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.HostelAdapter
import com.app.schoolerpapp.viewmodel.HostelViewModel
import kotlinx.android.synthetic.main.fragment_hostel.view.*
import kotlinx.android.synthetic.main.fragment_subject.view.*
import kotlinx.android.synthetic.main.toolbar.*

class HostelFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentHostelBinding: FragmentHostelBinding
    private lateinit var hostelViewModel: HostelViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var hostelAdapter: HostelAdapter
    private lateinit var txtTitle: TextView
    private var hostelList = ArrayList<Hostel>()
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
        fragmentHostelBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hostel, container, false)
        hostelViewModel = ViewModelProviders.of(this).get(HostelViewModel::class.java)
        fragmentHostelBinding.hostelViewModel = hostelViewModel
        setView(fragmentHostelBinding.root)
        return fragmentHostelBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_hostelList.setHasFixedSize(true)
        root.rv_hostelList.layoutManager = linearLayoutManager

        for (i in 1..100) {
            var hostel = Hostel()
            hostel.hostelName = "Rajput hotel " + i
            hostel.hostelType = "Boys"
            hostel.hostelAddress = "Kalani nagar"
            hostel.hostelIntake = "1000"
            hostelList.add(hostel)
        }


        hostelAdapter = HostelAdapter(
            activity,
            hostelList,
            hostelViewModel,
            object : HostelAdapter.OnClickHostelListener {
                override fun onHostelClick(pos: Int) {

                }

            })
        root.rv_hostelList.adapter = hostelAdapter


    }

    override fun onResume() {
        super.onResume()
        activity.txt_title.setText(activity.resources.getString(R.string.hostel_list))

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

                hostelAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
}