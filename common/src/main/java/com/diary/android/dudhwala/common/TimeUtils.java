package com.diary.android.dudhwala.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {

    private static final int FIRST_DAY_OF_MONTH = 1;

    public static String convertTimestampToDateString(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
    }

    public static long convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(strDate);

            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return 0;
        }
    }

    public static long getMonthStartTimeStamp(int month, int year) {

        Calendar calendar = new GregorianCalendar(year, month, FIRST_DAY_OF_MONTH);
        return calendar.getTimeInMillis();
    }

    public static long getMonthEndTimeStamp(int month, int year) {

        Calendar calendar = new GregorianCalendar(year, month, FIRST_DAY_OF_MONTH);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar = new GregorianCalendar(year, month, lastDayOfMonth);
        return calendar.getTimeInMillis();
    }
}
