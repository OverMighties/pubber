package com.overmighties.pubber.core.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "styles")
public class StyleEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @ColumnInfo(name="drink_id")
    // @NonNull
    public Long drinkId;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="style_id")
    public Long styleId;
    public String styleName;
}
