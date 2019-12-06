package com.app.schoolerpapp.ui.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FeeItemBinding
import com.app.schoolerpapp.model.Fees
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.StudentViewModel


class FeeAdapter() : RecyclerView.Adapter<FeeAdapter.ViewHolder>() {
    private lateinit var activity: DashBoardActivity
    private lateinit var feesList: ArrayList<Fees>
    private lateinit var onClickFeesListener: OnClickFeesListener
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var fromMenu: String

    constructor(
        activity: DashBoardActivity, feesList: ArrayList<Fees>,
        studentViewModel:StudentViewModel,
        fromMenu:String,
        onClickFeesListener: OnClickFeesListener
    ) : this() {
        this.activity = activity
        this.studentViewModel = studentViewModel
        this.feesList = feesList
        this.fromMenu = fromMenu
        this.onClickFeesListener = onClickFeesListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = DataBindingUtil.inflate<FeeItemBinding>(
            LayoutInflater.from(activity),
            R.layout.fee_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var fees = feesList.get(position)
        holder.feeItemBinding.fees = fees
        holder.feeItemBinding.studentViewModel = studentViewModel

        var drawable: Drawable? = activity.resources.getDrawable(R.drawable.fees)
        drawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(drawable, Color.GREEN)
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_OVER)

        holder.feeItemBinding.btnStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);

        holder.feeItemBinding.root.setOnClickListener { onClickFeesListener.onFeeClick(position) }
        holder.feeItemBinding.btnStatus.setOnClickListener { onClickFeesListener.onPaymentClick(position) }

        if(fees.status.equals("0"))
        {
            holder.feeItemBinding.txtStatus.setText(activity.resources.getString(R.string.unpaid))
            holder.feeItemBinding.txtStatus.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.red), PorterDuff.Mode.SRC_OVER);
            if (fromMenu.equals("fromMenu"))
                holder.feeItemBinding.btnStatus.visibility= View.VISIBLE
            else
                holder.feeItemBinding.btnStatus.visibility= View.INVISIBLE
        }else if(fees.status.equals("1")){
            holder.feeItemBinding.txtStatus.setText(activity.resources.getString(R.string.paid2))
            holder.feeItemBinding.txtStatus.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.green), PorterDuff.Mode.SRC_OVER);
            holder.feeItemBinding.btnStatus.visibility=View.INVISIBLE
        }else if(fees.status.equals("2")){
            holder.feeItemBinding.txtStatus.setText(activity.resources.getString(R.string.partial))
            holder.feeItemBinding.txtStatus.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.colorAccent), PorterDuff.Mode.SRC_OVER);
            if (fromMenu.equals("fromMenu"))
                holder.feeItemBinding.btnStatus.visibility= View.VISIBLE
            else
                holder.feeItemBinding.btnStatus.visibility= View.INVISIBLE
        }



    }

    class ViewHolder(itemView: FeeItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var feeItemBinding: FeeItemBinding

        init {
            this.feeItemBinding = itemView
        }
    }

    interface OnClickFeesListener {
        fun onFeeClick(pos: Int)
        fun onPaymentClick(pos: Int)
    }
}