package com.overmighties.pubber.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.overmighties.pubber.core.database.entities.DrinkEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleCrossRefEntity;
import com.overmighties.pubber.core.database.entities.OpeningHoursEntity;
import com.overmighties.pubber.core.database.entities.PhotoEntity;
import com.overmighties.pubber.core.database.entities.PubDrinkCrossRefEntity;
import com.overmighties.pubber.core.database.entities.PubEntity;
import com.overmighties.pubber.core.database.entities.RatingsEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleEntity;

@Database(entities = {PubEntity.class, DrinkEntity.class, DrinkStyleEntity.class, PubDrinkCrossRefEntity.class, DrinkStyleCrossRefEntity.class, OpeningHoursEntity.class, PhotoEntity.class, RatingsEntity.class},version=1,exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract PubsDao pubsDao();
}
