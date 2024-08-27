package com.overmighties.pubber.core.data;

import android.util.Log;

import com.overmighties.pubber.core.database.DbResponse;
import com.overmighties.pubber.core.database.PubberLocalApi;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.network.PubberNetworkApi;
import com.overmighties.pubber.sync.Result;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PubsRepository {
    public static final String TAG="PubsRepository";
    private final PubberNetworkApi remoteDataSource;
    private final PubberLocalApi localDataSource;
    public Result<DbResponse> sync() throws RuntimeException
    {

        try {
            LocalDateTime localDateTime=LocalDateTime.now();
            localDataSource.updatePubs(remoteDataSource.getPubs()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(list ->
                    {
                        List<Pub> pubList = new ArrayList<>();
                        for (var el : list) {
                            pubList.add(PubDtoMapper.mapFromDto(el, localDateTime));
                        }
                        Log.i(TAG, "sync: fetched "+pubList.size()+" pubs from remote data source");
                        return pubList;
                    }).blockingGet());
        }catch(Exception exception)
        {
            Log.e(TAG, "sync error: "+exception.getMessage()+"\n"+exception.getLocalizedMessage(),exception );
            return  new Result.Error<>(exception, exception.getLocalizedMessage());
        }
        return  new Result.Success<>(new DbResponse("Data synchronized", DbResponse.Status.OK));
    }
    public Single<List<Pub>> getPubs()
    {
        return localDataSource.getPubs()
                .subscribeOn(Schedulers.io());

    }


}
