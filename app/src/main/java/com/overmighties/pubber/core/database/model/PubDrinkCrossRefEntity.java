package com.overmighties.pubber.core.database.model;

import androidx.annotation.NonNull;
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
        tableName = "pub_drink_cross_ref",
        primaryKeys = {"pub_id", "drink_id"},
        indices = {@Index(value = "pub_id"), @Index(value = "drink_id")}
)
public class PubDrinkCrossRefEntity {

    @Ignore
    public static final Long ID_NONE = 0L;

    @NonNull
    @ColumnInfo(name = "pub_id")
    public Long pubId;

    @NonNull
    @ColumnInfo(name = "drink_id")
    public Long drinkId;
}
