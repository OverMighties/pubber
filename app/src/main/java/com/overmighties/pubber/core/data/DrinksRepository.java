package com.overmighties.pubber.core.data;

import android.util.Log;

import com.overmighties.pubber.core.data.mappers.PubDtoMapper;
import com.overmighties.pubber.core.database.DbResponse;
import com.overmighties.pubber.core.database.PubberDrinksLocalApi;
import com.overmighties.pubber.core.database.PubberLocalApi;
import com.overmighties.pubber.core.drinksdataset.PubberDrinkOfflineApi;
import com.overmighties.pubber.core.model.Drink;
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
public class DrinksRepository {
    public static final String TAG="DrinksRepository";
    private final PubberDrinksLocalApi localDataSource;
    private final PubberDrinkOfflineApi offlineDataSource;
    //TODO get all drinks from PubsDataSource and update DrinksRepository from them
    public Result<DbResponse> sync() throws RuntimeException
    {

        try {
            localDataSource.updateDrinks(offlineDataSource.getDrinks()
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(list -> PubDtoMapper.mapFromDtoDrinks(list))
                            .blockingGet());
        }catch(Exception exception)
        {
            Log.e(TAG, "sync error: "+exception.getMessage()+"\n"+exception.getLocalizedMessage(),exception );
            return  new Result.Error<>(exception, exception.getLocalizedMessage());
        }
        return  new Result.Success<>(new DbResponse("Drink Data synchronized", DbResponse.Status.OK));
    }
    public Single<List<Drink>> getDrinks()
    {
        return localDataSource.getDrinks()
                .subscribeOn(Schedulers.io());

    }
}
