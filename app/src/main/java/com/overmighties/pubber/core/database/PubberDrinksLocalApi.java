package com.overmighties.pubber.core.database;

import com.overmighties.pubber.core.model.Drink;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PubberDrinksLocalApi {
    Single<List<Drink>> getDrinks();
    void updateDrinks(List<Drink> drinks) throws RuntimeException;
    void addDrink(Drink drink) throws RuntimeException;
}
