package com.app.schoolerpapp.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.schoolerpapp.R;
import com.app.schoolerpapp.model.StudentAttendance;
import com.app.schoolerpapp.ui.adapter.GridAdapter;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView txtMonth;
    private GridView calendarGridView;
    //    private Button addEventButton;
    private static int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private String[] monthArr;

    public CalendarCustomView(Context context) {
        super(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
//        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        txtMonth = (TextView) view.findViewById(R.id.txt_month);
        monthArr = new String[]{context.getString(R.string.jan),
                context.getString(R.string.feb),
                context.getString(R.string.mar),
                context.getString(R.string.apr),
                context.getString(R.string.may),
                context.getString(R.string.jun),
                context.getString(R.string.jul),
                context.getString(R.string.aug),
                context.getString(R.string.sep),
                context.getString(R.string.oct),
                context.getString(R.string.nov),
                context.getString(R.string.dec)};
        txtMonth.setText(monthArr[cal.get(Calendar.MONTH)] + " , " + cal.get(Calendar.YEAR));
//        currentDate = (TextView)view.findViewById(R.id.display_current_date);
//        addEventButton = (Button)view.findViewById(R.id.add_calendar_event);
        calendarGridView = (GridView) view.findViewById(R.id.calendar_grid);

    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                txtMonth.setText(monthArr[cal.get(Calendar.MONTH)] + ", " + cal.get(Calendar.YEAR));
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                txtMonth.setText(monthArr[cal.get(Calendar.MONTH)] + ", " + cal.get(Calendar.YEAR));
                setUpCalendarAdapter();
            }
        });
    }

    private void setGridCellClickEvents() {
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date mDate = dayValueInCells.get(position);
                Calendar dateCal = Calendar.getInstance();
                dateCal.setTime(mDate);
                int month = dateCal.get(Calendar.MONTH);
                int year = dateCal.get(Calendar.YEAR);
                int date = dateCal.get(Calendar.DAY_OF_MONTH);
                mAdapter.selectPos(position);
                mAdapter.notifyDataSetChanged();
                listener.onDateClicked(year, month, date);
            }
        });
    }

    List<Date> dayValueInCells;

    private void setUpCalendarAdapter() {
        dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        if (firstDayOfTheMonth <= 4) MAX_CALENDAR_COLUMN = 35;
        else MAX_CALENDAR_COLUMN = 42;
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {

            //Check for  next month start with sun
            Calendar mCal2 = (Calendar) cal.clone();
            mCal2.add(Calendar.MONTH, 1);

            mCal2.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfTheMonth2 = mCal2.get(Calendar.DAY_OF_WEEK) - 1;
//            mCal2.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth2);

            Log.d(TAG, "firstdayofmonthnext" + firstDayOfTheMonth2);
            if (firstDayOfTheMonth2 == 0) {
                MAX_CALENDAR_COLUMN = 35;
            }
            //
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
//        currentDate.setText(sDate);
        mAdapter = new GridAdapter(context, dayValueInCells, cal, attendanceList, new GridAdapter.OnDateClick() {
            @Override
            public void clickDate(int position) {
                Date mDate = dayValueInCells.get(position);
                Calendar dateCal = Calendar.getInstance();
                dateCal.setTime(mDate);
                int month = dateCal.get(Calendar.MONTH);
                int year = dateCal.get(Calendar.YEAR);
                int date = dateCal.get(Calendar.DAY_OF_MONTH);
                mAdapter.selectPos(position);
                mAdapter.notifyDataSetChanged();
                listener.onDateClicked(year, month, date);
            }
        });
        calendarGridView.setAdapter(mAdapter);
    }

    public void setModified(int year, int monthOfYear, int dayOfMonth
//            , ArrayList<StudentAttendance> calendarEventList
    ) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        setUpCalendarAdapter();
    }

    public void setEvent(ArrayList<StudentAttendance> studentAttendanceList) {
        ArrayList<StudentAttendance> eventArrayList = new ArrayList<>();
        if (studentAttendanceList != null) {


//            for (StudentAttendance calendarEvent : studentAttendanceList) {
//                int year = cal.get(Calendar.YEAR);
//                String month = (cal.get(Calendar.MONTH) + 1) >= 10 ? (cal.get(Calendar.MONTH) + 1) + "" : "0" + (cal.get(Calendar.MONTH) + 1) + "";
//                String dayOfMonth = (cal.get(Calendar.DAY_OF_MONTH)) >= 10 ? (cal.get(Calendar.DAY_OF_MONTH)) + "" : "0" + (cal.get(Calendar.DAY_OF_MONTH)) + "";
//                Log.e("date-->", year + ".." + month + ".." + dayOfMonth + cal.getTime());
//                calendarEvent.startDate = convertStringToDate(calendarEvent.getStart());
////            if(calendarEvent.getStart().split("-")[1].equalsIgnoreCase(month)){
//                eventArrayList.add(calendarEvent);
////            }
//            }
        }

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

    private DateItemClick listener;
    private ArrayList<StudentAttendance> attendanceList = new ArrayList<>();

    public void setListener(DateItemClick listener) {
        this.listener = listener;
    }

    public void setList(@NotNull ArrayList<StudentAttendance> attendanceList) {
        this.attendanceList = attendanceList;
        mAdapter.setAttendanceList(attendanceList);
        mAdapter.notifyDataSetChanged();
    }

    public interface DateItemClick {
        void onDateClicked(int y, int m, int d);
    }
}
