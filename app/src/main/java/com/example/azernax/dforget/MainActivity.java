package com.example.azernax.dforget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //button which start "AddActivity"
    public void clickBTadd(View v)
    {
        Intent intent1 = new Intent(MainActivity.this, AddActivity.class);
        MainActivity.this.startActivity(intent1);
    }
}
