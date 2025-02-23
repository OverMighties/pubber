package com.overmighties.pubber.core.database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(tableName = "pubs"
/*, foreignKeys = {
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
        }
        */
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  PubEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey
    @ColumnInfo(name="pub_id")
   // @NonNull
    public Long pubId;
    public String name;
    public String address;
    public String fetchTime;
    //@Ignore
    //private String placeId;
    public String city;
    @ColumnInfo(name = "phone_number")
    public String phoneNumber;
    @ColumnInfo(name = "website_url")
    public String websiteUrl;
    @ColumnInfo(name = "icon_path")
    public String iconPath;
    public String description;
    public Boolean reservable;
    public Boolean takeout;
    public Double latitude;
    public Double longitude;

}
