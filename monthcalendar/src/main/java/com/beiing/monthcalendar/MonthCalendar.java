package com.beiing.monthcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.beiing.monthcalendar.adapter.CalendarPagerAdapter;
import com.beiing.monthcalendar.adapter.WeekAdapter;
import com.beiing.monthcalendar.listener.CustomPagerChandeListender;
import com.beiing.monthcalendar.listener.GetViewHelper;
import com.beiing.monthcalendar.listener.OnDateSelectListener;
import com.beiing.monthcalendar.listener.OnMonthChangeListener;
import com.beiing.monthcalendar.listener.OnOtherMonthSelectListener;

import org.joda.time.DateTime;

/**
 * Created by linechen on 2017/5/23.<br/>
 * 描述：
 * </br>
 */

public class MonthCalendar extends LinearLayout{

    private static final String TAG = "MonthCalendar";

    public static final int DAYS_OF_WEEK = 7;

    public static final int MAX_COUNT = 1000;
    public static final int CENTER_POSITION = MAX_COUNT / 2;

    private int headerHeight;
    private int headerBgColor;
    private int calendarHeight;


    private ViewPager viewPagerContent;
    private GetViewHelper getViewHelper;
    private CalendarPagerAdapter calendarPagerAdapter;

    private OnDateSelectListener onDateSelectListener;
    private OnMonthChangeListener onMonthChangeListener;

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

    public void setGetViewHelper(GetViewHelper getViewHelper) {
        this.getViewHelper = getViewHelper;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        addHeaderView();
        addMonthView();
        initListener();
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
        viewPagerContent = (ViewPager) calendar.findViewById(R.id.viewpager_calendar);
        addView(calendar);
        DateTime startDay = new DateTime();
        calendarPagerAdapter = new CalendarPagerAdapter(getContext(), calendarHeight, startDay.getYear(), startDay.getMonthOfYear(), getViewHelper);
        viewPagerContent.setAdapter(calendarPagerAdapter);
        viewPagerContent.setCurrentItem(CENTER_POSITION);
    }


    private void initListener() {
        viewPagerContent.addOnPageChangeListener(new CustomPagerChandeListender() {
            @Override
            public void onPageSelected(int position) {
                onMonthChange(position);
            }
        });

        calendarPagerAdapter.setOnOtherMonthSelectListener(new OnOtherMonthSelectListener() {
            @Override
            public void onPreMonthSelect() {
                viewPagerContent.setCurrentItem(viewPagerContent.getCurrentItem() - 1, true);
            }

            @Override
            public void onNextMonthSelect() {
                viewPagerContent.setCurrentItem(viewPagerContent.getCurrentItem() + 1, true);
            }
        });

    }

    private void onMonthChange(int position) {
        int intervalMonth = position - CENTER_POSITION;
        DateTime newDate = new DateTime(new DateTime(calendarPagerAdapter.getStartYear(),
                calendarPagerAdapter.getStartMonth(), 1, 0, 0).plusMonths(intervalMonth));
        if (onMonthChangeListener != null) {
            onMonthChangeListener.onMonthChanged(newDate.getYear(), newDate.getMonthOfYear());
        }
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
        calendarPagerAdapter.setOnDateSelectListener(onDateSelectListener);
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        this.onMonthChangeListener = onMonthChangeListener;
    }


    /**
     * 设置选中日期
     * @param dateTime
     */
    public void setSelectDateTime(DateTime dateTime){
        calendarPagerAdapter.setSelectDateTime(dateTime);
        gotoDate(dateTime.getYear(), dateTime.getMonthOfYear());
    }

    /**
     * 获取选中日期
     * @return 选中日期
     */
    public DateTime getSelectDateTime() {
        return calendarPagerAdapter.getSelectDateTime();
    }

    /**
     * 跳转到指定日期
     * @param year 指定年
     * @param month 指定月
     */
    public void gotoDate(int year, int month){
        viewPagerContent.setCurrentItem(CENTER_POSITION, true);
        calendarPagerAdapter.setStartDateTime(year, month);
        onMonthChange(CENTER_POSITION);
    }

    /**
     * 获取当前显示年份
     * @return current year
     */
    public int getCurrentYear(){
        int intervalMonth = viewPagerContent.getCurrentItem() - CENTER_POSITION;
        DateTime dateTime = new DateTime(calendarPagerAdapter.getStartYear(), calendarPagerAdapter.getStartMonth(), 1, 0, 0)
                .plusMonths(intervalMonth);
        return dateTime.getYear();
    }

    /**
     * 获取当前显示月份
     * @return current month
     */
    public int getCurrentMonth(){
        int intervalMonth = viewPagerContent.getCurrentItem() - CENTER_POSITION;
        DateTime dateTime = new DateTime(calendarPagerAdapter.getStartYear(), calendarPagerAdapter.getStartMonth(), 1, 0, 0)
                .plusMonths(intervalMonth);
        return dateTime.getMonthOfYear();
    }

    /**
     * 刷新界面
     */
    public void refresh(){
        calendarPagerAdapter.notifyDataSetChanged();
    }

}
