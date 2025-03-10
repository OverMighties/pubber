package com.overmighties.pubber.core.database;

import static com.overmighties.pubber.core.data.mappers.DrinkDataMapper.mapEntityToDrink;
import static com.overmighties.pubber.core.data.mappers.PubDataMapper.mapToEntityDrinks;
import static com.overmighties.pubber.core.data.mappers.PubEntityMapper.mapFromEntityDrinks;

import android.util.Log;

import com.overmighties.pubber.core.model.Drink;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DrinksRoomDbSource implements  PubberDrinksLocalApi{

    private final AppDb appDb;
    public static final String TAG="DrinksRoomDbSource";
    @Override
    public Single<List<Drink>> getDrinks() {
        return appDb.drinksDao().getDrinks()
                .map(list->{
                    Log.i(TAG, "getDrinks: fetched drinks from db in number of "+list.size());
                    return mapFromEntityDrinks(list);
                });
    }

    @Override
    public void updateDrinks(List<Drink> drinks) throws RuntimeException {
        appDb.drinksDao().insertAll(mapToEntityDrinks(drinks));
    }

    @Override
    public void addDrink(Drink drink) throws RuntimeException {
        appDb.drinksDao().addDrink(mapEntityToDrink(drink));
    }

}
