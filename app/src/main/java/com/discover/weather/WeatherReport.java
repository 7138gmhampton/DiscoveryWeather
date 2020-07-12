package com.discover.weather;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;

public class WeatherReport extends AppCompatActivity implements OnSeekBarChangeListener,
        OnClickListener, ConditionFragment.ConditionFragmentListener
{
    private int selected_condition;
    private HashMap<Integer,String> overall_condition_options;
    private ColorStateList default_colour;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        default_colour = ((TextView)findViewById(R.id.textConditions)).getTextColors();
        overall_condition_options = ConditionOptions.getInstance().getOptions();
        if (savedInstanceState != null)
            selected_condition = savedInstanceState.getInt("condition", -1);
        else selected_condition = -1;

        attachListenersToActivity();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateWindDirectionDisplay();
        updateSelectedConditionDisplay();
        toggleDirectionControls();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle out_state)
    {
        super.onSaveInstanceState(out_state);
        out_state.putInt("condition", selected_condition);
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
            case R.id.btnConfirm: submitReadingForConfirmation(); break;
            case R.id.btnNorth:
            case R.id.btnNorthEast:
            case R.id.btnEast:
            case R.id.btnSouthEast:
            case R.id.btnSouth:
            case R.id.btnSouthWest:
            case R.id.btnWest:
            case R.id.btnNorthWest:
                changeDirectionToMatchCardinal(((Button)view).getText().toString());
                break;
            case R.id.checkIndeterminateWind: toggleDirectionControls(); break;
            case R.id.btnWindMoreInfo:
                new MoreInfoDialog().show(getSupportFragmentManager(), "more_info");
        }
    }

    @Override
    public void onClickCondition(int index)
    {
        selected_condition = index;

        updateSelectedConditionDisplay();
    }

    private void attachListenersToActivity()
    {
        ((SeekBar)findViewById(R.id.seekWind)).setOnSeekBarChangeListener(this);
        findViewById(R.id.textConditionSelected).setOnClickListener(this);
        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.checkIndeterminateWind).setOnClickListener(this);
        findViewById(R.id.btnWindMoreInfo).setOnClickListener(this);
        for (View view : findViewById(R.id.layoutCardinalsMain).getTouchables())
            view.setOnClickListener(this);
        for (View view : findViewById(R.id.layoutCardinalsOff).getTouchables())
            view.setOnClickListener(this);
    }

    private void updateWindDirectionDisplay()
    {
        ((TextView)findViewById(R.id.textDirectionDisplay))
                .setText(getString(R.string.display_wind_direction,
                    ((SeekBar)findViewById(R.id.seekWind)).getProgress()));
        ((TextView)findViewById(R.id.textDirectionDisplayCardinal))
            .setText(Cardinal.getCardinal(((SeekBar)findViewById(R.id.seekWind)).getProgress()));
    }

    private void updateSelectedConditionDisplay()
    {
        TextView condition_display = findViewById(R.id.textConditionSelected);

        if (selected_condition < 0) condition_display.setText(R.string.placeholder_condition);
        else {
            condition_display.setText(overall_condition_options.get(selected_condition));
            removeHighlight();
        }
    }

    private void removeHighlight()
    {
        TextView condition_label = findViewById(R.id.textConditions);
        condition_label.setTextColor(default_colour);
        condition_label.setTypeface(null, Typeface.NORMAL);
    }

    private void showConditionDialog()
    {
        Bundle condition_options_bundle = new Bundle();
        condition_options_bundle.putSerializable("options", overall_condition_options);

        DialogFragment condition_dialog = new ConditionFragment();
        condition_dialog.setArguments(condition_options_bundle);

        condition_dialog.show(getSupportFragmentManager(), "conditions");
    }

    private void submitReadingForConfirmation()
    {
        if (selected_condition < 0) {
            notifyOfMandatoryConditionSelection();
            return;
        }

        WeatherReading reading = new WeatherReading(
                setFloatMetric(findViewById(R.id.editTemp)),
                setFloatMetric(findViewById(R.id.editPressure)),
                setFloatMetric(findViewById(R.id.editSpeed)),
                setIntegerMetric(findViewById(R.id.editRain)),
                setIntegerMetric(findViewById(R.id.editSnow)),
                setWindDirection(),
                selected_condition);

        Intent start_confirmation = new Intent(getApplicationContext(), ConfirmAndTag.class);
        start_confirmation.putExtra("reading", reading);

        startActivity(start_confirmation);
    }

    @Nullable
    private Float setFloatMetric(@NonNull View source_field)
    {
        String source_text = ((EditText)source_field).getText().toString();

        if (source_text.trim().length() < 1) return null;
        else return Float.parseFloat(source_text);
    }

    @Nullable
    private Integer setIntegerMetric(@NonNull View source_field)
    {
        String source_text = ((EditText)source_field).getText().toString();

        if (source_text.trim().length() < 1) return null;
        else return Integer.parseInt(source_text);
    }

    @Nullable
    private Integer setWindDirection()
    {
        if (((CheckBox)findViewById(R.id.checkIndeterminateWind)).isChecked())
            return null;
        return ((SeekBar)findViewById(R.id.seekWind)).getProgress();
    }

    private void notifyOfMandatoryConditionSelection()
    {
        Toast.makeText(getApplicationContext(), "The overall condition must be selected",
            Toast.LENGTH_LONG).show();

        applyHighlight();
    }

    private void applyHighlight()
    {
        TextView condition_label = findViewById(R.id.textConditions);
        condition_label.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        condition_label.setTypeface(null,Typeface.BOLD);
    }

    private void changeDirectionToMatchCardinal(String cardinal)
    {
        ((SeekBar)findViewById(R.id.seekWind)).setProgress(Cardinal.getAbsoluteDirection(cardinal));
    }

    private void toggleDirectionControls()
    {
        boolean state = !((CheckBox)findViewById(R.id.checkIndeterminateWind)).isChecked();

        toggleSingleButtonSet((ViewGroup)findViewById(R.id.layoutCardinalsMain), state);
        toggleSingleButtonSet((ViewGroup)findViewById(R.id.layoutCardinalsOff), state);
        ((View)findViewById(R.id.seekWind)).setEnabled(state);
        ((View)findViewById(R.id.textDirectionDisplay)).setEnabled(state);
        ((View)findViewById(R.id.textDirectionDisplayCardinal)).setEnabled(state);
    }

    private void toggleSingleButtonSet(@NonNull ViewGroup set, boolean state)
    {
        for (int iii = 0; iii < set.getChildCount(); ++iii) set.getChildAt(iii).setEnabled(state);
    }
}
