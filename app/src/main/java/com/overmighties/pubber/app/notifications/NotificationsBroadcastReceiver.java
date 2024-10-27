package com.overmighties.pubber.app.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class NotificationsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            sheduleNotification(context);
        } else if (PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED.equals(action)) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (!pm.isDeviceIdleMode()) {
                sheduleNotification(context);
            }
        }
    }

    public void sheduleNotification(Context context){
        Calendar time_now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        Calendar time_target = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        time_target.set(Calendar.HOUR_OF_DAY, 19);
        time_target.set(Calendar.MINUTE, 0);
        time_target.set(Calendar.SECOND, 0);

        long delay_ms = time_target.getTimeInMillis() - time_now.getTimeInMillis();

        if(delay_ms < 0){
            time_target.add(Calendar.HOUR_OF_DAY, -1);
            delay_ms = time_target.getTimeInMillis() - time_now.getTimeInMillis();
            if(delay_ms <= 0){
                time_target.add(Calendar.HOUR_OF_DAY, 24);
                delay_ms = time_target.getTimeInMillis() - time_now.getTimeInMillis();
            }

        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();
        PeriodicWorkRequest notificationRequest = new PeriodicWorkRequest.Builder(
                NotificationWorker.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(delay_ms, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "sentNotification",
                ExistingPeriodicWorkPolicy.UPDATE,
                notificationRequest);
    }
}
