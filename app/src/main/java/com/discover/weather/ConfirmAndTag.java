package com.discover.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class ConfirmAndTag extends AppCompatActivity implements OnMapReadyCallback
{
    private MapView map_view;
    private GoogleMap map;
    private WeatherReading reading;

    private static final String MAP_VIEW_BUNDLE_KEY = "MAPVIEWBUNDLEKEY";

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
        // Log check the parcellable transfer
//        WeatherReading reading = (WeatherReading)getIntent().getExtras().getParcelable("reading");
        if (reading.getTemperature() == null)
            Log.d("pass", "Temp unset");
        else Log.d("pass", "The temp is: " + reading.getTemperature());
        if (reading.getPressure() == null)
            Log.d("pass", "Pressure unset");
        else Log.d("pass", "The pressure is: " + reading.getPressure());
    }

    @Override
    public void onSaveInstanceState(Bundle out_state)
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
