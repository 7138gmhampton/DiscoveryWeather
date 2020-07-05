package com.discover.weather;

public class WeatherReading
{
    private float temperature_in_celsius_;

    public WeatherReading(float temperature_in_celsius)
    {
        temperature_in_celsius_ = temperature_in_celsius;
    }

    public float getTemperature() { return temperature_in_celsius_; }
}
