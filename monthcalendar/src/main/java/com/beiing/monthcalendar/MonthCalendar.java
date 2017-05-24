package com.beiing.monthcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.beiing.monthcalendar.adapter.CalendarPagerAdapter;
import com.beiing.monthcalendar.adapter.WeekAdapter;
import com.beiing.monthcalendar.listener.GetViewHelper;
import com.beiing.monthcalendar.widget.WrapContentViewPager;

import org.joda.time.DateTime;

/**
 * Created by linechen on 2017/5/23.<br/>
 * 描述：
 * </br>
 */

public class MonthCalendar extends LinearLayout{

    private static final String TAG = "MonthCalendar";

    public static final int DAYS_OF_WEEK = 7;
    public static final int DAYS_OF_PAGE = 42;

    private int maxCount = 1000;
    private int centerPosition = maxCount / 2;

    private int headerHeight;
    private int headerBgColor;
    private int calendarHeight;


    private WrapContentViewPager viewPagerContent;
    private GetViewHelper getViewHelper;
    private CalendarPagerAdapter calendarPagerAdapter;

    public MonthCalendar(Context context) {
        this(context, null);
    }

    public MonthCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MonthCalendar);
        try {
            headerHeight = (int) ta.getDimension(R.styleable.MonthCalendar_mc_headerHeight, getResources().getDimension(R.dimen.calender_header_height));
            headerBgColor = ta.getColor(R.styleable.MonthCalendar_mc_headerBgColor, Color.WHITE);
            calendarHeight = (int) ta.getDimension(R.styleable.MonthCalendar_mc_calendarHeight, getResources().getDimension(R.dimen.calender_content_height));
        } finally {
            ta.recycle();
        }
    }

    private void initView() {
        setOrientation(VERTICAL);
        addHeaderView();
        addMonthView();
    }

    private void addHeaderView() {
        View header =  LayoutInflater.from(getContext()).inflate(R.layout.layout_calender_header, this, false);
        header.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, headerHeight));
        header.setBackgroundColor(headerBgColor);
        GridView weekGrid = (GridView) header.findViewById(R.id.grid_week);
        addView(header);
        weekGrid.setAdapter(new WeekAdapter(getViewHelper));
    }

    private void addMonthView() {
        View calendar = LayoutInflater.from(getContext()).inflate(R.layout.layout_calendar_content, this, false);
        calendar.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, calendarHeight));
        viewPagerContent = (WrapContentViewPager) calendar.findViewById(R.id.viewpager_calendar);
        addView(calendar);
        DateTime startDay = new DateTime(2017, 1, 24, 0, 0);
        startDay = startDay.minusDays(startDay.getDayOfMonth()- 1);
        startDay = startDay.minusDays(startDay.getDayOfWeek() % DAYS_OF_WEEK);
        calendarPagerAdapter = new CalendarPagerAdapter(getContext(), calendarHeight, maxCount, startDay.getYear(), startDay.getMonthOfYear(), getViewHelper);
        viewPagerContent.setAdapter(calendarPagerAdapter);
        viewPagerContent.setCurrentItem(centerPosition);
    }

    public void setGetViewHelper(GetViewHelper getViewHelper) {
        this.getViewHelper = getViewHelper;
        initView();
    }


}
