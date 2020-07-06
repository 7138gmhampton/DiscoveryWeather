package com.discover.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

class UploadWorker extends ListenableWorker
{
    public UploadWorker(
        @NonNull Context context,
        @NonNull WorkerParameters workerParams)
    {
        super(context,workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork()
    {
        return null;
    }

//    @NonNull
//    @Override
//    public Result doWork()
//    {
//        return Result.success();
//    }
}
