package com.app.schoolerpapp.ui.fragment

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.databinding.FragmentTimetableBinding
import com.app.schoolerpapp.model.TimeTable
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.viewmodel.ClassViewModel
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.fragment_timetable.view.*
import androidx.cardview.widget.CardView
import android.widget.LinearLayout
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.app.schoolerpapp.R
import com.app.schoolerpapp.model.SubjectClass
import kotlinx.android.synthetic.main.toolbar.*


class ClassTimetableFragment : Fragment(), View.OnClickListener {

    private var tCountSubject: Int = 0
    private var count = 0
    private var id11 = 1
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentTimetableBinding: FragmentTimetableBinding
    private lateinit var classViewModel: ClassViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var timeTableList = ArrayList<TimeTable>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
    }

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTimetableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timetable, container, false
        )
        classViewModel = ViewModelProviders.of(this).get(ClassViewModel::class.java)
        fragmentTimetableBinding.classViewModel = classViewModel
        setView(fragmentTimetableBinding.root)
        return fragmentTimetableBinding.root
    }

    private fun setView(root: View) {
        root.cons_week_day.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.black), PorterDuff.Mode.MULTIPLY);


        this.root = root
        for (i in 0..6) {
            var timeTable = TimeTable()
            timeTable.day = "day" + i
            if (i == 6) {
                for (j in 0..5) {
                    var subjectClass = SubjectClass()
                    subjectClass.subject = "Subject " + (i + 1) + (j + 1)
                    subjectClass.time = "Not Scheduled"
                    subjectClass.room = ""
                    timeTable.subjectList.add(subjectClass)
                }
            } else {
                for (j in 0..5) {
                    var subjectClass = SubjectClass()
                    subjectClass.subject = "Subject " + (i + 1) + (j + 1)
                    subjectClass.time = "10:00 AM - 10:30 AM"
                    subjectClass.room = "101"
                    timeTable.subjectList.add(subjectClass)
                }
            }
            timeTableList.add(timeTable)
        }




        loadClass(0)


//

//        root.txt_class_subject.setFactory(ViewSwitcher.ViewFactory {
//            // TODO Auto-generated method stub
//            // create a TextView
//            val t = TextView(activity)
//            // set the gravity of text to top and center horizontal
//            t.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
//            // set displayed text size
//            t.setTextColor(activity.resources.getColor(android.R.color.black))
//            t.textSize = activity.resources.getDimension(R.dimen._16ssp)
//            t
//        })
//        root.txt_class_subject.setCurrentText((timeTableList.get(count).subject).toString())

//        root.txt_class_time.setFactory(ViewSwitcher.ViewFactory {
//            // TODO Auto-generated method stub
//            // create a TextView
//            val t = TextView(activity)
//            // set the gravity of text to top and center horizontal
//            t.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
//            // set displayed text size
//            t.setTextColor(activity.resources.getColor(android.R.color.black))
//            t.textSize = activity.resources.getDimension(R.dimen._14ssp)
//            t
//        })
//        root.txt_class_time.setCurrentText((timeTableList.get(count).time).toString())
//
//        root.txt_class_room.setFactory(ViewSwitcher.ViewFactory {
//            // TODO Auto-generated method stub
//            // create a TextView
//            val t = TextView(activity)
//            // set the gravity of text to top and center horizontal
//            t.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
//            // set displayed text size
//            t.setTextColor(activity.resources.getColor(android.R.color.black))
//            t.textSize = activity.resources.getDimension(R.dimen._14ssp)
//            t
//        })
//        root.txt_class_room.setCurrentText((timeTableList.get(count).room).toString())
        root.txt_mon.setOnClickListener(this)
        root.txt_tue.setOnClickListener(this)
        root.txt_wed.setOnClickListener(this)
        root.txt_thr.setOnClickListener(this)
        root.txt_fri.setOnClickListener(this)
        root.txt_sat.setOnClickListener(this)
        root.txt_sun.setOnClickListener(this)

        root.img_arrow_pre.setOnClickListener(this)
        root.img_arrow_next.setOnClickListener(this)


        // set the animation type to ViewFlipper
        val `in` = AnimationUtils.loadAnimation(
            activity,
            R.anim.slide_in_left
        )
        val out = AnimationUtils.loadAnimation(
            activity,
            R.anim.slide_out_right
        )

        root.viewswitcher.setInAnimation(`in`)
        root.viewswitcher.setOutAnimation(out)
        root.img_arrow_pre.visibility = View.INVISIBLE
    }

    private fun loadClass(choose: Int) {
        count = 0
        tCountSubject = timeTableList.get(choose).subjectList.size
        root.viewswitcher.removeAllViews()
        for (subjectClass in timeTableList.get(choose).subjectList) {
            // create the object of ImageView
            // Set the CardView layoutParams

//            val constraintSet = ConstraintSet()
//            constraintSet.clone(root.cons_view)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp)
            )


            val cardView = CardView(activity)
            cardView.radius = 15f
            cardView.id = id11++
            cardView.setCardBackgroundColor(activity.resources.getColor(R.color.gray_light))
            cardView.setContentPadding(
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp),
                resources.getDimensionPixelSize(R.dimen._5sdp)
            )
            cardView.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.MULTIPLY);
            cardView.layoutParams = params
            cardView.cardElevation = 15f

            val params22 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            var linearLayout = LinearLayout(activity)
            linearLayout.layoutParams = params22
            linearLayout.orientation = LinearLayout.VERTICAL

