package com.app.schoolerpapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentNoticeBoardBinding
import com.app.schoolerpapp.model.Notice
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.NoticeAdapter
import com.app.schoolerpapp.viewmodel.NoticeViewModel
import kotlinx.android.synthetic.main.fragment_notice_board.view.*
import kotlinx.android.synthetic.main.toolbar.*

class NoticeBoardFragment : Fragment() {
    private lateinit var activity: DashBoardActivity
    private lateinit var fragmentNoticeBoardBinding: FragmentNoticeBoardBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var noticeViewModel: NoticeViewModel
    private var noticeList = ArrayList<Notice>()
    private lateinit var noticeAdapter: NoticeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var param: RelativeLayout.LayoutParams =
            activity.txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginEnd = 0
        param.marginStart = 0
        activity.txt_title.layoutParams = param
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentNoticeBoardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notice_board, container, false)
        noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel::class.java)
        fragmentNoticeBoardBinding.noticeViewModel = noticeViewModel
        setView(fragmentNoticeBoardBinding.root)
        return fragmentNoticeBoardBinding.root
    }

    private fun setView(root: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_noticeboard.setHasFixedSize(true)
        root.rv_noticeboard.layoutManager = linearLayoutManager
        for (i in 0..100) {
            var notice = Notice()
            notice.noticeName = "Notice " + i
            notice.noticeDate = "22-11-2019"
            notice.noticeDesc = "Dear Parent,\n" +
                    "\n" +
                    "Greetings…\n" +
                    "\n" +
                    "As per our school calendar Nov-2019 Parent Teacher Meeting is due on Saturday Nov 09, 2019 from classes LKG to X. The timings are 11:00 AM to 02:00 PM. Please make sure that you attend the meeting in person to interact with the teachers concerned.\n" +
                    "\n" +
                    "We look forward to your valuable suggestions, so that all of us together bring out effective and productive overall changes to scale new heights.\n" +
                    "\n" +
                    "With warm regards.\n" +
                    "\n" +
                    "Mr. Anil Singh\n" +
                    "Principal "
            noticeList.add(notice)
        }
        noticeAdapter = NoticeAdapter(activity, noticeList, noticeViewModel,object : NoticeAdapter.OnNoticeListener{
            override fun onClick(pos:Int) {
                root.rv_noticeboard.smoothScrollToPosition(pos)
                noticeAdapter.notifyItemChanged(pos)
            }

        })
        root.rv_noticeboard.adapter = noticeAdapter
    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        activity.txt_title.setText(activity.resources.getString(R.string.notice_board))

    }
}