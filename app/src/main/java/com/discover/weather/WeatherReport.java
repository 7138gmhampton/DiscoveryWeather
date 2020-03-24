package com.discover.weather;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherReport extends AppCompatActivity implements OnSeekBarChangeListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        //updateWindDirectionDisplay();
        SeekBar windSeekBar = findViewById(R.id.seekWind);
        windSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateWindDirectionDisplay();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if (seekBar.getId() == R.id.seekWind) updateWindDirectionDisplay();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private void updateWindDirectionDisplay()
    {
        SeekBar directionSetter = findViewById(R.id.seekWind);
        int currentDirection = directionSetter.getProgress();
        TextView directionDisplay = findViewById(R.id.textDirectionDisplay);

        directionDisplay.setText(Integer.toString(currentDirection) + "\u00B0");
    }
}
