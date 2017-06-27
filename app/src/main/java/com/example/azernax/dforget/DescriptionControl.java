package com.example.azernax.dforget;

import android.content.Context;
import android.database.Cursor;

import static com.example.azernax.dforget.MainActivity.d1;

/**
 * Created by azernax on 05.06.2017.
 */

public class DescriptionControl {

    public static String g_description="", g_importance="", g_hour="", g_minutes="", g_day="", g_month="", g_year="";

    //method which get description to show from my database (sqlite)
    public void getDescriptions(Context context)
    {
        DataBaseHandler handler;
        String str[] = new String[3];
        handler = new DataBaseHandler(context);
        handler.open();
        Cursor c = handler.DisplayDesc();

        if (c.moveToFirst())
        {
            do {
                d1 = c.getString(c.getColumnIndex("description_event"));

            }while(c.moveToNext());
        }


        handler.close();
    }

    public void getDate(Context context)
    {

        DataBaseHandler              handler;

        handler = new DataBaseHandler(context);
        handler.open();

        try
        {
            Cursor c = handler.getDateFromTab();
            if(c.moveToFirst())
            {
                do
                {
                    g_description = c.getString(c.getColumnIndex("description_event"));
                    g_importance  = c.getString(c.getColumnIndex("importance_event"));
                    g_hour        = c.getString(c.getColumnIndex("hour_event"));
                    g_minutes     = c.getString(c.getColumnIndex("minutes_event"));
                    g_day         = c.getString(c.getColumnIndex("day_event"));
                    g_month       = c.getString(c.getColumnIndex("month_event"));
                    g_year        = c.getString(c.getColumnIndex("year_event"));

                }while(c.moveToNext());
            }
            else
            {
              //  Toast.makeText(context, "Error: ", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e)
        {
        //    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        handler.close();
    }
}
