package com.discover.weather;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        Bundle map_view_bundle = null;
        if (savedInstanceState != null)
            map_view_bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        prepareMap(map_view_bundle);
        reading = getIntent().getExtras().getParcelable(("reading"));

        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
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
        this.map.getUiSettings().setRotateGesturesEnabled(false);
        this.map.setMinZoomPreference(-10.0f);
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(
            new LatLng(56.463266, -2.974478), 10.0f));

        enableLocationService();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnConfirm: confirmSubmission(); break;
            case R.id.btnCancel: confirmCancellation();
        }
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) { }

    private void confirmCancellation()
    {
        final AlertDialog.Builder cancel_dialog = new AlertDialog.Builder(this);

        cancel_dialog.setMessage(getResources().getString(R.string.text_cancel))
            .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
            })
            .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog,int which)
                {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
            });

        cancel_dialog.create().show();
    }

    private void confirmSubmission()
    {
        final AlertDialog.Builder submit_dialog = new AlertDialog.Builder(this);

        submit_dialog.setMessage(authorSummary())
            .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
            })
            .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) { submitReading(); }
            });

        submit_dialog.create().show();
    }

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

    private String authorSummary()
    {
        String summary = "Details to be submitted:" + System.lineSeparator();

        summary += ConditionOptions.getInstance().getOptions().get(reading.getConditionCode()) +
            System.lineSeparator();
        summary += appendDetail("Temperature: ", reading.getTemperature(), R.string.label_temp_units);
        summary += appendDetail("Pressure: ", reading.getPressure(), R.string.label_pressure_unit);
        summary += appendDetail("Wind Speed: ", reading.getWindSpeed(), R.string.label_speed_unit);
        summary += appendDetail("Wind Direction: ", reading.getWindDirection(), R.string.units_direction);
        summary += appendDetail("Rainfall: ", reading.getRainfall(), R.string.label_precipitation_unit);
        summary += appendDetail("Snowfall: ", reading.getSnowfall(), R.string.label_precipitation_unit);

        summary += System.lineSeparator() + System.lineSeparator() + "Are these details correct?";
        return summary;
    }

    @SuppressWarnings("DefaultLocale")
    private String appendDetail(String name, Float metric, int unit)
    {
        String detail = "";

        if (metric != null) {
            detail += name;
            detail += String.format("%.2f", metric);
            detail += getResources().getString(unit);
            detail += System.lineSeparator();
        }

        return detail;
    }

    private String appendDetail(String name, Integer metric, int unit)
    {
        String detail = "";

        if (metric != null) {
            detail += name;
            detail += metric.toString();
            detail += getResources().getString(unit);
            detail += System.lineSeparator();
        }

        return detail;
    }
}
