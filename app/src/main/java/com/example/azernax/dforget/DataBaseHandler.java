package com.example.azernax.dforget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by AZERNAX on 29.03.2017.
 */

public class DataBaseHandler {

    //variables
    DataBaseHelper dbHelper;
    Context context;

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
            db.execSQL("CREATE TABLE eventsTab(id_event INTEGER PRIMARY KEY AUTOINCREMENT, descrition_event VARCHAR(150) NOT NULL, importance_event VARCHAR(2) DEFAULT 'C',hour_event INTEGER NOT NULL, minutes_event INTEGER NOT NULL, day_event INTEGER NOT NULL, month_event INTEGER NOT NULL, year_event INTEGER NOT NULL);");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST eventsTab");
            onCreate(db);
        }
    }


}
