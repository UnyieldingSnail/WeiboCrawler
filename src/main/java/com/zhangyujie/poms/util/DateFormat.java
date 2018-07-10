package com.zhangyujie.poms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
    public static String format(String dateStr) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        try {
            if (dateStr.matches("刚刚")) {
                date = new Date();
            } else if (dateStr.matches("\\d+分钟前")) {
                int end = dateStr.indexOf("分");
                int sub = Integer.parseInt(dateStr.substring(0, end));
                calendar.add(Calendar.MINUTE, -sub);
                date = calendar.getTime();
            } else if (dateStr.matches("\\d+小时前")) {
                int end = dateStr.indexOf("小");
                int sub = Integer.parseInt(dateStr.substring(0, end));
                calendar.add(Calendar.HOUR, -sub);
                date = calendar.getTime();
            } else if (dateStr.matches("昨天\\s\\d{2}:\\d{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
                Date d = sdf.parse(dateStr);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                calendar.setTime(d);
                calendar.set(year, month, day);
                calendar.add(Calendar.DATE, -1);
                date = calendar.getTime();
            } else if (dateStr.matches("\\d{2}-\\d{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                Date d = sdf.parse(dateStr);
                int year = calendar.get(Calendar.YEAR);
                calendar.setTime(d);
                calendar.set(Calendar.YEAR, year);
                date = calendar.getTime();
            } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // '2017-06-27 00:00:00'
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    public static long parse(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0L;
        try {
            time = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
