package com.overmighties.pubber.sync;

import android.app.Activity;
import android.content.Context;

import com.overmighties.pubber.core.database.DbResponse;
import com.overmighties.pubber.sync.Result;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.overmighties.pubber.core.data.PubsRepository;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SyncWorker extends Worker {

    private final PubsRepository pubsRepository;
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, PubsRepository pubsRepository) {
        super(context, workerParams);
        this.pubsRepository=pubsRepository;
    }

    @NonNull
    @Override
    public Result doWork() {
        com.overmighties.pubber.sync.Result<DbResponse> dbResponseResult= Single.fromCallable(pubsRepository::sync)
                .subscribeOn(Schedulers.io())
                .blockingGet();
        if(dbResponseResult instanceof   com.overmighties.pubber.sync.Result.Success)
        {
            return Result.success();
        }
        return Result.failure(new Data.Builder()
                .putString("error_message",((com.overmighties.pubber.sync.Result.Error<DbResponse>) dbResponseResult).message)
                .build());
    }
}
