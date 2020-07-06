package com.discover.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings("FieldCanBeLocal")
public class ConfirmAndTag extends AppCompatActivity implements OnMapReadyCallback
{
    private MapView map_view;
    private GoogleMap map;
    private WeatherReading reading;

    private static final String MAP_VIEW_BUNDLE_KEY = "MAP_VIEW_BUNDLE_KEY";

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_and_tag);

        Bundle map_view_bundle = null;
        if (savedInstanceState != null)
            map_view_bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        map_view = findViewById(R.id.mapView);
        map_view.onCreate(map_view_bundle);
        map_view.getMapAsync(this);
        reading = getIntent().getExtras().getParcelable(("reading"));
//        WeatherReading reading = (WeatherReading)getIntent().getExtras().getParcelable("reading");
        if (reading.getTemperature() == null)
            Log.d("passing", "Temp unset");
        else Log.d("passing", "The temp is: " + reading.getTemperature());
        if (reading.getPressure() == null)
            Log.d("passing", "Pressure unset");
        else Log.d("passing", "The pressure is: " + reading.getPressure());
        if (reading.getWindSpeed() == null)
            Log.d("passing", "Wind speed unset");
        else Log.d("passing", "The wind speed is: " + reading.getWindSpeed());
        if (reading.getRainfall() == null)
            Log.d("passing", "Rainfall unset");
        else Log.d("passing", "The rainfall is: " + reading.getRainfall());
        if (reading.getSnowfall() == null)
            Log.d("passing", "Snowfall unset");
        else Log.d("passing", "The snowfall is: " + reading.getSnowfall());
        Log.d("passing", "The wind dir is: " + reading.getWindDirection());
        Log.d("passing", "The condition code is: " + reading.getConditionCode());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle out_state)
    {
        super.onSaveInstanceState(out_state);

        Bundle map_view_bundle = out_state.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (map_view_bundle == null) {
            map_view_bundle = new Bundle();
            out_state.putBundle(MAP_VIEW_BUNDLE_KEY, map_view_bundle);
        }

        map_view.onSaveInstanceState(map_view_bundle);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        map_view.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        map_view.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        map_view.onStop();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        map_view.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        map_view.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        map_view.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        this.map = map;
        this.map.setMinZoomPreference(12);
        this.map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(56.463266, -2.974478)));
    }
}
