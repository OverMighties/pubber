package com.overmighties.pubber.core.drinksdataset;

import com.overmighties.pubber.core.network.model.DrinkDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OfflineDrinksDataSource implements PubberDrinkOfflineApi {

    @Override
    public Single<List<DrinkDto>> getDrinks() {
        return Single.create(emit -> emit.onSuccess(DrinksDataSet.getInstance().getDataSet()));
    }
}
