package com.beiing.monthcalendar.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.beiing.monthcalendar.MonthCalendar.DAYS_OF_WEEK;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class WeekAdapter extends BaseAdapter {

    private List<String> weeks;
    private ViewAdapter viewAdapter;

    public WeekAdapter(ViewAdapter viewAdapter) {
        this.viewAdapter = viewAdapter;
        String[] weekdays = DateFormatSymbols.getInstance().getWeekdays();
        weeks = new ArrayList<>(Arrays.asList(weekdays));
        weeks.remove(0);
    }

    @Override
    public int getCount() {
        return DAYS_OF_WEEK;
    }

    @Override
    public Object getItem(int position) {
        return weeks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return viewAdapter.getWeekView(position, convertView, parent, weeks.get(position));
    }
}
