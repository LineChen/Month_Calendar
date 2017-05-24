package com.beiing.monthcalendar.listener;

import android.view.View;
import android.view.ViewGroup;

import com.beiing.monthcalendar.bean.Day;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public interface GetViewHelper {

    View getDayView(int position, View convertView, ViewGroup parent, Day day);

    View getWeekView(int position, View convertView, ViewGroup parent, String week);

}
