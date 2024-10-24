package com.overmighties.pubber.core.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "beer")
public class BeerEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey
    @ColumnInfo(name="beer_id")
    public Long beerId;
    @ColumnInfo(name = "beer_drink_id")
    public Long beerDrinkId;
    @ColumnInfo(name = "long_description")
    public String longDescription;
    @ColumnInfo(name = "short_description")
    public String shortDescription;
    @ColumnInfo(name = "photo_url")
    public String photoUrl;
    public String maltiness;
    public String blg;
    public String alcoholContent;
}
