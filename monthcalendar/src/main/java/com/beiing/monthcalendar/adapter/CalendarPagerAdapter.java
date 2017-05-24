package com.beiing.monthcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.beiing.monthcalendar.R;
import com.beiing.monthcalendar.listener.DateSelectListener;
import com.beiing.monthcalendar.listener.GetViewHelper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import jackwharton_salvage.RecyclingPagerAdapter;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class CalendarPagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private int maxCount;
    private int centerPosition;
    private int calendarHeight;

    private int startYear;
    private int startMonth;
    /**日期选择:默认是今天**/
    private DateTime selectDateTime;
    private GetViewHelper getViewHelper;
    private DateSelectListener dateSelectListener;

    public CalendarPagerAdapter(Context context, int calendarHeight, int maxCount, int startYear, int startMonth, GetViewHelper getViewHelper) {
        this.context = context;
        this.calendarHeight = calendarHeight;
        this.maxCount = maxCount;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.getViewHelper = getViewHelper;
        centerPosition = maxCount / 2;
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
        int interval = position - centerPosition;
        DateTime start = new DateTime(startYear, startMonth, 1, 0, 0, DateTimeZone.UTC).plusMonths(interval);
        final DayAdapter dayAdapter = new DayAdapter(calendarHeight, start, getViewHelper, selectDateTime);
        viewHolder.weekGrid.setAdapter(dayAdapter);
        viewHolder.weekGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectDateTime = dayAdapter.getItem(position).getDateTime();
                dayAdapter.setSelectDateTime(selectDateTime);
                notifyDataSetChanged();
                if(dateSelectListener != null){
                    dateSelectListener.onDateSelect(selectDateTime);
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
        return maxCount;
    }

    private static class WeekViewHolder{
        GridView weekGrid;

        WeekViewHolder(View root) {
            weekGrid = (GridView) root.findViewById(R.id.grid_date);
        }
    }

    public DateTime getSelectDateTime() {
        return selectDateTime;
    }

    public void setDateSelectListener(DateSelectListener dateSelectListener) {
        this.dateSelectListener = dateSelectListener;
    }

//    public DateTime getStartDateTime() {
//        return startDateTime;
//    }
//
//    public void setStartDateTime(DateTime startDateTime) {
//        this.startDateTime = startDateTime;
//        notifyDataSetChanged();
//    }
}
