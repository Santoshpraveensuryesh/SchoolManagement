package com.app.schoolerpapp.ui.fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentFeeDetailsBinding
import com.app.schoolerpapp.model.Fees
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.fragment_fee_details.view.*
import kotlinx.android.synthetic.main.toolbar.*

class FeeDetailsFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private var fees=Fees()
    private lateinit var fragmentFeeDetailsBinding: FragmentFeeDetailsBinding
    private lateinit var studentViewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        if (arguments != null) {
            fees = arguments!!.getParcelable<Fees>("fees")!!
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
        fragmentFeeDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fee_details, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        fragmentFeeDetailsBinding.studentViewModel = studentViewModel
        fragmentFeeDetailsBinding.fees = fees

        setView(fragmentFeeDetailsBinding.root)
        return fragmentFeeDetailsBinding.root
    }

    private fun setView(root: View) {
        if (fees.status.equals("0")) {
            root.txt_fee_status11.setText(activity.resources.getString(R.string.unpaid))
            root.txt_fee_status11.getBackground().setColorFilter(
                ContextCompat.getColor(activity, R.color.red),
                PorterDuff.Mode.SRC_OVER
            )
            root.btn_status.visibility= View.VISIBLE

        } else if (fees.status.equals("1")) {
            root.txt_fee_status11.setText(activity.resources.getString(R.string.paid2))
            root.txt_fee_status11.getBackground().setColorFilter(
                ContextCompat.getColor(activity, R.color.green),
                PorterDuff.Mode.SRC_OVER
            )
            root.btn_status.visibility= View.INVISIBLE

        } else if (fees.status.equals("2")) {
            root.txt_fee_status11.setText(activity.resources.getString(R.string.partial))
            root.txt_fee_status11.getBackground().setColorFilter(
                ContextCompat.getColor(activity, R.color.colorAccent),
                PorterDuff.Mode.SRC_OVER
            )
            root.btn_status.visibility= View.VISIBLE

        }
        var drawable: Drawable? = activity.resources.getDrawable(R.drawable.fees)
        drawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(drawable, Color.GREEN)
        root.btn_status.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    override fun onResume() {
        super.onResume()
        activity.hideMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.fee_details))
    }
}