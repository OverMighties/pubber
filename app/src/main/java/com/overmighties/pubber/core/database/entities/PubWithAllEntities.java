package com.overmighties.pubber.core.database.entities;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PubWithAllEntities {
    @Ignore
    public static final Long ID_NONE =null;
    @Embedded
    public PubEntity pub;
    @Relation(
            parentColumn = "pub_id",
            entityColumn = "ratings_pub_id"
    )
    public RatingsEntity ratings;

    @Relation(
            parentColumn = "pub_id",
            entityColumn = "opening_hours_pub_id"
    )
    public  List<OpeningHoursEntity> openingHours;
    @Relation(
            entity = DrinkEntity.class,
            parentColumn = "pub_id",
            entityColumn = "drink_id",
            associateBy = @Junction(PubDrinkCrossRefEntity.class)
    )
    public List<DrinkWithAllEntities> drinks;

    @Relation(
            parentColumn = "pub_id",
            entityColumn = "pub_id"
    )
    public  List<PhotoEntity> photos;
    @Relation(
            parentColumn = "pub_id",
            entityColumn = "pub_id"
    )
    public  List<TagEntity> tags;

}
