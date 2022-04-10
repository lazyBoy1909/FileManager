package com.example.happybirthday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a = 5, b = 0;
        try
        {
            System.out.println(a/b);
        }
        catch(Exception e)
        {
            Log.e("MainActivity", "Divided by zero");
        }
    }
}