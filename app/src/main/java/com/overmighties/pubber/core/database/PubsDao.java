package com.overmighties.pubber.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.overmighties.pubber.core.database.model.DrinkEntity;
import com.overmighties.pubber.core.database.model.PhotoEntity;
import com.overmighties.pubber.core.database.model.PubDrinkCrossRefEntity;
import com.overmighties.pubber.core.database.model.PubEntity;
import com.overmighties.pubber.core.database.model.PubWithAllEntities;
import com.overmighties.pubber.core.database.model.RatingsEntity;
import com.overmighties.pubber.core.model.Ratings;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
@Dao
public abstract class PubsDao  {

    @Transaction
    @Query("SELECT * FROM pubs")
    abstract Single<List<PubWithAllEntities>> getPubsWithEntities();
    @Transaction
    @Query("SELECT * FROM pubs WHERE :pubId=pub_id")
    abstract Single<PubWithAllEntities> getPubById(Long pubId);

    @Transaction
    @Query("SELECT * FROM pub_drink_cross_ref WHERE :pubId=pub_id")
    abstract Single<List<PubDrinkCrossRefEntity>> getDrinksByPubId(Long pubId);

    @Transaction
    public synchronized void insertAll(List<PubWithAllEntities> pubs)
    {

        for (PubWithAllEntities pubWithAllEntities : pubs) {
            insertPub(pubWithAllEntities.pub);
            if(pubWithAllEntities.drinks!=null)
            {
                insertDrinks(pubWithAllEntities.drinks);
                var drinks=getDrinksByPubId(pubWithAllEntities.pub.pubId).blockingGet();
                for(var drink : drinks)
                {
                    insertPubDrinkCrossRef(new PubDrinkCrossRefEntity(pubWithAllEntities.pub.pubId,drink.drinkId));
                }
            }
            if(pubWithAllEntities.photos!=null)
                insertPhotos(pubWithAllEntities.photos);
            if(pubWithAllEntities.ratings!=null)
                insertRatings(pubWithAllEntities.ratings);
        }
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPub(PubEntity pubs);
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDrinks(List<DrinkEntity> drinks);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPubDrinkCrossRef(PubDrinkCrossRefEntity refEntity);
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPhotos(List<PhotoEntity> photos);
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertRatings(RatingsEntity ratings);
}
