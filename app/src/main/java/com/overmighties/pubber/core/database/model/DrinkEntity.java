package com.overmighties.pubber.core.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "drinks")
public class DrinkEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long drinkId=0L;
    public String name;
    public String type;
}
