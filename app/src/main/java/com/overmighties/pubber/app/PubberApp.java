package com.overmighties.pubber.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.overmighties.pubber.R;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.sync.SyncWorker;
import com.overmighties.pubber.sync.SyncWorkerFactory;
import com.overmighties.pubber.util.SettingsHandler;

public class PubberApp extends Application implements Configuration.Provider{

    private AppDb db;
    public AppContainer appContainer;


    @Override
    public void onCreate() {
        super.onCreate();

        db=Room.databaseBuilder(getApplicationContext(),AppDb.class,"db-pubber").build();
        appContainer=new AppContainer(db);
        syncDataRepos();
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
