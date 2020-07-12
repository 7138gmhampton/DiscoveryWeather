package com.discover.weather;

import android.content.Context;
import android.content.res.Resources;

import java.util.Locale;

class Summary
{
    private static final String intro = "Details to be submitted:" + System.lineSeparator();
    private static final String outro = System.lineSeparator() + System.lineSeparator() +
        "Are these details correct?";

    static String author(Context context, WeatherReading reading)
    {
        Resources resources = context.getResources();
//        String summary = intro;
//
//        summary += ConditionOptions.getInstance().getOptions().get(reading.getConditionCode()) +
//            System.lineSeparator();
//        summary += appendDetail("Temperature: ", reading.getTemperature(),
//            resources.getString(R.string.label_temp_units));
//        summary += appendDetail("Pressure: ", reading.getPressure(),
//            resources.getString(R.string.label_pressure_unit));
//        summary += appendDetail("Wind Speed: ", reading.getWindSpeed(),
//            resources.getString(R.string.label_speed_unit));
//        summary += appendDetail("Wind Direction: ", reading.getWindDirection(),
//            resources.getString(R.string.units_direction));
//        summary += appendDetail("Rainfall: ", reading.getRainfall(),
//            resources.getString(R.string.label_precipitation_unit));
//        summary += appendDetail("Snowfall: ", reading.getSnowfall(),
//            resources.getString(R.string.label_precipitation_unit));
//
//        summary += outro;
        return intro +
            ConditionOptions.getInstance().getOptions().get(reading.getConditionCode()) +
            System.lineSeparator() +
            appendDetail("Temperature: ", reading.getTemperature(),
                resources.getString(R.string.label_temp_units)) +
            appendDetail("Pressure: ", reading.getPressure(),
                resources.getString(R.string.label_pressure_unit)) +
            appendDetail("Wind Speed: ", reading.getWindSpeed(),
                resources.getString(R.string.label_speed_unit)) +
            appendDetail("Wind Direction: ", reading.getWindDirection(),
                resources.getString(R.string.units_direction)) +
            appendDetail("Rainfall: ", reading.getRainfall(),
                resources.getString(R.string.label_precipitation_unit)) +
            appendDetail("Snowfall: ", reading.getSnowfall(),
                resources.getString(R.string.label_precipitation_unit)) +
            outro;
    }

    private static String appendDetail(String name, Float metric, String unit)
    {
//        String detail = "";
//
//        if (metric != null) {
//            detail += name;
//            detail += String.format("%.2f", metric);
//            detail += unit;
//            detail += System.lineSeparator();
//        }
//
//        return detail;
        if (metric != null)
            return name + String.format(Locale.UK,"%.2f", metric) + unit +
                System.lineSeparator();
        return "";
    }

    private static String appendDetail(String name, Integer metric, String unit)
    {
//        String detail = "";
//
//        if (metric != null) {
//            detail += name;
//            detail += metric.toString();
//            detail += unit;
//            detail += System.lineSeparator();
//        }
//
//        return detail;
        if (metric != null)
            return  name + metric.toString() + unit + System.lineSeparator();
        return "";
    }
}
