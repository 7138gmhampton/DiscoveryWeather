package com.discover.weather;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ConfirmAndTag extends AppCompatActivity implements OnMapReadyCallback,
        View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener
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

        findViewById(R.id.btnConfirm).setOnClickListener(this);
        Bundle map_view_bundle = null;
        if (savedInstanceState != null)
            map_view_bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        prepareMap(map_view_bundle);
        reading = getIntent().getExtras().getParcelable(("reading"));
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
//        this.map.moveCamera(CameraUpdateFactory.newLatLng(
//                new LatLng(56.463266, -2.974478)));
        this.map.getUiSettings().setRotateGesturesEnabled(false);
//        this.map.setMaxZoomPreference(30.0f);
        this.map.setMinZoomPreference(-10.0f);
//        Log.d("googlemap", this.map.getMaxZoomLevel() + " Max & " +
//            this.map.getMinZoomLevel() + " Min");
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(56.463266, -2.974478), 10.0f));
        enableLocationService();
    }

    @Override
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnConfirm: submitReading();
        }
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) { }

    private void prepareMap(Bundle map_view_bundle)
    {
        map_view = findViewById(R.id.mapView);
        map_view.onCreate(map_view_bundle);
        map_view.getMapAsync(this);
    }

    private void enableLocationService()
    {
        if (ContextCompat.checkSelfPermission(this,
            ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            this.map.setMyLocationEnabled(true);
        else ActivityCompat.requestPermissions(this,
            new String[]{ACCESS_FINE_LOCATION}, 200);
    }

    private void submitReading()
    {
        Data data_points = new Data.Builder()
            .putAll(reading.prepareForUpload(map.getCameraPosition().target))
            .build();

        WorkRequest upload_request = new OneTimeWorkRequest.Builder(UploadWorker.class)
            .setInputData(data_points)
            .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(upload_request);
        startActivity(new Intent(getApplicationContext(), ThankYou.class));
    }
}
