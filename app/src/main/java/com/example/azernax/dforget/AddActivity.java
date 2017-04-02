package com.example.azernax.dforget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {


    //
    private EditText txt_description;
    private EditText txt_importance;
    private EditText txt_hour;
    private EditText txt_minutes;
    private Button bt_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        txt_description = (EditText) findViewById(R.id.txt_description);
        txt_importance = (EditText) findViewById(R.id.txt_importance);
        txt_hour = (EditText) findViewById(R.id.txt_hour);
        txt_minutes = (EditText) findViewById(R.id.txt_minutes);
        bt_add = (Button) findViewById(R.id.bt_add);

    }
}
