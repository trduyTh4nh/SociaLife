package com.example.projectmain.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timeHelper {
    public static String getTime(String time){
        Calendar calendar = Calendar.getInstance();
        if (time == null) {
            time = "0";
        }
        long t = calendar.getTimeInMillis() - Long.parseLong(time);
        long hr = (t / (1000 * 60 * 60));
        long min = (t / (1000 * 60));

        String timedifference;

        if (hr >= 24 * 365) {
            timedifference = String.valueOf(hr / 24 / 365) + " Năm trước";
        } else if (hr >= 24 * 30) {
            timedifference = String.valueOf(hr / 24 / 30) + " Tháng trước";
        } else if (hr >= 24 * 7) {
            timedifference = String.valueOf(hr / 24 / 7) + " Tuần trước";
        } else if (hr >= 24) {
            timedifference = String.valueOf(hr / 24) + " Ngày trước";
        } else {
            timedifference = String.valueOf(hr) + " Giờ trước";
        }
        if (hr <= 1) {
            timedifference = String.valueOf(min) + " Phút trước";
        } else if (min <= 1) {
            timedifference = String.valueOf(t / (1000) + " Giây trước");
        }
        DateFormat d = SimpleDateFormat.getDateInstance();
        d.setCalendar(calendar);
        String test = d.format(new Date(Long.parseLong(time)));
        return timedifference + " (Đăng ngày " + test + ")";
    }
}
