package com.beiing.monthcalendar.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beiing.monthcalendar.bean.Day;
import com.beiing.monthcalendar.listener.GetViewHelper;
import com.beiing.monthcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.beiing.monthcalendar.MonthCalendar.DAYS_OF_WEEK;


/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class DayAdapter extends BaseAdapter {
    private int calendarHeight;
    private List<Day> dateTimes;
    private GetViewHelper getViewHelper;
    private DateTime selectDateTime;

    public DayAdapter(int calendarHeight, DateTime startDateTime, GetViewHelper getViewHelper, DateTime selectDateTime) {
        this.calendarHeight = calendarHeight;
        this.getViewHelper = getViewHelper;
        this.selectDateTime = selectDateTime;
        dateTimes = new ArrayList<>();

        final int daysOfMonth = startDateTime.dayOfMonth().getMaximumValue();
        int firstDayOfWeek = startDateTime.getDayOfWeek() % DAYS_OF_WEEK;

        for (int i = firstDayOfWeek; i >= 1; i--) {
            dateTimes.add(new Day(new DateTime(startDateTime).minusDays(i), true));
        }

        for (int i = 0; i < daysOfMonth; i++) {
            dateTimes.add(new Day(new DateTime(startDateTime).plusDays(i), false));
        }

        DateTime lastDay = dateTimes.get(dateTimes.size() - 1).getDateTime();
        int yy = DAYS_OF_WEEK - lastDay.getDayOfWeek() % DAYS_OF_WEEK;
        for (int i = 1; i < yy; i++) {
            dateTimes.add(new Day(new DateTime(lastDay).plusDays(i), true));
        }
    }

    @Override
    public int getCount() {
        return dateTimes.size();
    }

    @Override
    public Day getItem(int position) {
        return dateTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Day day = dateTimes.get(position);
        day.setSelect(CalendarUtil.isSameDay(day.getDateTime(), selectDateTime));
        View view = getViewHelper.getDayView(position, convertView, parent, day);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = calendarHeight / (dateTimes.size() / DAYS_OF_WEEK);
        return view;
    }

    public void setSelectDateTime(DateTime selectDateTime) {
        this.selectDateTime = selectDateTime;
    }
}
