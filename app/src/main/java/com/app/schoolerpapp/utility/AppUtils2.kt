package com.viewmodel.app.utility

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import com.app.schoolerpapp.R



//import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
//import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;

object AppUtils2 {

    fun getBackgroundDrawable(pressedColor: Int, backgroundDrawable: Drawable): RippleDrawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(getPressedState(pressedColor), backgroundDrawable, null)
        } else null
    }

    fun applyRipple(view: View, activity: Activity) {
        val background = view.background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.background = getBackgroundDrawable(activity.resources.getColor(R.color.gray_light), background)
        }
    }

    fun getPressedState(pressedColor: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
    }

}
