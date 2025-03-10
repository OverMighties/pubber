package com.overmighties.pubber.app.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.annotations.NonNull;

public class NotificationWorker extends Worker {

    private static final String TAG = "NOTIFICATION_WORKER";
    private static final String PREF_NAME = "NotificationPrefs";
    private static final String LAST_NOTIFICATION_DATE = "lastNotificationDate";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @androidx.annotation.NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "Do work");
        try {
            if (isBetweenTimeWindow(12, 0,20, 0) && !hasNotificationBeenSentToday()) {
                saveNotificationSentStatus();
                NotificationHandler.createNotificationRemind(getApplicationContext());
            } else {
                return Result.retry();
            }
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Error sending notification", e);
            return Result.failure();
        }
    }

    private boolean isBetweenTimeWindow(int startHour, int startMinute, int endHour, int endMinute) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        Calendar time_start = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        Calendar time_end = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        time_start.set(Calendar.HOUR_OF_DAY, startHour);
        time_start.set(Calendar.MINUTE, startMinute);
        time_end.set(Calendar.HOUR_OF_DAY, endHour);
        time_end.set(Calendar.MINUTE, endMinute);
        Log.i(TAG, String.valueOf(calendar.getTimeInMillis() - time_start.getTimeInMillis()));
        return calendar.getTimeInMillis() >= time_start.getTimeInMillis() && calendar.getTimeInMillis() <= time_end.getTimeInMillis();
    }

    private boolean hasNotificationBeenSentToday() {
        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String lastDate = prefs.getString(LAST_NOTIFICATION_DATE, "");
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTime());

        return today.equals(lastDate);
    }

    private void saveNotificationSentStatus() {
        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTime());
        editor.putString(LAST_NOTIFICATION_DATE, today);
        editor.apply();
    }



}
