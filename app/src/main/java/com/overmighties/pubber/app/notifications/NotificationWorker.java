package com.overmighties.pubber.app.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.util.Pair;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.util.DayOfWeekConverter;

import java.util.Calendar;
import java.util.TimeZone;

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
