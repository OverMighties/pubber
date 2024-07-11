package com.overmighties.pubber.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.notifications.NotificationHandler;
import com.overmighties.pubber.app.notifications.NotificationWorker;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.sync.SyncWorker;
import com.overmighties.pubber.sync.SyncWorkerFactory;
import com.overmighties.pubber.util.SettingsHandler;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PubberApp extends Application implements Configuration.Provider{

    private AppDb db;
    public AppContainer appContainer;


    @Override
    public void onCreate() {
        super.onCreate();

        NotificationHandler.createNotificationChannels(this);


        db=Room.databaseBuilder(getApplicationContext(),AppDb.class,"db-pubber").build();
        appContainer=new AppContainer(db);

        WorkManager.initialize(this, getWorkManagerConfiguration());
        sheduleNotification();
        syncDataRepos();


    }

    private void sheduleNotification() {
        Calendar time_now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        Calendar time_target = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        time_target.set(Calendar.HOUR_OF_DAY, 18);
        time_target.set(Calendar.MINUTE, 0);
        time_target.set(Calendar.SECOND, 0);

        long delay_ms = time_target.getTimeInMillis() - time_now.getTimeInMillis();

        if(delay_ms < 0 ){
            time_target.add(Calendar.HOUR_OF_DAY, 24);
            delay_ms = time_target.getTimeInMillis() - time_now.getTimeInMillis();

        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();
        PeriodicWorkRequest notificationRequest = new PeriodicWorkRequest.Builder(
                NotificationWorker.class, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(delay_ms, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "sentNotification",
                ExistingPeriodicWorkPolicy.UPDATE,
                notificationRequest);
    }

        private void syncDataRepos()
    {
        Constraints constraints=new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkRequest sync= new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(sync);
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .setWorkerFactory(new SyncWorkerFactory(appContainer.getPubsRepository()))
                .build();

    }


}
