package com.overmighties.pubber.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.overmighties.pubber.core.database.model.DrinkEntity;
import com.overmighties.pubber.core.database.model.DrinkStyleCrossRefEntity;
import com.overmighties.pubber.core.database.model.OpeningHoursEntity;
import com.overmighties.pubber.core.database.model.PhotoEntity;
import com.overmighties.pubber.core.database.model.PubDrinkCrossRefEntity;
import com.overmighties.pubber.core.database.model.PubEntity;
import com.overmighties.pubber.core.database.model.RatingsEntity;
import com.overmighties.pubber.core.database.model.DrinkStyleEntity;

@Database(entities = {PubEntity.class, DrinkEntity.class, DrinkStyleEntity.class, PubDrinkCrossRefEntity.class, DrinkStyleCrossRefEntity.class, OpeningHoursEntity.class, PhotoEntity.class, RatingsEntity.class},version=1,exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract PubsDao pubsDao();
}
