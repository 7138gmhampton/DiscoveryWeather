package com.discover.weather;

import android.os.Parcel;

public class WeatherReading
{
    private float temperature_in_celsius;
    private static final int NO_DATA_POINTS = 1;

    public WeatherReading(float temperature_in_celsius)
    {
        this.temperature_in_celsius = temperature_in_celsius;
    }

    public WeatherReading(Parcel in)
    {
        String[] data = new String[NO_DATA_POINTS];
        in.readStringArray(data);

        this.temperature_in_celsius = Float.parseFloat(data[0]);
    }

    public float getTemperature() { return temperature_in_celsius; }


}
