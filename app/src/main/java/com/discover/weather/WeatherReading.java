package com.discover.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherReading implements Parcelable
{
    private Float temperature_in_celsius;
    private static final int NO_DATA_POINTS = 1;

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

    public WeatherReading(Float temperature_in_celsius)
    {
        this.temperature_in_celsius = temperature_in_celsius;
    }

    public WeatherReading(Parcel in)
    {
//        String[] data = new String[NO_DATA_POINTS];
//        in.readStringArray(data);

        this.temperature_in_celsius = (Float)in.readValue(null);
    }

    public Float getTemperature() { return temperature_in_celsius; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
//        out.writeFloat(temperature_in_celsius);
        out.writeValue(temperature_in_celsius);
    }
}
