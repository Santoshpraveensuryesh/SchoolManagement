package com.app.schoolerpapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.schoolerpapp.R;
import com.app.schoolerpapp.model.StudentAttendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<StudentAttendance> attendanceList;
    private OnDateClick onDateClick;
    private Calendar calendarCurrent;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate,
                       List<StudentAttendance> attendanceList, OnDateClick onDateClick) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.attendanceList = attendanceList;
        mInflater = LayoutInflater.from(context);
        this.onDateClick = onDateClick;
        calendarCurrent = Calendar.getInstance();

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int displayDate = dateCal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int dayValueWeek = dateCal.get(Calendar.DAY_OF_WEEK);
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }

//        Toast.makeText(getContext(), "value of day-->" + dayValueWeek, Toast.LENGTH_SHORT).show();

        //Add day to calendar
        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        LinearLayout rlItem = (LinearLayout) view.findViewById(R.id.rl_item);
        if (displayMonth == currentMonth && displayYear == currentYear) {

            if (
                    (displayDate == calendarCurrent.get(Calendar.DAY_OF_MONTH) &&
                            (calendarCurrent.get(Calendar.MONTH) + 1) == displayMonth &&
                            displayYear == calendarCurrent.get(Calendar.YEAR) && pos == -1)
                            || (pos != -1 && pos == position)
            ) {
                rlItem.setBackground(getContext().getResources().getDrawable(R.drawable.round_oval_circle));
                cellNumber.setTextColor(getContext().getResources().getColor(android.R.color.white));
            } else {
                rlItem.setBackground(null);
                cellNumber.setTextColor(getContext().getResources().getColor(android.R.color.white));
            }
            if (dayValueWeek == 1) {
                cellNumber.setTextColor(getContext().getResources().getColor(android.R.color.white));
            }
//            else {
//                cellNumber.setTextColor(getContext().getResources().getColor(android.R.color.black));
//            }
            rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDateClick.clickDate(position);
                }
            });
//Add events to the calendar
            TextView eventIndicator = (TextView) view.findViewById(R.id.event_id);
            Calendar eventCalendar = Calendar.getInstance();
            boolean isAttend = true;
            for (int i = 0; i < attendanceList.size(); i++) {

                if (attendanceList.get(i).getDate() != null && !TextUtils.isEmpty(attendanceList.get(i).getDate())) {
                    eventCalendar.setTime(convertStringToDate(attendanceList.get(i).getDate()));
//            int month=Integer.parseInt(allEvents.get(i).getDate().split("-")[1]);
//            eventCalendar.set(Calendar.MONTH,month );
//            Log.e("months--->",month+ " displayMonth=="+displayMonth+"");
                    if (
                            dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) &&
                                    displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                                    && displayYear == eventCalendar.get(Calendar.YEAR)
                    ) {
                        if (attendanceList.get(i).getAttendanceType().equalsIgnoreCase("Abstent"))
                            rlItem.setBackground(getContext().getResources().getDrawable(R.color.red));
                        else if (attendanceList.get(i).getAttendanceType().equalsIgnoreCase("Late")) {
                            rlItem.setBackground(getContext().getResources().getDrawable(R.color.yellow));
                            cellNumber.setTextColor(getContext().getResources().getColor(R.color.black));
                        }
                        else if (attendanceList.get(i).getAttendanceType().equalsIgnoreCase("Half-day"))
                            rlItem.setBackground(getContext().getResources().getDrawable(R.color.orange));
                        else if (attendanceList.get(i).getAttendanceType().equalsIgnoreCase("Holiday"))
                            rlItem.setBackground(getContext().getResources().getDrawable(R.color.gray_dark));
                        else
                            rlItem.setBackground(getContext().getResources().getDrawable(R.color.green));
                        isAttend = false;

//                cellNumber.setTextColor(getContext().getResources().getColor(android.R.color.white));
//                eventIndicator.setBackgroundColor(getContext().getResources().getColor(R.color.gray_dark));
                    }
                }
            }
//            if (isAttend)
//                rlItem.setBackground(getContext().getResources().getDrawable(R.color.green));
        } else {
            cellNumber.setTextColor(getContext().getResources().getColor(R.color.gray_dark));
        }
        cellNumber.setText(String.valueOf(dayValue));

        return view;
    }

    private Date convertStringToDate(String dateInString) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    int pos = -1;

    public void selectPos(int position) {
        this.pos = position;
    }

    public void setAttendanceList(ArrayList<StudentAttendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public interface OnDateClick {
        void clickDate(int pos);
    }
}