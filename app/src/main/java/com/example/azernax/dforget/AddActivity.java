package com.example.azernax.dforget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CalendarView;
import android.widget.Toast;

public class AddActivity extends Activity {


    //main variables
    private EditText txt_description;
    private EditText txt_importance;
    private EditText txt_hour;
    private EditText txt_minutes;
    private Button bt_add;
    private int year_get;
    private int month_get;
    private int day_get;

    DataBaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        txt_description = (EditText) findViewById(R.id.txt_description);
        txt_importance = (EditText) findViewById(R.id.txt_importance);
        txt_hour = (EditText) findViewById(R.id.txt_hour);
        txt_minutes = (EditText) findViewById(R.id.txt_minutes);
        bt_add = (Button) findViewById(R.id.bt_add);


        //get data when click on calendar
        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
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
            //action when click on button "bt_add"
            bt_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        //converts data
                        String description = txt_description.getText().toString();
                        String importance = txt_importance.getText().toString();
                        int hour = Integer.parseInt(txt_hour.getText().toString());
                        int minutes = Integer.parseInt(txt_minutes.getText().toString());

                        handler = new DataBaseHandler(getBaseContext()); //create new handler object
                        handler.open(); //open database
                        handler.InsertData(description, importance, hour, minutes, day_get, month_get, year_get);  //insert data to table
                        handler.close(); //close database

                        //clear the EditText fields
                        txt_description.setText("");
                        txt_importance.setText("");
                        txt_hour.setText("");
                        txt_minutes.setText("");
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Error" +e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error" +e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
