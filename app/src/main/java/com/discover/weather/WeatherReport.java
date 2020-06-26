package com.discover.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class WeatherReport extends AppCompatActivity implements OnSeekBarChangeListener,
        OnClickListener, ConditionFragment.ConditionFragmentListener
{
    private int selected_condition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        ((SeekBar)findViewById(R.id.seekWind)).setOnSeekBarChangeListener(this);
        findViewById(R.id.textConditionSelected).setOnClickListener(this);

        selected_condition = -1;
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
        //String[] conditions = getResources().getStringArray(R.array.dummy_conditions);

        selected_condition = index;

//        TextView condition_display = findViewById(R.id.textConditionSelected);
//        condition_display.setText(conditions[selected_condition]);
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
        else {
            String[] conditions = getResources().getStringArray(R.array.dummy_conditions);
            condition_display.setText(conditions[selected_condition]);
        }
    }
}
