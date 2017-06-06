package com.example.azernax.dforget;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends Activity {

    //main variables
    private EditText            txt_description;
    private EditText            txt_importance;
    private Button              bt_add;
    private int                 txt_hour;
    private int                 txt_minutes;
    private int                 year_get;
    private int                 month_get;
    private int                 day_get;
    private ArrayList<String>   objects;

    DataBaseHandler     handler;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txt_description  = (EditText) findViewById(R.id.txt_description);
        txt_importance   = (EditText) findViewById(R.id.txt_importance);
        bt_add           = (Button)   findViewById(R.id.bt_add);


        //get data when click on calendar
        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day)
            {
                year_get    = year;
                month_get   = month;
                day_get     = day;
            }
        });

        try {
            //action when click on button "bt_add"
            bt_add.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    try {
                        //converts data
                        String description   = txt_description.getText().toString();
                        String importance    = txt_importance.getText().toString();

                        //get time from timePicker
                        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
                        txt_hour              = timePicker.getHour();
                        txt_minutes           = timePicker.getMinute();

                        handler = new DataBaseHandler(getBaseContext()); //create new handler object
                        handler.open(); //open database
                        handler.InsertData(description, importance, txt_hour, txt_minutes, day_get, month_get+1, year_get);  //insert data to table
                        handler.close(); //close database

                        //clear the EditText fields
                        txt_description.setText("");
                        txt_importance.setText("");

                        ScheduleNotification nameObject = new ScheduleNotification();
                        nameObject.setAlarm(getApplicationContext(), year_get,month_get,day_get,txt_hour,txt_minutes,0,description);

                                    System.out.println("################## CREATE OBJECT in AddActivity ###################");
                                    System.out.println(year_get);
                                    System.out.println(month_get);
                                    System.out.println(day_get);
                                    System.out.println(txt_hour);
                                    System.out.println(txt_minutes);
                                    System.out.println(description);

                        DescriptionControl d = new DescriptionControl();
                        d.getDescriptions(getApplicationContext());

                        finish();

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
