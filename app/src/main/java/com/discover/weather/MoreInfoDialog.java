package com.discover.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MoreInfoDialog extends DialogFragment
{
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle saved_instance_state)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.text_wind_more_info);

        return builder.create();
    }
}
