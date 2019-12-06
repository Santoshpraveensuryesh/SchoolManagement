package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentTimelineBinding
import com.app.schoolerpapp.ui.activity.DashBoardActivity

class TimeLineFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentTimelineBinding: FragmentTimelineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTimelineBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false)
        setView(fragmentTimelineBinding.root)
        return fragmentTimelineBinding.root
    }

    private fun setView(root: View) {

    }
}