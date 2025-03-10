package com.overmighties.pubber.core.drinksdataset;

import com.overmighties.pubber.core.network.model.DrinkDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PubberDrinkOfflineApi {
    Single<List<DrinkDto>> getDrinks();

}
