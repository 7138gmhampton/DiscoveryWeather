package com.discover.weather;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Nullable;

public class WeatherReading implements Parcelable
{
    private final Float temperature_in_celsius;
    private final Float pressure_in_hectopascals;
    private final Float wind_speed_in_metres_per_sec;

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
            @Nullable Float wind_speed_in_metres_per_sec)
    {
        this.temperature_in_celsius = temperature_in_celsius;
        this.pressure_in_hectopascals = pressure_in_hectopascals;
        this.wind_speed_in_metres_per_sec = wind_speed_in_metres_per_sec;
    }

    private WeatherReading(Parcel in)
    {
        ClassLoader class_loader = WeatherReading.class.getClassLoader();

        this.temperature_in_celsius = (Float)in.readValue(class_loader);
        this.pressure_in_hectopascals = (Float)in.readValue(class_loader);
        this.wind_speed_in_metres_per_sec = (Float)in.readValue(class_loader);
    }

    @Nullable Float getTemperature() { return temperature_in_celsius; }
    @Nullable Float getPressure() { return pressure_in_hectopascals; }
    @Nullable Float getWindSpeed() { return wind_speed_in_metres_per_sec; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeValue(temperature_in_celsius);
        out.writeValue(pressure_in_hectopascals);
        out.writeValue(wind_speed_in_metres_per_sec);
    }
}
