package com.discover.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import javax.annotation.Nullable;

public class WeatherReading implements Parcelable
{
    private final Float temperature;
    private final Float pressure;
    private final Float wind_speed;
    private final Integer rainfall;
    private final Integer snowfall;
    private final Integer wind_direction;
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
            @Nullable Float temperature,
            @Nullable Float pressure,
            @Nullable Float wind_speed,
            @Nullable Integer rainfall,
            @Nullable Integer snowfall,
            @Nullable Integer wind_direction,
            int overall_condition)
    {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind_speed = wind_speed;
        this.rainfall = rainfall;
        this.snowfall = snowfall;
        this.wind_direction = wind_direction;
        this.overall_condition = overall_condition;
    }

    private WeatherReading(Parcel in)
    {
        ClassLoader class_loader = WeatherReading.class.getClassLoader();

        this.temperature = (Float)in.readValue(class_loader);
        this.pressure = (Float)in.readValue(class_loader);
        this.wind_speed = (Float)in.readValue(class_loader);
        this.rainfall = (Integer)in.readValue(class_loader);
        this.snowfall = (Integer)in.readValue(class_loader);
        this.wind_direction = (Integer)in.readValue(class_loader);
        this.overall_condition = in.readInt();
    }

    @Nullable Float getTemperature() { return temperature; }
    @Nullable Float getPressure() { return pressure; }
    @Nullable Float getWindSpeed() { return wind_speed; }
    @Nullable Integer getRainfall() { return rainfall; }
    @Nullable Integer getSnowfall() { return snowfall; }
    @Nullable Integer getWindDirection() { return wind_direction; }
    int getConditionCode() { return overall_condition; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeValue(temperature);
        out.writeValue(pressure);
        out.writeValue(wind_speed);
        out.writeValue(rainfall);
        out.writeValue(snowfall);
        out.writeValue(wind_direction);
        out.writeInt(overall_condition);
    }

    HashMap<String,Object> prepareForUpload(LatLng lat_long)
    {
        HashMap<String,Object> data = new HashMap<>();

        if (temperature != null)  data.put("temperature", temperature);
        if (pressure != null) data.put("pressure", pressure);
        if (wind_speed != null) data.put("wind_speed", wind_speed);
        if (rainfall != null) data.put("rainfall", rainfall);
        if (snowfall != null) data.put("snowfall", snowfall);
        if (wind_direction != null) data.put("wind_direction", wind_direction);
        data.put("condition", overall_condition);
        data.put("latitude", lat_long.latitude);
        data.put("longitude", lat_long.longitude);
        data.put("timestamp", System.currentTimeMillis()/1000);

        return data;
    }
}
