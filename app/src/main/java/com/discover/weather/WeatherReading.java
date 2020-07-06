package com.discover.weather;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import javax.annotation.Nullable;

public class WeatherReading implements Parcelable
{
    private final Float temperature_in_celsius;
    private final Float pressure_in_hectopascals;
    private final Float wind_speed_in_metres_per_sec;
    private final Integer rainfall_in_millimetres;
    private final Integer snowfall_in_millimetres;
    private final int wind_direction_in_degrees;
    private final int overall_condition;

    public static final Parcelable.Creator<WeatherReading> CREATOR =
            new Parcelable.Creator<WeatherReading>()
            {
                public WeatherReading createFromParcel(Parcel in)
                {
                    return new WeatherReading(in);
                }

                public WeatherReading[] newArray(int size)
                {
                    return new WeatherReading[size];
                }
            };

    WeatherReading(
            @Nullable Float temperature_in_celsius,
            @Nullable Float pressure_in_hectopascals,
            @Nullable Float wind_speed_in_metres_per_sec,
            @Nullable Integer rainfall_in_millimetres,
            @Nullable Integer snowfall_in_millimetres,
            @NonNull int wind_direction_in_degrees,
            @NonNull int overall_condition)
    {
        this.temperature_in_celsius = temperature_in_celsius;
        this.pressure_in_hectopascals = pressure_in_hectopascals;
        this.wind_speed_in_metres_per_sec = wind_speed_in_metres_per_sec;
        this.rainfall_in_millimetres = rainfall_in_millimetres;
        this.snowfall_in_millimetres = snowfall_in_millimetres;
        this.wind_direction_in_degrees = wind_direction_in_degrees;
        this.overall_condition = overall_condition;
    }

    private WeatherReading(Parcel in)
    {
        ClassLoader class_loader = WeatherReading.class.getClassLoader();

        this.temperature_in_celsius = (Float)in.readValue(class_loader);
        this.pressure_in_hectopascals = (Float)in.readValue(class_loader);
        this.wind_speed_in_metres_per_sec = (Float)in.readValue(class_loader);
        this.rainfall_in_millimetres = (Integer)in.readValue(class_loader);
        this.snowfall_in_millimetres = (Integer)in.readValue(class_loader);
        this.wind_direction_in_degrees = in.readInt();
        this.overall_condition = in.readInt();
    }

    @Nullable Float getTemperature() { return temperature_in_celsius; }
    @Nullable Float getPressure() { return pressure_in_hectopascals; }
    @Nullable Float getWindSpeed() { return wind_speed_in_metres_per_sec; }
    @Nullable Integer getRainfall() { return  rainfall_in_millimetres; }
    @Nullable Integer getSnowfall() { return snowfall_in_millimetres; }
    @NonNull int getWindDirection() { return wind_direction_in_degrees; }
    @NonNull int getConditionCode() { return overall_condition; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeValue(temperature_in_celsius);
        out.writeValue(pressure_in_hectopascals);
        out.writeValue(wind_speed_in_metres_per_sec);
        out.writeValue(rainfall_in_millimetres);
        out.writeValue(snowfall_in_millimetres);
        out.writeInt(wind_direction_in_degrees);
        out.writeInt(overall_condition);
    }

    public HashMap<String,Object> prepareForUpload(LatLng lat_long)
    {
        HashMap<String,Object> data = new HashMap<>();

        if (temperature_in_celsius != null)
            data.put("temperature", temperature_in_celsius);
        if (pressure_in_hectopascals != null)
            data.put("pressure", pressure_in_hectopascals);
        if (wind_speed_in_metres_per_sec != null)
            data.put("wind_speed", wind_speed_in_metres_per_sec);
        if (rainfall_in_millimetres != null)
            data.put("rainfall", rainfall_in_millimetres);
        if (snowfall_in_millimetres != null)
            data.put("snowfall", snowfall_in_millimetres);
        data.put("wind_direction", wind_direction_in_degrees);
        data.put("condition", overall_condition);
        data.put("latitude", lat_long.latitude);
        data.put("longitude", lat_long.longitude);
        data.put("timestamp", System.currentTimeMillis()/1000);

        return data;
    }
}