// Initialize a new TextView to put in CardView
            val tv = TextView(activity)
            tv.layoutParams = params
            tv.text = activity.getString(R.string.subject)
            tv.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._10ssp)
            )
            tv.gravity = Gravity.CENTER
            tv.setTextColor(activity.resources.getColor(R.color.black))
            linearLayout.addView(tv)

            val tv11 = TextView(activity)
            tv11.layoutParams = params
            tv11.text = subjectClass.subject
            tv11.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._14ssp)
            )
            tv11.gravity = Gravity.CENTER
            tv11.setTextColor(activity.resources.getColor(R.color.gray_dark))
            linearLayout.addView(tv11)


            val tv22 = TextView(activity)
            tv22.layoutParams = params
            tv22.text = activity.getString(R.string.class_time)
            tv22.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._10ssp)
            )
            tv22.gravity = Gravity.CENTER
            tv22.setTextColor(activity.resources.getColor(R.color.black))
            linearLayout.addView(tv22)

            val tv33 = TextView(activity)
            tv33.layoutParams = params
            tv33.text = subjectClass.time
            tv33.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._12ssp)
            )
            tv33.gravity = Gravity.CENTER
            tv33.setTextColor(activity.resources.getColor(R.color.gray_dark))
            linearLayout.addView(tv33)

            val tv44 = TextView(activity)
            tv44.layoutParams = params
            tv44.text = activity.getString(R.string.class_room)
            tv44.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._10ssp)
            )
            tv44.gravity = Gravity.CENTER
            tv44.setTextColor(activity.resources.getColor(R.color.black))
            linearLayout.addView(tv44)

            val tv55 = TextView(activity)
            tv55.layoutParams = params
            tv55.text = subjectClass.room
            tv55.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                activity.resources.getDimension(R.dimen._12ssp)
            )
            tv55.gravity = Gravity.CENTER
            tv55.setTextColor(activity.resources.getColor(R.color.gray_dark))
            linearLayout.addView(tv55)


            if (choose == 6) {
                tv55.visibility = View.GONE
                tv44.visibility = View.GONE
//                tv33.visibility=View.GONE
                tv22.visibility = View.GONE
                tv.visibility = View.GONE
            }

            cardView.addView(linearLayout)
            //        constraintSet.connect(
