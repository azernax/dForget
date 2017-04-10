package com.example.azernax.dforget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Date;

/**
 * Created by AZERNAX on 10.04.2017.
 */

public class ScheduleNotification extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleNotification.completeWakefulIntent(intent);

        //create notification to show !!!

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(Context context, int year, int month, int day, int hour, int minute, int second)
    {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();

        //set date to show notification
        calendar.set(Calendar.YEAR, year);               //year
        calendar.set(Calendar.MONTH, month);             //month
        calendar.set(Calendar.DAY_OF_MONTH, day);        //day
        calendar.set(Calendar.HOUR_OF_DAY, hour);        //hour
        calendar.set(Calendar.MINUTE, minute);           //minute
        calendar.set(Calendar.SECOND, second);           //second

        Date date = calendar.getTime();
        alarmManager.setExact(alarmManager.RTC_WAKEUP, date.getTime(), alarmIntent);
    }
}
