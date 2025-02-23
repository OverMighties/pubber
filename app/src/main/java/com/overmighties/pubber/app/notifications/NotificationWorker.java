package com.overmighties.pubber.app.notifications;

import android.content.Context;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


import io.reactivex.rxjava3.annotations.NonNull;

public class NotificationWorker extends Worker {

    private static final String TAG = "NOTIFICATION_WORKER";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @Override
    public Result doWork() {
        try {
            NotificationHandler.createNotificationRemind(getApplicationContext());
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Error sending notification", e);
            return Result.failure();
        }
    }



}
