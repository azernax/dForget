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

import static com.example.azernax.dforget.MainActivity.e_description;
import static com.example.azernax.dforget.MainActivity.e_importance;

/**
 * Created by AZERNAX on 21.05.2017.
 */

public class EditActivity extends Activity {
    //main variables
    protected EditText txt_description_E;
    protected EditText txt_importance_E;
    protected int txt_hour;
    protected int txt_minutes;
    private Button bt_edit;
    protected int year_get;
    protected int month_get;
    protected int day_get;

    DataBaseHandler handler;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txt_description_E = (EditText) findViewById(R.id.txt_description_E);
        txt_importance_E = (EditText) findViewById(R.id.txt_importance_E);
        bt_edit = (Button) findViewById(R.id.bt_edit);

        txt_description_E.setText(e_description);
        txt_importance_E.setText(e_importance);

        //get data when click on calendar
        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView_E);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day)
            {
                year_get = year;
                month_get = month;
                day_get = day;
            }
        });


        try {
            //action when click on button "bt_edit"
            bt_edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        //converts data
                        String description = txt_description_E.getText().toString();
                        String importance = txt_importance_E.getText().toString();

                        //get time from timePicker
                        TimePicker timePicker =(TimePicker) findViewById(R.id.timePicker);
                        txt_hour = timePicker.getHour();
                        txt_minutes = timePicker.getMinute();

                        handler = new DataBaseHandler(getBaseContext()); //create new handler object
                        handler.open(); //open database
                        handler.InsertData(description, importance, txt_hour, txt_minutes, day_get, month_get+1, year_get);  //insert data to table
                        handler.close(); //close database

                        //clear the EditText fields
                        txt_description_E.setText("");
                        txt_importance_E.setText("");

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