//            cardView.getId(),
//            ConstraintSet.TOP,
//            root.cons_timetble.getId(),
//            ConstraintSet.TOP,
//            18
//        )
//        constraintSet.connect(
//            contactUs.getId(),
//            ConstraintSet.LEFT,
//            root.cons_timetble.getId(),
//            ConstraintSet.LEFT,
//            18
//        )
//
//        constraintSet.applyTo(root.cons_timetble)

            root.viewswitcher.addView(cardView)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_arrow_next -> {
                if (count < (tCountSubject - 1)) {
                    // set the animation type to ViewFlipper
                    val `in` = AnimationUtils.loadAnimation(
                        activity,
                        R.anim.slide_in_left
                    )
                    val out = AnimationUtils.loadAnimation(
                        activity,
                        R.anim.slide_out_right
                    )
                    root.viewswitcher.setInAnimation(`in`)
                    root.viewswitcher.setOutAnimation(out)
                    viewswitcher.showNext()
                    count++
                    if (count == (tCountSubject - 1)) {
                        img_arrow_next.visibility = View.INVISIBLE
                    }
                    img_arrow_pre.visibility = View.VISIBLE

                }
            }
            R.id.img_arrow_pre -> {
                if (count > 0) {
                    // set the animation type to ViewFlipper
                    val `in` = AnimationUtils.loadAnimation(
                        activity,
                        R.anim.slide_in_right
                    )
                    val out = AnimationUtils.loadAnimation(
                        activity,
                        R.anim.slide_out_left
                    )
                    root.viewswitcher.setInAnimation(`in`)
                    root.viewswitcher.setOutAnimation(out)
                    viewswitcher.showPrevious()
                    count--
                    if (count == 0) {
                        img_arrow_pre.visibility = View.INVISIBLE
                    }
                    img_arrow_next.visibility = View.VISIBLE
                }
            }
            R.id.txt_mon -> {
                txt_mon.setTextColor(resources.getColor(R.color.red))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT_BOLD)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(0)
            }
            R.id.txt_tue -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.red))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT_BOLD)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(1)
            }
            R.id.txt_wed -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.red))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT_BOLD)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(2)
            }
            R.id.txt_thr -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.red))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT_BOLD)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(3)
            }
            R.id.txt_fri -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.red))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT_BOLD)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(4)
            }
            R.id.txt_sat -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.red))
                txt_sun.setTextColor(resources.getColor(R.color.white))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT_BOLD)
                txt_sun.setTypeface(Typeface.DEFAULT)
                loadClass(5)
            }
            R.id.txt_sun -> {
                txt_mon.setTextColor(resources.getColor(R.color.white))
                txt_tue.setTextColor(resources.getColor(R.color.white))
                txt_wed.setTextColor(resources.getColor(R.color.white))
                txt_thr.setTextColor(resources.getColor(R.color.white))
                txt_fri.setTextColor(resources.getColor(R.color.white))
                txt_sat.setTextColor(resources.getColor(R.color.white))
                txt_sun.setTextColor(resources.getColor(R.color.red))
                txt_mon.setTypeface(Typeface.DEFAULT)
                txt_tue.setTypeface(Typeface.DEFAULT)
                txt_wed.setTypeface(Typeface.DEFAULT)
                txt_thr.setTypeface(Typeface.DEFAULT)
                txt_fri.setTypeface(Typeface.DEFAULT)
                txt_sat.setTypeface(Typeface.DEFAULT)
                txt_sun.setTypeface(Typeface.DEFAULT_BOLD)
                loadClass(6)


//                if (count > 6 || count < 6) {
//                    // Declare in and out animations and load them using AnimationUtils class
//                    val `in` = AnimationUtils.loadAnimation(
//                        activity,
//                        R.anim.slide_in_left
//                    )
//                    val out = AnimationUtils.loadAnimation(
//                        activity,
//                        R.anim.slide_out_right
//                    )
//
//                    // set the animation type to TextSwitcher
//                    viewswitcher.setInAnimation(`in`)
//                    viewswitcher.setOutAnimation(out)
////                    txt_class_subject.setInAnimation(`in`)
////                    txt_class_subject.setOutAnimation(out)
////                    txt_class_room.setInAnimation(`in`)
////                    txt_class_room.setOutAnimation(out)
//
//
//                    count = 6
////                    txt_class_subject.setText(timeTableList.get(count).subject.toString())
////                    txt_class_time.setText(timeTableList.get(count).time.toString())
////                    txt_class_room.setText(timeTableList.get(count).room.toString())
//                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.class_timetable))

//        activity.hideMenuIcon()
    }
}