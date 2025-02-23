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
import com.overmighties.pubber.core.database.entities.PhotoEntity;
import com.overmighties.pubber.core.database.entities.PubDrinkCrossRefEntity;
import com.overmighties.pubber.core.database.entities.PubEntity;
import com.overmighties.pubber.core.database.entities.PubWithAllEntities;
import com.overmighties.pubber.core.database.entities.RatingsEntity;
import com.overmighties.pubber.core.database.entities.TagEntity;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
@Dao
public abstract class PubsDao {

    @Transaction
    @Query("SELECT * FROM pubs")
    abstract Single<List<PubWithAllEntities>> getPubsWithEntities();

    @Transaction
    @Query("SELECT * FROM pubs WHERE :pubId=pub_id")
    abstract Single<PubWithAllEntities> getPubById(Long pubId);

    @Transaction
    @Query("SELECT * FROM pub_drink_cross_ref WHERE :pubId=pub_id")
    abstract Single<List<PubDrinkCrossRefEntity>> getDrinksCrossRefByPubId(Long pubId);

    @Transaction
    @Query("SELECT * FROM drink_style_drink_cross_ref WHERE :drinkId=drink_id")
    abstract Single<List<DrinkStyleDrinkCrossRefEntity>> getDrinkStylesCrossRefByDrinkId(Long drinkId);

    @Transaction
    @Query("SELECT * FROM drinks WHERE :name=name")
    abstract Single<DrinkWithAllEntities> getDrinkByName(String name);

    @Transaction
    @Query("SELECT * FROM styles WHERE :name=style_name")
    abstract Single<DrinkWithAllEntities> getDrinkStyleByName(String name);

    @Transaction
    public synchronized void insertAll(List<PubWithAllEntities> pubs) {

        for (PubWithAllEntities pubWithAllEntities : pubs) {

            insertPub(pubWithAllEntities.pub);

            if (pubWithAllEntities.drinks != null) {
                List<DrinkEntity> drinkEntities=pubWithAllEntities.drinks
                        .stream()
                        .map(data->data.drink)
                        .distinct()
                        .collect(Collectors.toList());
                insertDrinks(drinkEntities);

                for (DrinkWithAllEntities drinkWithAllEntities : pubWithAllEntities.drinks) {

                    PubDrinkCrossRefEntity crossRef = new PubDrinkCrossRefEntity();
                    crossRef.pubId = pubWithAllEntities.pub.pubId;
                    crossRef.drinkId = drinkWithAllEntities.drink.drinkId;
                    insertPubDrinkCrossRef(crossRef);

                    if ( drinkWithAllEntities.drinkStyles != null) {

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
            }
            if (pubWithAllEntities.photos != null)
                insertPhotos(pubWithAllEntities.photos);

            if (pubWithAllEntities.ratings != null)
                insertRatings(pubWithAllEntities.ratings);

            if (pubWithAllEntities.tags != null)
                insertTags(pubWithAllEntities.tags);

        }
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPub(PubEntity pubs);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDrinks(List<DrinkEntity> drinks);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertDrinkStylesCrossRef(DrinkStyleDrinkCrossRefEntity refDrinkStyles);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertPubDrinkCrossRef(PubDrinkCrossRefEntity refEntity);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPhotos(List<PhotoEntity> photos);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertRatings(RatingsEntity ratings);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDrinkStyles(List<DrinkStyleEntity> drinkStyles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertTags(List<TagEntity> tags);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertBeer(BeerEntity beer);
}