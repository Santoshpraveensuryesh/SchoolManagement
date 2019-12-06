package com.app.schoolerpapp.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.SubmenuItemBinding
import com.app.schoolerpapp.model.UserSubMenuItem
import com.app.schoolerpapp.ui.activity.DashBoardActivity

class SubMenuAdapter() : RecyclerView.Adapter<SubMenuAdapter.ViewHolder>() {
    private lateinit var activity: Activity
    private lateinit var subMenuList: ArrayList<UserSubMenuItem>
    private lateinit var onSubItemClickListener: OnSubItemClickListener

    constructor(
        activity: Activity, subMenuList: ArrayList<UserSubMenuItem>,
        onSubItemClickListener: OnSubItemClickListener
    ) : this() {
        this.activity = activity
        this.onSubItemClickListener = onSubItemClickListener
        this.subMenuList = subMenuList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var submenuItemBinding = DataBindingUtil.inflate<SubmenuItemBinding>(
            LayoutInflater.from(activity),
            R.layout.submenu_item,
            parent,
            false
        )

        return ViewHolder(submenuItemBinding)
    }

    override fun getItemCount(): Int {
        return subMenuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var subMenuItem = subMenuList.get(position)
        holder.submenuItemBinding.txtSubmenu.setText(subMenuItem.subMenuName)

        holder.submenuItemBinding.consSubmenuItem.setOnClickListener {
            onSubItemClickListener.onSubItemClick(position)
        }

    }

    class ViewHolder(itemView: SubmenuItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var submenuItemBinding: SubmenuItemBinding

        init {
            this.submenuItemBinding = itemView
        }
    }

    interface OnSubItemClickListener {
        fun onSubItemClick(pos: Int)
    }
}