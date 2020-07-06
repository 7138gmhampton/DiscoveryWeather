package com.discover.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UploadWorker extends Worker
{
    private Map<String,Object> data_points;

    public UploadWorker(
        @NonNull Context context,
        @NonNull WorkerParameters worker_params)
    {
        super(context,worker_params);

        data_points = worker_params.getInputData().getKeyValueMap();
    }

    @NonNull
    @Override
    public Result doWork()
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("reading").add(data_points);

        return Result.success();
    }
}
