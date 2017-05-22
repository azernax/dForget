package com.example.azernax.dforget;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by AZERNAX on 10.04.2017.
 */

public class ScheduleNotification extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleNotification.completeWakefulIntent(intent);

        //create notification to show !!!
        Toast.makeText(context, "TEST schedule!!!! ", Toast.LENGTH_LONG).show();


        //notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("dF Notification!");
        builder.setContentText("description"); //--description event--

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(Context context, int year, int month, int day, int hour, int minute, int second)
    {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleNotification.class);
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
