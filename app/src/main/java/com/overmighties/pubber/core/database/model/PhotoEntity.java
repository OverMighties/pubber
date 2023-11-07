package com.overmighties.pubber.core.database.model;

import androidx.annotation.NonNull;
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
@Entity(tableName = "photos")
public class PhotoEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey(autoGenerate = true)
    //@NonNull
    public Long id;
    @ColumnInfo(name="pub_id")
    public Long pubId;
    public String title;
    @ColumnInfo(name = "photo_path")
    public String photoPath;
}
