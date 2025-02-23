package com.overmighties.pubber.core.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(
        tableName = "drinks",
        indices = {@Index(value = {"name"}, unique = true)}
)
public class DrinkEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="drink_id")
    public Long drinkId;
    public String name;
    public String type;
    public String description;
}
