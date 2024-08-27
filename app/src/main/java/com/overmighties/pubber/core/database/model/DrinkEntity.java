package com.overmighties.pubber.core.database.model;

import androidx.core.util.Pair;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "drinks")
public class DrinkEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="drink_id")
   // @NonNull
    public Long drinkId;
    public String name;
    public String type;
    public String description;
}
