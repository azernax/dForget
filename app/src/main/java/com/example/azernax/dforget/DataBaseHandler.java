package com.example.azernax.dforget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.azernax.dforget.MainActivity.e_description;
import static com.example.azernax.dforget.MainActivity.e_minutes;

/**
 * Created by AZERNAX on 29.03.2017.
 */

public class DataBaseHandler {

    //variables
    DataBaseHelper dbHelper;
    Context        context;
    SQLiteDatabase db;

    //constructor
    public DataBaseHandler(Context ctx)
    {
       this.context = ctx;
        dbHelper = new DataBaseHelper(context);
    }


    public static class DataBaseHelper extends SQLiteOpenHelper
    {
        //create object to help create, open database etc....
        public DataBaseHelper(Context ctx)
        {
            super(ctx,"myDataBase",null,1);
        }

        //create db and table if not exist
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE eventsTab(id_event INTEGER PRIMARY KEY, description_event VARCHAR(150) NOT NULL, importance_event VARCHAR(2) DEFAULT 'C',hour_event INTEGER NOT NULL, minutes_event INTEGER NOT NULL, day_event INTEGER NOT NULL, month_event INTEGER NOT NULL, year_event INTEGER NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST eventsTab");
            onCreate(db);
        }
    }

    //create or open database for reading and writing
    public DataBaseHandler open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //close database
    public void close()
    {
        dbHelper.close();
    }

    //method for insert data
    public long InsertData(String description_event, String importance_event, int hour_event, int minutes_event, int day_event, int month_event, int year_event)
    {
        ContentValues content = new ContentValues();
        content.put("description_event",   description_event);
        content.put("importance_event",    importance_event);
        content.put("hour_event",          hour_event);
        content.put("minutes_event",       minutes_event);
        content.put("day_event",           day_event);
        content.put("month_event",         month_event);
        content.put("year_event",          year_event);
        return db.insertOrThrow("eventsTab",null, content);
    }

    //delete row when click button
    public void DeleteData()
    {
        db.execSQL("delete from eventsTab where description_event='"+e_description+"' and minutes_event='"+e_minutes+"'");
    }

    //delete row which shown on notification
    public void DeleteRow(String toDel)
    {
        db.execSQL("delete from eventsTab WHERE description_event='"+toDel+"'");
    }

    //SELECT *
    public Cursor DisplayData()
    {
        return db.rawQuery("SELECT * FROM eventsTab ORDER BY year_event, month_event, day_event, hour_event, minutes_event", null);
    }

    //TEST
    //show 1 description event
    public Cursor DisplayDesc()
    {
        return db.rawQuery("SELECT description_event FROM eventsTab WHERE description_event != '' ORDER BY year_event, month_event, day_event, hour_event, minutes_event LIMIT 1", null);
    }

    public Cursor getDateFromTab()
    {
        return db.rawQuery("SELECT * FROM eventsTab WHERE description_event != '' ORDER BY year_event, month_event, day_event, hour_event, minutes_event LIMIT 1", null);
    }
}
