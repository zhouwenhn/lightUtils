package com.chowen.lightutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chowen.lightutils.log.Loger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Loger.d("chowen");
    }
}
