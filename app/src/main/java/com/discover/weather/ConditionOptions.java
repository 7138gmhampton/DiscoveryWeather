package com.discover.weather;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class ConditionOptions
{
    private HashMap<Integer,String> options;

    private static final ConditionOptions instance = new ConditionOptions();

    public static ConditionOptions getInstance() { return instance; }
    public HashMap<Integer,String> getOptions()
    {
        if (options == null) loadOptions();

        return options;
    }

    private void loadOptions()
    {
        options = new HashMap<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("condition").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                    //noinspection ConstantConditions
                    for (QueryDocumentSnapshot document : task.getResult())
                        options.put(Integer.parseInt(document.getId()),
                            document.getString("display"));
                else Log.e("database", "Error querying: " + task.getException());
            }
        });
    }
}
