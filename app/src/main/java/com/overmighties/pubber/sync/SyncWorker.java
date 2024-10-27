package com.overmighties.pubber.sync;

import android.content.Context;

import com.overmighties.pubber.core.data.DrinksRepository;
import com.overmighties.pubber.core.database.DbResponse;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.overmighties.pubber.core.data.PubsRepository;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SyncWorker extends Worker {

    private final PubsRepository pubsRepository;
    private final DrinksRepository drinksRepository;
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, PubsRepository pubsRepository, DrinksRepository drinksRepository) {
        super(context, workerParams);
        this.pubsRepository=pubsRepository;
        this.drinksRepository = drinksRepository;
    }

    @NonNull
    @Override
    public Result doWork() {
        com.overmighties.pubber.sync.Result<DbResponse> dbResponseResult= Single.fromCallable(pubsRepository::sync)
                .subscribeOn(Schedulers.io())
                .blockingGet();
        com.overmighties.pubber.sync.Result<DbResponse> dbResponseResultDrinks = Single.fromCallable(drinksRepository::sync)
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
