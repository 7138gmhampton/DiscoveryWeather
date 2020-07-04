package com.discover.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class WeatherReport extends AppCompatActivity implements OnSeekBarChangeListener,
        OnClickListener, ConditionFragment.ConditionFragmentListener
{
    private int selected_condition;
    private Map<Integer,String> overall_condition_options;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        ((SeekBar)findViewById(R.id.seekWind)).setOnSeekBarChangeListener(this);
        findViewById(R.id.textConditionSelected).setOnClickListener(this);
        overall_condition_options = new HashMap<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("condition").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        overall_condition_options.put(Integer.parseInt(document.getId()),
                                document.getString("display"));
//                        logDrawnConditions();
                    }
                else Log.e("database", "Error querying: " + task.getException());
                logDrawnConditions();
            }
        });

        selected_condition = -1;
//        Log.d("database", "Condition set size: " + overall_condition_options.size());
//        for (Map.Entry<Integer, String> condition : overall_condition_options.entrySet())
//            Log.d("database",condition.getKey().toString() + " -> " + condition.getValue());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateWindDirectionDisplay();
        updateSelectedConditionDisplay();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if (seekBar.getId() == R.id.seekWind) updateWindDirectionDisplay();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.textConditionSelected:
                DialogFragment condition_dialog = new ConditionFragment();
                condition_dialog.show(getSupportFragmentManager(), "conditions");
        }
    }

    @Override
    public void onClickCondition(DialogFragment dialog, int index)
    {
        selected_condition = index;

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

        if (selected_condition < 0) condition_display.setText(R.string.placeholder_condition);
        else condition_display.setText(getResources().
                    getStringArray(R.array.dummy_conditions)[selected_condition]);
    }

    private void logDrawnConditions()
    {
        Log.d("database", "Condition set size: " + overall_condition_options.size());
        for (Map.Entry<Integer, String> condition : overall_condition_options.entrySet())
            Log.d("database",condition.getKey().toString() + " -> " + condition.getValue());
    }
}
