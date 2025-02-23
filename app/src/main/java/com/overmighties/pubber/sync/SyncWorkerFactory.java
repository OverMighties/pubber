package com.overmighties.pubber.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;

import com.overmighties.pubber.app.notifications.NotificationWorker;
import com.overmighties.pubber.core.data.PubsRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SyncWorkerFactory extends WorkerFactory {

    private final PubsRepository pubsRepository;
    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        if (workerClassName.equals(NotificationWorker.class.getName())) {
            return new NotificationWorker(appContext, workerParameters);
        }
        return new SyncWorker(appContext, workerParameters, pubsRepository);
    }
}
