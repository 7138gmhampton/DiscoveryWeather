package com.discover.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class ThankYou extends AppCompatActivity
{
    private int time_to_display;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        time_to_display = 5;

        final Handler ticker = new Handler();
        ticker.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (time_to_display < 0)
                    startActivity(new Intent(getApplicationContext(),Home.class));
                else {
                    --time_to_display;
                    Log.d("ui-behave","Display remain " + time_to_display + "s");
                    ticker.postDelayed(this,1000);
                }
            }
        }, 1000);
    }
}