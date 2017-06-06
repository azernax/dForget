package com.example.azernax.dforget;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

import static com.example.azernax.dforget.DescriptionControl.g_day;
import static com.example.azernax.dforget.DescriptionControl.g_description;
import static com.example.azernax.dforget.DescriptionControl.g_hour;
import static com.example.azernax.dforget.DescriptionControl.g_minutes;
import static com.example.azernax.dforget.DescriptionControl.g_month;
import static com.example.azernax.dforget.DescriptionControl.g_year;
import static com.example.azernax.dforget.MainActivity.d1;


/**
 * Created by AZERNAX on 10.04.2017.
 */

public class ScheduleNotification extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;
    private String description_event;

    public static String descToShow="";


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleNotification.completeWakefulIntent(intent);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        SharedPreferences desc1 = context.getSharedPreferences("DESC", Context.MODE_PRIVATE);
        d1 = desc1.getString("desc1", "");

        descToShow = d1;

        DataBaseHandler dbh = new DataBaseHandler(context);
        dbh.open();
        dbh.DeleteRow(descToShow);
        dbh.close();


        Toast.makeText(context, descToShow, Toast.LENGTH_LONG).show();

        //notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle(descToShow);
        builder.setContentText(descToShow); //--description event--
        builder.setAutoCancel(false).build();

        //from android dev:
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, builder.build());
        PendingIntent notifyPIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
        builder.setContentIntent(notifyPIntent);

                    System.out.println("----------- BEFORE function getDate ------------");
                    System.out.println(g_year);
                    System.out.println(g_month);
                    System.out.println(g_day);
                    System.out.println(g_hour);
                    System.out.println(g_minutes);
                    System.out.println(g_description);

        //get date from database to next alarm
        DescriptionControl ob = new DescriptionControl();
        ob.getDate(context);

                    System.out.println("----------- AFTER function getDate -------------");
                    System.out.println(g_year);
                    System.out.println(g_month);
                    System.out.println(g_day);
                    System.out.println(g_hour);
                    System.out.println(g_minutes);
                    System.out.println(g_description);



        //                        PROBLEM
        //******** when I create new object start INFINITE LOOP ********

        //create next object alarm
        ScheduleNotification Object = new ScheduleNotification();
        Object.setAlarm(context, Integer.parseInt(g_year), Integer.parseInt(g_month)-1, Integer.parseInt(g_day), Integer.parseInt(g_hour), Integer.parseInt(g_minutes), 0 , g_description);
        //**************************************************************



                    System.out.println("######## AFTER CREATE OBJECT ##########");
                    System.out.println(Integer.parseInt(g_year));
                    System.out.println(Integer.parseInt(g_month)-1);
                    System.out.println(Integer.parseInt(g_day));
                    System.out.println(Integer.parseInt(g_hour));
                    System.out.println(Integer.parseInt(g_minutes));
                    System.out.println(g_description);

        //clear variables
        g_year = "";
        g_month = "";
        g_day = "";
        g_hour = "";
        g_minutes = "";
        g_description = "";
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(Context context, int year, int month, int day, int hour, int minute, int second, String description)
    {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleNotification.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();

        //test
        description_event = description;
        //

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
