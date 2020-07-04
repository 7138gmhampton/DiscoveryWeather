package com.discover.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class ConditionFragment extends DialogFragment
{
    private ConditionFragmentListener listener;
    private Map<Integer, String> overall_condition_options_;

    public ConditionFragment() { }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle condition_options_argument = getArguments();
        HashMap<Integer,String> temp_map = (HashMap<Integer, String>) condition_options_argument.getSerializable("options");
        overall_condition_options_ = new HashMap<Integer,String>();
        overall_condition_options_.putAll(temp_map);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] condition_options = overall_condition_options_.values().toArray(new String[overall_condition_options_.size()]);
        ArrayAdapter conditions = new ArrayAdapter(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1);
        conditions.addAll(condition_options);

        builder.setTitle(R.string.label_conditions).
            setAdapter(conditions, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onClickCondition(ConditionFragment.this, which);
                }

            });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            listener = (ConditionFragmentListener)context;
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

    private void pollOverallConditionsFromDatabase()
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("condition").get().addOnCompleteListener(
            new OnCompleteListener<QuerySnapshot>()
            {
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if (task.isSuccessful())
                        //noinspection ConstantConditions
                        for (QueryDocumentSnapshot document : task.getResult())
                            Log.d("database", document.getId() + ": " +
                                    document.getString("display"));
                    else Log.d("database", "Error querying: " +
                            task.getException());
                }
            });
    }

    private void logConditionOptions()
    {
        Log.d("pass", "Condition set size: " + overall_condition_options_.size());
        for (Map.Entry<Integer, String> condition : overall_condition_options_.entrySet())
            Log.d("pass",condition.getKey().toString() + " -> " + condition.getValue());
    }
}
