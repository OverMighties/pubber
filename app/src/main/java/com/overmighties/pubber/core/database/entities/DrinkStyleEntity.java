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
        tableName = "styles",
        indices = {@Index(value = {"style_name"}, unique = true)}
)
public class DrinkStyleEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @ColumnInfo(name="drink_id")
    // @NonNull
    public Long drinkId;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="drink_style_id")
    public Long drinkStyleId;
    @ColumnInfo(name="style_name")
    public String styleName;
}
