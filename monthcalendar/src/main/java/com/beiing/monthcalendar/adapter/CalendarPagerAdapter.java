package com.beiing.monthcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.beiing.monthcalendar.R;
import com.beiing.monthcalendar.bean.Day;
import com.beiing.monthcalendar.listener.GetViewHelper;
import com.beiing.monthcalendar.listener.OnDateSelectListener;
import com.beiing.monthcalendar.listener.OnOtherMonthSelectListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import jackwharton_salvage.RecyclingPagerAdapter;

import static com.beiing.monthcalendar.MonthCalendar.CENTER_POSITION;
import static com.beiing.monthcalendar.MonthCalendar.MAX_COUNT;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class CalendarPagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private int calendarHeight;

    /**默认显示的年份**/
    private int startYear;
    /**默认显示的月份**/
    private int startMonth;
    /**日期选择:默认是今天**/
    private DateTime selectDateTime;
    private GetViewHelper getViewHelper;
    private OnDateSelectListener onDateSelectListener;
    private OnOtherMonthSelectListener onOtherMonthSelectListener;

    public CalendarPagerAdapter(Context context, int calendarHeight, int startYear, int startMonth, GetViewHelper getViewHelper) {
        this.context = context;
        this.calendarHeight = calendarHeight;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.getViewHelper = getViewHelper;
        selectDateTime = new DateTime();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        WeekViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar, container, false);
            viewHolder = new WeekViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (WeekViewHolder) convertView.getTag();
        }
        int interval = position - CENTER_POSITION;
        final DateTime start = new DateTime(startYear, startMonth, 1, 0, 0, DateTimeZone.UTC).plusMonths(interval);
        final DayAdapter dayAdapter = new DayAdapter(calendarHeight, start, getViewHelper, selectDateTime);
        viewHolder.weekGrid.setAdapter(dayAdapter);
        viewHolder.weekGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day day = dayAdapter.getItem(position);
                //点击其他月份的日期时，跳转到日期所在月份
                if(day.isOtherMonth() && onOtherMonthSelectListener != null){
                    if(day.getDateTime().getMillis() < start.getMillis()){
                        //跳转到上个月
                        onOtherMonthSelectListener.onPreMonthSelect();
                    } else {
                        //跳转到下个月
                        onOtherMonthSelectListener.onNextMonthSelect();
                    }
                }

                selectDateTime = day.getDateTime();
                dayAdapter.setSelectDateTime(selectDateTime);
                notifyDataSetChanged();
                if(onDateSelectListener != null){
                    onDateSelectListener.onDateSelect(selectDateTime);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return MAX_COUNT;
    }

    private static class WeekViewHolder{
        GridView weekGrid;

        WeekViewHolder(View root) {
            weekGrid = (GridView) root.findViewById(R.id.grid_date);
        }
    }


    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
    }

    public void setOnOtherMonthSelectListener(OnOtherMonthSelectListener onOtherMonthSelectListener) {
        this.onOtherMonthSelectListener = onOtherMonthSelectListener;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartDateTime(int startYear, int startMonth) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        notifyDataSetChanged();
    }

    public DateTime getSelectDateTime() {
        return selectDateTime;
    }

    public void setSelectDateTime(DateTime selectDateTime) {
        this.selectDateTime = selectDateTime;
    }
}
