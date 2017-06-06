package com.example.azernax.dforget;

import android.content.Context;
import android.database.Cursor;

import static com.example.azernax.dforget.MainActivity.d1;
import static com.example.azernax.dforget.MainActivity.d2;
import static com.example.azernax.dforget.MainActivity.d3;

/**
 * Created by azernax on 05.06.2017.
 */

public class DescriptionControl {


    //method which get description to show from my database (sqlite)
    public void getDescriptions(Context context)
    {

        DataBaseHandler handler;
        String str[] = new String[3];
        handler = new DataBaseHandler(context);
        handler.open();
        Cursor c = handler.DisplayDesc();

        int i =0;
        if (c.moveToFirst())
        {
            do {
                str[i] = c.getString(c.getColumnIndex("description_event"));
                System.out.println("STR = "+str[i]);
                i++;
            }while(c.moveToNext());
        }

        d1 = str[0];
        d2 = str[1];
        d3 = str[2];
        handler.close();
    }


}
