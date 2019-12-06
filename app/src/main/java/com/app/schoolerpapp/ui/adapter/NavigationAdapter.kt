package com.viewmodel.app.ui.adapter

/**
 * Created by impetrosys on 07/9/19.
 */

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.MenuItemBinding
import com.app.schoolerpapp.model.UserMenuItem
import com.app.schoolerpapp.ui.adapter.SubMenuAdapter
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat


class NavigationAdapter() :
    RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {
    private var isClick: Boolean = false
    private lateinit var activity: Activity
    private lateinit var menuItemArray: ArrayList<UserMenuItem>
    private lateinit var menuItemCLickListener: MenuItemCLickListener

    constructor(
        activity: Activity,
        menuItemArray: ArrayList<UserMenuItem>,
        menuItemCLickListener: MenuItemCLickListener
    ) : this() {
        this.activity = activity
        this.menuItemArray = menuItemArray
        this.menuItemCLickListener = menuItemCLickListener
    }

    class ViewHolder(itemView: MenuItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var txtMenu: TextView
        var consMenuItem: ConstraintLayout
        var imgDownArrow: ImageView
        var imgMenuIcon: ImageView
        var rvSubmenuList: RecyclerView
        var view: View

        init {
            view = itemView.root
            txtMenu = itemView.root.findViewById(R.id.txt_menu)
            consMenuItem = itemView.root.findViewById(R.id.cons_menu_item)
            imgDownArrow = itemView.root.findViewById(R.id.img_down_arrow)
            imgMenuIcon = itemView.root.findViewById(R.id.img_menu_icon)
            rvSubmenuList = itemView.root.findViewById(R.id.rv_submenuList)
        }
    }

    override fun getItemCount(): Int {
        return menuItemArray?.size!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        var menuItem = menuItemArray.get(pos)
        viewHolder.txtMenu.setText(menuItem.menuName)
        viewHolder.imgMenuIcon.setImageResource(menuItem.icon)
        if (menuItem.seleted) {
            viewHolder.consMenuItem.setBackgroundColor(activity!!.getResources().getColor(android.R.color.black))
            viewHolder.txtMenu.setTextColor(activity!!.resources.getColor(android.R.color.white))
        } else {
            viewHolder.consMenuItem.setBackgroundColor(activity!!.getResources().getColor(android.R.color.white))
            viewHolder.txtMenu.setTextColor(activity!!.resources.getColor(android.R.color.black))
        }

        var linearLayoutManager = LinearLayoutManager(activity)
        viewHolder.rvSubmenuList.setHasFixedSize(true)
        viewHolder.rvSubmenuList.layoutManager = linearLayoutManager
        var subMenuAdapter = SubMenuAdapter(
            activity,
            menuItem.subCategory,
            object : SubMenuAdapter.OnSubItemClickListener {
                override fun onSubItemClick(subPos: Int) {
                    menuItemCLickListener.onItemCLick(pos, subPos)
                }

            })
        viewHolder.rvSubmenuList.adapter = subMenuAdapter
        if (menuItem.visible) {
            viewHolder.rvSubmenuList.visibility = View.VISIBLE
            val slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
//            if (isClick)
//                viewHolder.rvSubmenuList.startAnimation(slideUp)
            isClick = false
            viewHolder.imgDownArrow.setBackground(activity.resources.getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp))
//            viewHolder.imgDownArrow.setColorFilter(
//                ContextCompat.getColor(
//                    activity,
//                    android.R.color.white
//                )
//            )
            viewHolder.imgDownArrow.setColorFilter(ContextCompat.getColor(activity, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

            viewHolder.imgMenuIcon.setColorFilter(ContextCompat.getColor(activity, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

//            viewHolder.view.setBackgroundColor(activity.getResources().getColor(android.R.color.black))
        } else {
            viewHolder.rvSubmenuList.visibility = View.GONE
            viewHolder.view.setBackgroundColor(0)
            viewHolder.imgDownArrow.setBackground(activity.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            viewHolder.imgMenuIcon.setColorFilter(ContextCompat.getColor(activity, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);

            viewHolder.imgDownArrow.setColorFilter(ContextCompat.getColor(activity, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);

        }
        if (pos == 7 || pos == 9 || pos == 10 || pos == 11 || pos == 12 || pos == 13) {
            viewHolder.imgDownArrow.visibility = View.INVISIBLE
        } else {
            viewHolder.imgDownArrow.visibility = View.VISIBLE
        }
        viewHolder.consMenuItem.setOnClickListener(View.OnClickListener {
            isClick = true
            for (k in menuItemArray.indices) {

                if (pos == k) {
                    menuItemArray.get(pos).seleted = true
                    menuItemArray.get(pos).visible = true

                } else {
                    menuItemArray.get(k).seleted = false
                    menuItemArray.get(k).visible = false

                }
            }
            if (pos == 7 || pos == 9 || pos == 10 || pos == 11 || pos == 12 || pos == 13)
                menuItemCLickListener.onItemCLick(pos, -1)

            notifyDataSetChanged()
        })


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<MenuItemBinding>(
            LayoutInflater.from(activity),
            R.layout.menu_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    interface MenuItemCLickListener {
        fun onItemCLick(pos: Int, subPos: Int)
    }

}