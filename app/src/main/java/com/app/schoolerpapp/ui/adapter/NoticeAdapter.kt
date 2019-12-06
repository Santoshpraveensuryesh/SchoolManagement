package com.app.schoolerpapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.NoticeItemBinding
import com.app.schoolerpapp.model.Notice
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.NoticeViewModel


class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private lateinit var activity: DashBoardActivity
    private lateinit var noticeList: ArrayList<Notice>
    private lateinit var noticeViewModel: NoticeViewModel
    private lateinit var onNoticeListener: OnNoticeListener

    constructor(
        activity: DashBoardActivity,
        noticeList: ArrayList<Notice>,
        noticeViewModel: NoticeViewModel,
        onNoticeListener: OnNoticeListener
    ) {
        this.activity = activity
        this.noticeList = noticeList
        this.noticeViewModel = noticeViewModel
        this.onNoticeListener = onNoticeListener
    }

    class ViewHolder(itemView: NoticeItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var noticeItemBinding: NoticeItemBinding

        init {
            noticeItemBinding = itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var noticeItemBinding = DataBindingUtil.inflate<NoticeItemBinding>(
            LayoutInflater.from(activity),
            R.layout.notice_item,
            parent,
            false
        )
        return ViewHolder(noticeItemBinding)
    }

    fun expand(v: View) {
        val matchParentMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(
            (v.getParent() as View).getWidth(),
            View.MeasureSpec.EXACTLY
        )
        val wrapContentMeasureSpec: Int =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight: Int = v.getMeasuredHeight()
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1
        v.setVisibility(View.VISIBLE)
        val a: Animation = object : Animation() {
            override protected fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                v.getLayoutParams().height =
                    if (interpolatedTime == 1f) ConstraintLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Expansion speed of 1dp/ms
        a.setDuration(((targetHeight / v.getContext().getResources().getDisplayMetrics().density)).toLong())
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight: Int = v.getMeasuredHeight()
        val a: Animation = object : Animation() {
            override protected fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                if (interpolatedTime == 1f) {
                    v.setVisibility(View.GONE)
                } else {
                    v.getLayoutParams().height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Collapse speed of 1dp/ms
        a.setDuration(((initialHeight / v.getContext().getResources().getDisplayMetrics().density)).toLong())
        v.startAnimation(a)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notice = noticeList.get(position)
        holder.noticeItemBinding.noticeViewModel = noticeViewModel
        holder.noticeItemBinding.notice = notice

        if (notice.isExpand) {
            holder.noticeItemBinding.txtNoticeDesc.visibility=View.VISIBLE
        } else {
            holder.noticeItemBinding.txtNoticeDesc.visibility=View.GONE
        }

        holder.noticeItemBinding.txtNoticeName.setOnClickListener {
            if (!notice.isExpand) {
                expand(holder.noticeItemBinding.txtNoticeDesc)
                notice.isExpand = true

            } else {
                collapse(holder.noticeItemBinding.txtNoticeDesc)
                notice.isExpand = false
            }
            onNoticeListener.onClick(position)
//            notifyItemChanged(position)
        }
    }
    interface OnNoticeListener{
        fun onClick(pos:Int)
    }
}