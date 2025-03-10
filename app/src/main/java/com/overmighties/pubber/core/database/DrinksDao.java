package com.overmighties.pubber.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.overmighties.pubber.core.database.entities.BeerEntity;
import com.overmighties.pubber.core.database.entities.DrinkEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleDrinkCrossRefEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleEntity;
import com.overmighties.pubber.core.database.entities.DrinkWithAllEntities;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class DrinksDao {
    @Transaction
    @Query("SELECT * FROM drinks")
    abstract Single<List<DrinkWithAllEntities>> getDrinks();

    @Transaction
    @Query("SELECT * FROM drinks WHERE :drinkId=drink_id")
    abstract DrinkWithAllEntities getDrinkById(Long drinkId);

    @Transaction
    public synchronized void insertAll(List<DrinkWithAllEntities> drinks){
        for(DrinkWithAllEntities drinkWithAllEntities:drinks){
            addDrink(drinkWithAllEntities);
        }
    }

    @Transaction
    public synchronized void addDrink(DrinkWithAllEntities drinkWithAllEntities){
        insertDrink(drinkWithAllEntities.drink);
        if (drinkWithAllEntities.drinkStyles != null) {
            List<DrinkStyleEntity> drinkStyleEntities = drinkWithAllEntities.drinkStyles;
            insertDrinkStyles(drinkStyleEntities);
            for (DrinkStyleEntity drinkStyleEntity : drinkStyleEntities) {
                DrinkStyleDrinkCrossRefEntity drinkStyleDrinkCrossRefEntity = new DrinkStyleDrinkCrossRefEntity();
                drinkStyleDrinkCrossRefEntity.drinkStyleId = drinkStyleEntity.drinkStyleId;
                drinkStyleDrinkCrossRefEntity.drinkId = drinkStyleEntity.drinkId;

                insertDrinkStylesCrossRef(drinkStyleDrinkCrossRefEntity);
            }
        }

        if (drinkWithAllEntities.beer != null){
            insertBeer(drinkWithAllEntities.beer);
        }
    }


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDrink(DrinkEntity drink);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertDrinkStylesCrossRef(DrinkStyleDrinkCrossRefEntity refDrinkStyles);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDrinkStyles(List<DrinkStyleEntity> drinkStyles);
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertBeer(BeerEntity beer);
}
