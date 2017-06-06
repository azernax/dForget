package com.example.azernax.dforget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    //static variables
    public static String e_description="", e_importance="", e_hour="", e_minutes="", e_day="", e_month="", e_year="";

    private GridView             dataGridView;
    private ArrayList<String>    list;
    private ArrayAdapter<String> adapter;
    DataBaseHandler              handler;

    public static String d1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //description to show
        SharedPreferences desc1 = getSharedPreferences("DESC", 0);
        d1 = desc1.getString("desc1", "");


        //run method which draw GridView list
        drawGridView();

        //click cell on dgw
        dataGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                //loop which clear background color
                for(int u = list.size()-1; u>=0 ;u--)
                {
                    view = dataGridView.getChildAt(u);
                    view.setBackgroundColor(Color.WHITE);
                }

                for (int i =0; i < 1000;)
                {
                    if(position > i)
                    {
                        for(int j =6; j < 1000;)
                        {
                            if (position <= j)
                            {
                                position=j-6;
                                break;
                            }
                            j+=7;
                        }
                    }
                        i+=7;
                }

                for (int i=position;i<(position+7);i++ )
                {
                    view = dataGridView.getChildAt(i);
                    view.setBackgroundColor(Color.rgb(211,217,229));
                }

                e_description   = list.get(position);
                e_importance    = list.get(position+1);
                e_hour          = list.get(position+2);
                e_minutes       = list.get(position+3);
                e_day           = list.get(position+4);
                e_month         = list.get(position+5);
                e_year          = list.get(position+6);

                Toast.makeText(getApplicationContext(),"Item Clicked: " + list.get(position)+" "+ list.get(position+1)+" "+ list.get(position+2)+" "+ list.get(position+3)+" "+ list.get(position+4)+" "+ list.get(position+5)+" "+ list.get(position+6), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //method which draw GridView list
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void drawGridView()
    {
        dataGridView = (GridView) findViewById(R.id.dgw); //dgw = Data Grid View

        list         = new ArrayList<String>();
        adapter      = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);

        String description, importance, hour, minutes, day, month, year;

        handler = new DataBaseHandler(getBaseContext());
        handler.open();

        try
        {
          Cursor c = handler.DisplayData();
            if(c.moveToFirst())
            {
                do
                {
                    description = c.getString(c.getColumnIndex("description_event"));
                    importance  = c.getString(c.getColumnIndex("importance_event"));
                    hour        = c.getString(c.getColumnIndex("hour_event"));
                    minutes     = c.getString(c.getColumnIndex("minutes_event"));
                    day         = c.getString(c.getColumnIndex("day_event"));
                    month       = c.getString(c.getColumnIndex("month_event"));
                    year        = c.getString(c.getColumnIndex("year_event"));

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

    //button which start "EditActivity"
    public void clickBTedit(View v)
    {
        Intent intent2 = new Intent(MainActivity.this, EditActivity.class);
        MainActivity.this.startActivity(intent2);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickBTrefresh(View v)
    {
        drawGridView();
    }

    //button which delete event
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickBTdelete(View v)
    {
        handler.open();
        handler.DeleteData();
        handler.close();
        drawGridView();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences desc1 = getSharedPreferences("DESC", 0);
        SharedPreferences.Editor editor = desc1.edit();
        editor.putString("desc1", d1);
        editor.commit();
    }
}
