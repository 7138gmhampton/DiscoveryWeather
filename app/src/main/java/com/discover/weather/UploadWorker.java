package com.discover.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

class UploadWorker extends Worker
{
    private Map<String,Object> data_points;

    public UploadWorker(
        @NonNull Context context,
        @NonNull WorkerParameters workerParams)
    {
        super(context,workerParams);

        data_points = workerParams.getInputData().getKeyValueMap();
    }

//    @NonNull
//    @Override
//    public ListenableFuture<Result> startWork()
//    {
//        return CallbackToFutureAdapter.getFuture(completer -> {
//            UploadCallback callback = new UploadCallback()
//            {
//                @Override
//                public void onAchieve()
//                {
//
//                }
//
//                @Override
//                public void onError()
//                {
//
//                }
//            }
//        })
//    }

    @NonNull
    @Override
    public Result doWork()
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //Map<String,Object> data_points =
        database.collection("reading").add(data_points);

        return Result.success();
    }
}
