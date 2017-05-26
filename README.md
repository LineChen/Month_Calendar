# Month_Calendar 

一个按月显示的日历，布局完全自定义。

![capture](https://github.com/LineChen/Month_Calendar/blob/master/month_calendar.gif)

## 使用

布局：

```java

    <com.beiing.monthcalendar.MonthCalendar
        android:id="@+id/month_calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/white"
        app:mc_calendarHeight="@dimen/calender_content_height"/>

```

代码中：

- 设置布局显示

> 必须调用`setGetViewHelper`方法加载布局，`getDayView`方法控制每一天显示，
> `getWeekView`方法控制星期显示，使用类似ListView中BaseAdapter中的getView方法。

```java
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
                switch (position){
                    case 0:
                        week = "日";
                        tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                        break;
                    case 1:
                        week = "一";
                        break;
                    case 2:
                        week = "二";
                        break;
                    case 3:
                        week = "三";
                        break;
                    case 4:
                        week = "四";
                        break;
                    case 5:
                        week = "五";
                        break;
                    case 6:
                        week = "六";
                        tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                        break;
                }
                tvWeek.setText(week);
                return convertView;
            }
        });

```


- 设置日期选择监听

```java

        monthCalendar.setOnDateSelectListener(new OnDateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                tvSelectDate.setText("你选择的日期：" + selectDate.toString("yyyy-MM-dd"));
            }
        });

```

- 设置月切换监听

```java

monthCalendar.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(int currentYear, int currentMonth) {
                tvMonthChange.setText(currentYear + "年" + currentMonth + "月");
            }
        });

```

**其他方法**

- `getSelectDateTime` 获取当前选中日期

- `setSelectDateTime(DateTime dateTime)` 设置选中日期

- `gotoDate(DateTime dateTime)` 跳转到指定日期

- `getCurrentYear` 获取当前显示年份

- `getCurrentMonth` 获取当前显示月份

- `refresh` 刷新界面



# License

```
   Copyright 2017 LineChen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

















