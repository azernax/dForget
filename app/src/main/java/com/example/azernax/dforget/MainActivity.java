package com.example.azernax.dforget;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private GridView dataGridView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    DataBaseHandler handler;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataGridView = (GridView) findViewById(R.id.dgw); //dgw = Data Grid View

        list = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);

        String description, importance, hour, minutes, day, month, year;

        //test
        ScheduleNotification object1 = new ScheduleNotification();
        object1.setAlarm(getApplicationContext(), 2017,4,20,13,51,35);
        //

        handler=new DataBaseHandler(getBaseContext());
        handler.open();

    try
    {
    Cursor c = handler.DisplayData();
        if(c.moveToFirst())
        {
            do
            {
                description = c.getString(c.getColumnIndex("description_event"));
                importance = c.getString(c.getColumnIndex("importance_event"));
                hour = c.getString(c.getColumnIndex("hour_event"));
                minutes = c.getString(c.getColumnIndex("minutes_event"));
                day = c.getString(c.getColumnIndex("day_event"));
                month = c.getString(c.getColumnIndex("month_event"));
                year = c.getString(c.getColumnIndex("year_event"));

                list.add(description);
                list.add(importance);
                list.add(hour);
                list.add(minutes);
                list.add(day);
                list.add(month);
                list.add(year);
                dataGridView.setAdapter(adapter);

            }while(c.moveToNext());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: ", Toast.LENGTH_LONG).show();
        }
    }catch(Exception e)
    {
        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }


        handler.close();
    }

    //button which start "AddActivity"
    public void clickBTadd(View v)
    {
        Intent intent1 = new Intent(MainActivity.this, AddActivity.class);
        MainActivity.this.startActivity(intent1);
    }
}
