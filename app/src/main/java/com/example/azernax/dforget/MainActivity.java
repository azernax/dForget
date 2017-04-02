package com.example.azernax.dforget;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private GridView dataGridView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    DataBaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataGridView = (GridView) findViewById(R.id.dgw); //dgw= Data Grid View

        list = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);

        String description, importance, hour, minutes, day, month, year;


        handler=new DataBaseHandler(getBaseContext());
        handler.open();

        Cursor c = handler.DisplayData();

        if(c.moveToFirst())
        {
            do
            {
                description=c.getString(c.getColumnIndex("decsription"));
                importance=c.getString(c.getColumnIndex("importance"));
                hour=c.getString(c.getColumnIndex("hour"));
                minutes=c.getString(c.getColumnIndex("minutes"));
                day=c.getString(c.getColumnIndex("day"));
                month=c.getString(c.getColumnIndex("month"));
                year=c.getString(c.getColumnIndex("year"));

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
        handler.close();
    }

    //button which start "AddActivity"
    public void clickBTadd(View v)
    {
        Intent intent1 = new Intent(MainActivity.this, AddActivity.class);
        MainActivity.this.startActivity(intent1);
    }
}
