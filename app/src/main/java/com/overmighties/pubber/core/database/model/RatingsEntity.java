package com.overmighties.pubber.core.database.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class RatingsEntity {
    @PrimaryKey(autoGenerate = true)

    private Long id;
    public Long pubId;
    private Float google;
    @ColumnInfo(name="google_count")
    private Integer googleCount;

    private Float facebook;
    @ColumnInfo(name="google_count")
    private Integer facebookCount;

    private Float tripadvisor;
    @ColumnInfo(name="tripadvisor_count")
    private Integer tripadvisorCount;
    private Float untapped ;
    @ColumnInfo(name="untapped_count")
    private Integer untappedCount;
    @ColumnInfo(name="our_drinks_quality")
    private Float ourDrinksQuality;
    @ColumnInfo(name="our_service_quality")
    private Float ourServiceQuality;
    @ColumnInfo(name="our_cost")
    private Integer ourCost;
}
