package com.overmighties.pubber.core.database.model;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "pubs", foreignKeys = {
        @ForeignKey(
                entity = DrinkEntity.class,
                parentColumns = {"school_id"},
                childColumns = {"school_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = OpeningHoursEntity.class,
                parentColumns = {"class_id"},
                childColumns = {"class_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = PhotoEntity.class,
                parentColumns = {"class_id"},
                childColumns = {"class_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class PubEntity {
    @PrimaryKey
    private Long id;

    private String name;

    private String address;

    @Ignore

    private String placeId;

    private String city;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "website_url")
    private String websiteUrl;
    @ColumnInfo(name = "icon_path")
    private String iconPath;

    private String description;

    private Boolean reservable;

    private Boolean takeout;

    @Embedded
    private RatingsEntity ratings;
    @ColumnInfo(name = "opening_hours")
    @Embedded
    private List<OpeningHoursEntity> openingHours;
    @Embedded
    private List<DrinkEntity> drinks;
    @Embedded
    private List<PhotoEntity> photos;
}
