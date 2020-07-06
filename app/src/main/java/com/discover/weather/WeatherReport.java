package com.discover.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class WeatherReport extends AppCompatActivity implements OnSeekBarChangeListener,
        OnClickListener, ConditionFragment.ConditionFragmentListener
{
    private int selected_condition_;
    private HashMap<Integer,String> overall_condition_options_;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        ((SeekBar)findViewById(R.id.seekWind)).setOnSeekBarChangeListener(this);
        findViewById(R.id.textConditionSelected).setOnClickListener(this);
        findViewById(R.id.btnConfirm).setOnClickListener(this);
        populateConditionOptions();

        selected_condition_ = -1;
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateWindDirectionDisplay();
        updateSelectedConditionDisplay();
    }

    @Override
    public void onProgressChanged(@NonNull SeekBar seekBar, int progress, boolean fromUser)
    {
        if (seekBar.getId() == R.id.seekWind) updateWindDirectionDisplay();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onClick(@NonNull View view)
    {
        switch (view.getId()) {
            case R.id.textConditionSelected: showConditionDialog(); break;
            case R.id.btnConfirm: submitReadingForConfirmation();
        }
    }

    @Override
    public void onClickCondition(int index)
    {
        selected_condition_ = index;

        updateSelectedConditionDisplay();
    }

    private void updateWindDirectionDisplay()
    {
        ((TextView)findViewById(R.id.textDirectionDisplay)).
                setText(getString(
                R.string.display_wind_direction,
                ((SeekBar)findViewById(R.id.seekWind)).getProgress()));
    }

    private void updateSelectedConditionDisplay()
    {
        TextView condition_display = findViewById(R.id.textConditionSelected);

        if (selected_condition_ < 0) condition_display.setText(R.string.placeholder_condition);
        else condition_display.setText(overall_condition_options_.get(selected_condition_));
    }

    @SuppressLint("UseSparseArrays")
    @SuppressWarnings("ConstantConditions")
    private void populateConditionOptions()
    {
        overall_condition_options_ = new HashMap<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("condition").get().addOnCompleteListener(new
            OnCompleteListener<QuerySnapshot>()
            {
             @Override
             public void onComplete(@NonNull Task<QuerySnapshot> task)
             {
                 if (task.isSuccessful())
                     for (QueryDocumentSnapshot document : task.getResult()) {
                         overall_condition_options_.put(Integer.parseInt(document.getId()),
                                 document.getString("display"));
                     }
                 else Log.e("database", "Error querying: " + task.getException());
             }
            });
    }

    private void showConditionDialog()
    {
        Bundle condition_options_bundle = new Bundle();
        condition_options_bundle.putSerializable("options",
                overall_condition_options_);

        DialogFragment condition_dialog = new ConditionFragment();
        condition_dialog.setArguments(condition_options_bundle);

        condition_dialog.show(getSupportFragmentManager(), "conditions");
    }

    private void submitReadingForConfirmation()
    {
        WeatherReading reading = new WeatherReading(
                setNumericMetric(findViewById(R.id.editTemp)),
                setNumericMetric(findViewById(R.id.editPressure)),
                setNumericMetric(findViewById(R.id.editSpeed)));

        Intent start_confirmation = new Intent(getApplicationContext(), ConfirmAndTag.class);
        start_confirmation.putExtra("reading", reading);

        startActivity(start_confirmation);
    }

    @Nullable
    private Float setNumericMetric(@NonNull View source_field)
    {
        EditText source_text = (EditText)source_field;

        if (source_text.getText().toString().trim().length() < 1) return null;
        else return Float.parseFloat(source_text.getText().toString());
    }
}
