package com.overmighties.pubber.core.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PubWithAllEntities {
    @Embedded
    public PubEntity pub;
    @Relation(
            parentColumn = "pubId",
            entityColumn = "pubId"
    )
    public RatingsEntity ratings;

    @Relation(
            parentColumn = "pubId",
            entityColumn = "pubId"
    )
    public  List<OpeningHoursEntity> openingHours;
    @Relation(
            parentColumn = "pubId",
            entityColumn = "drinkId",
            associateBy = @Junction(PubDrinkCrossRefEntity.class)
    )
    public List<DrinkEntity> drinks;

    @Relation(
            parentColumn = "pubId",
            entityColumn = "pubId"
    )
    public  List<PhotoEntity> photos;

}
