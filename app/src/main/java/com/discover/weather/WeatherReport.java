package com.discover.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class WeatherReport extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        //updateWindDirectionDisplay();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateWindDirectionDisplay();
    }

    private void updateWindDirectionDisplay()
    {
        SeekBar directionSetter = findViewById(R.id.seekWind);
        int currentDirection = directionSetter.getProgress();
        TextView directionDisplay = findViewById(R.id.textDirectionDisplay);

        directionDisplay.setText(Integer.toString(currentDirection));
    }
}
