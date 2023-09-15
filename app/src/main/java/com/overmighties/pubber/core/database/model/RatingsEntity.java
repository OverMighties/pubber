package com.overmighties.pubber.core.database.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RatingsEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long id= 0L;
    public Long pubId;
    public Float google;
    @ColumnInfo(name="google_count")
    public Integer googleCount;

    public Float facebook;
    @ColumnInfo(name="facebook_count")
    public Integer facebookCount;

    public Float tripadvisor;
    @ColumnInfo(name="tripadvisor_count")
    public Integer tripadvisorCount;
    public Float untappd;
    @ColumnInfo(name="untapped_count")
    public Integer untappdCount;
    @ColumnInfo(name="our_drinks_quality")
    public Float ourDrinksQuality;
    @ColumnInfo(name="our_service_quality")
    public Float ourServiceQuality;
    @ColumnInfo(name="our_cost")
    public Integer ourCost;
}
