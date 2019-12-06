package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentProfileBinding
import com.app.schoolerpapp.model.Student

class ProfileFragment : Fragment() {
    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private var myChildDetails = ArrayList<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            myChildDetails = arguments!!.getParcelableArrayList<Student>("myChildDetails")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        if (myChildDetails.size > 0)
            fragmentProfileBinding.student = myChildDetails.get(0)

        return fragmentProfileBinding.root
    }
}