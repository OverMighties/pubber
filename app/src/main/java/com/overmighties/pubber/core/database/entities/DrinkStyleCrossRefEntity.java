package com.overmighties.pubber.core.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(
        tableName = "drink_style_cross_ref",
        primaryKeys = {"drink_id", "drink_style_id"},
        indices = {@Index(value = "drink_id"), @Index(value = "drink_style_id")}
)
public class DrinkStyleCrossRefEntity {
    @Ignore
    public static final Long ID_NONE = 0L;

    @NonNull
    @ColumnInfo(name = "drink_id")
    public Long drinkId;

    @NonNull
    @ColumnInfo(name = "drink_style_id")
    public Long drinkStyleId;
}
