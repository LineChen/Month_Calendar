package com.beiing.monthcalendar.bean;

import org.joda.time.DateTime;

/**
 * Created by linechen on 2017/5/23.<br/>
 * 描述：
 * </br>
 */

public class Day {

    private DateTime dateTime;
    private boolean select;
    private boolean otherMonth;

    public Day() {
    }

    public Day(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Day(DateTime dateTime, boolean otherMonth) {
        this.dateTime = dateTime;
        this.otherMonth = otherMonth;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isOtherMonth() {
        return otherMonth;
    }

    public void setOtherMonth(boolean otherMonth) {
        this.otherMonth = otherMonth;
    }
}
