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

public class ConditionFragment extends DialogFragment
{
    private ConditionFragmentListener listener;

    public ConditionFragment() { }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        String conditions[] =
        ArrayAdapter conditions = ArrayAdapter.createFromResource(getContext(),
                R.array.dummy_conditions, android.R.layout.simple_list_item_1);

//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection("condition").get().addOnCompleteListener(
//            new OnCompleteListener<QuerySnapshot>()
//            {
//                public void onComplete(@NonNull Task<QuerySnapshot> task)
//                {
//                    if (task.isSuccessful())
//                        for (QueryDocumentSnapshot document : task.getResult())
//                            Log.d("database", document.getId() + ": " +
//                                    document.getString("display"));
//                    else Log.d("database", "Error querying: " +
//                            task.getException());
//                }
//            });
        pollOverallConditionsFromDatabase();
        builder.setTitle(R.string.label_conditions).
//                setItems(R.array.dummy_conditions, new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        listener.onClickCondition(ConditionFragment.this, which);
//                    }
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
        public void onClickCondition(DialogFragment dialog, int index);
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
                        for (QueryDocumentSnapshot document : task.getResult())
                            Log.d("database", document.getId() + ": " +
                                    document.getString("display"));
                    else Log.d("database", "Error querying: " +
                            task.getException());
                }
            });
    }
}
