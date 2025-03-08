package com.overmighties.pubber.app;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.room.Room;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.overmighties.pubber.app.basic.PubberAppLifecycleListener;
import com.overmighties.pubber.app.basic.PubberAppLifecycleObserver;
import com.overmighties.pubber.app.notifications.NotificationHandler;
import com.overmighties.pubber.app.notifications.NotificationWorker;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.sync.SyncWorker;
import com.overmighties.pubber.sync.SyncWorkerFactory;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PubberApp extends Application implements Configuration.Provider, PubberAppLifecycleListener {

    public AppContainer appContainer;


    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new PubberAppLifecycleObserver(this));

        NotificationHandler.createNotificationChannels(this);

        AppDb db = Room.databaseBuilder(getApplicationContext(), AppDb.class, "db-pubber").build();
        appContainer=new AppContainer(db);
        appContainer.setLocationRepository(getApplicationContext());
        appContainer.setSavedPubsDataStore(getApplicationContext());
        WorkManager.initialize(this, getWorkManagerConfiguration());
        scheduleNotification();
        syncDataRepos();

    }

    private void scheduleNotification() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();
        PeriodicWorkRequest notificationRequest = new PeriodicWorkRequest.Builder(
                NotificationWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
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
                .setWorkerFactory(new SyncWorkerFactory(appContainer.getPubsRepository(), appContainer.getDrinksRepository()))
                .build();

    }

    public void changeStartActivityAlias(){
        if(SettingsHandler.ThemeHelper.getAppliedTheme(this).equals(SettingsHandler.ThemeHelper.getSavedTheme(this)))
            return;
        PackageManager pm = getPackageManager();
        ComponentName lightComponent = new ComponentName(this, "com.overmighties.pubber.app.aliases.StartActivityLightThemeAlias");
        ComponentName darkComponent = new ComponentName(this, "com.overmighties.pubber.app.aliases.StartActivityDarkThemeAlias");
        if(SettingsHandler.ThemeHelper.getSavedTheme(this).equals(SettingsHandler.ThemeHelper.THEME_DARK)) {
            pm.setComponentEnabledSetting(lightComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            pm.setComponentEnabledSetting(darkComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            SettingsHandler.ThemeHelper.saveAppliedTheme(this, SettingsHandler.ThemeHelper.THEME_DARK);
        } else {
            pm.setComponentEnabledSetting(darkComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            pm.setComponentEnabledSetting(lightComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            SettingsHandler.ThemeHelper.saveAppliedTheme(this, SettingsHandler.ThemeHelper.THEME_LIGHT);

        }
    }

    @Override
    public void onAppForegrounded() {
    }

    @Override
    public void onAppResumed() {
    }

    @Override
    public void onAppBackgrounded() {
        changeStartActivityAlias();
    }

}
