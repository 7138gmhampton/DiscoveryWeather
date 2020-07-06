package com.discover.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

class UploadWorker extends Worker
{
    public UploadWorker(
        @NonNull Context context,
        @NonNull WorkerParameters workerParams)
    {
        super(context,workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        return Result.success();
    }
}
