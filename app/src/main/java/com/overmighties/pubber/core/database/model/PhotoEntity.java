package com.overmighties.pubber.core.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class PhotoEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    @ColumnInfo(name = "photo_path")
    private String photoPath;
}
