package com.overmighties.pubber.core.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(tableName = "pub_drink_cross_ref",primaryKeys = {"pubId", "drinkId"})
public class PubDrinkCrossRefEntity {
    @NonNull
    public Long pubId;
    @NonNull
    public Long drinkId;
}
