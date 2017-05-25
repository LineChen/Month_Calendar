package com.beiing.month_calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiing.monthcalendar.MonthCalendar;
import com.beiing.monthcalendar.bean.Day;
import com.beiing.monthcalendar.listener.GetViewHelper;
import com.beiing.monthcalendar.listener.OnDateSelectListener;
import com.beiing.monthcalendar.listener.OnMonthChangeListener;
import com.beiing.monthcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MonthCalendar monthCalendar;
    private List<DateTime> eventDates;
    private TextView tvMonthChange;
    private TextView tvCurrentYearMonth;
    private TextView tvSelectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMonthChange = (TextView) findViewById(R.id.tv_month_change);
        tvCurrentYearMonth = (TextView) findViewById(R.id.tv_current_year_month);
        tvSelectDate = (TextView) findViewById(R.id.tv_select_date);
        tvMonthChange.setText(new DateTime().toString("yyyy年M月"));

        eventDates = new ArrayList<>();

        monthCalendar = (MonthCalendar) findViewById(R.id.month_calendar);
        monthCalendar.setGetViewHelper(new GetViewHelper() {
            @Override
            public View getDayView(int position, View convertView, ViewGroup parent, Day day) {
                if(convertView == null){
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_day, parent, false);
                }
                TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
                DateTime dateTime = day.getDateTime();
                tvDay.setText(dateTime.toString("d"));
                boolean select = day.isSelect();
                if(CalendarUtil.isToday(dateTime) && select){
                    tvDay.setTextColor(Color.WHITE);
                    tvDay.setBackgroundResource(R.drawable.circular_blue);
                } else if(CalendarUtil.isToday(dateTime)){
                    tvDay.setTextColor(getResources().getColor(R.color.colorTodayText));
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                } else if(select){
                    tvDay.setTextColor(Color.WHITE);
                    tvDay.setBackgroundResource(R.drawable.circular_blue);
                } else {
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                    if(day.isOtherMonth()){
                        tvDay.setTextColor(Color.LTGRAY);
                    } else {
                        tvDay.setTextColor(Color.BLACK);
                    }
                }

                ImageView ivPoint = (ImageView) convertView.findViewById(R.id.iv_point);
                ivPoint.setVisibility(View.INVISIBLE);
                for (DateTime d : eventDates) {
                    if(CalendarUtil.isSameDay(d, dateTime)){
                        ivPoint.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                return convertView;
            }

            @Override
            public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                if(convertView == null){
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_week, parent, false);
                }
                TextView tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
                tvWeek.setText(week);
                if(position == 0 || position == 6){
                    tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                return convertView;
            }
        });

        monthCalendar.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(int currentYear, int currentMonth) {
                tvMonthChange.setText(currentYear + "年" + currentMonth + "月");
            }
        });

        monthCalendar.setOnDateSelectListener(new OnDateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                tvSelectDate.setText("你选择的日期：" + selectDate.toString("yyyy-MM-dd"));
            }
        });
    }

    int plus = 0;
    public void addEvent(View view) {
        eventDates.add(new DateTime().plusDays(plus ++));
        monthCalendar.refresh();
    }

    public void gotoDate(View view) {
        DateTime dateTime = new DateTime().plusMonths((int) (Math.random() * 10));
        monthCalendar.gotoDate(dateTime.getYear(),dateTime.getMonthOfYear());
    }

    public void setSelectDate(View view) {
        monthCalendar.setSelectDateTime(new DateTime().plusDays((int) (Math.random() * 20)));
    }

    public void gotoToday(View view) {
        DateTime dateTime = new DateTime();
        monthCalendar.gotoDate(dateTime.getYear(), dateTime.getMonthOfYear());
    }

    public void getCurrentYearMonth(View view) {
        tvCurrentYearMonth.setText("当前是" + monthCalendar.getCurrentYear() + "年" + monthCalendar.getCurrentMonth() + "月");
    }
}
