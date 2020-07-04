package com.discover.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;
import java.util.Objects;

public class ConditionFragment extends DialogFragment
{
    private ConditionFragmentListener listener_;

    public ConditionFragment() { }

    @Override
    @NonNull
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle(R.string.label_conditions).
            setAdapter(prepareAdapter(
                    (HashMap<Integer,String>) getArguments().getSerializable("options")),
                    new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener_.onClickCondition(ConditionFragment.this, which);
                }
            });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            listener_ = (ConditionFragmentListener)context;
        }
        catch (ClassCastException err) {
            throw new ClassCastException(getActivity().toString() +
                    " must implement ConditionFragmentListener");
        }
    }

    public interface ConditionFragmentListener
    {
        void onClickCondition(DialogFragment dialog, int index);
    }

    private ArrayAdapter prepareAdapter(HashMap<Integer,String> conditions)
    {
        String[] options = conditions.values().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1);

        adapter.addAll(options);

        return adapter;
    }
}
