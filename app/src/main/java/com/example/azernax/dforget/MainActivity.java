package com.example.azernax.dforget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //
    private EditText txt_description;
    private EditText txt_importance;
    private EditText txt_hour;
    private EditText txt_minutes;
    private Button   bt_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
