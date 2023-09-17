package com.overmighties.pubber.core.database.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OpeningHoursEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String weekday;

    @ColumnInfo(name = "time_open")

    private String timeOpen;
    @ColumnInfo(name = "time_close")
    private String timeClose;
}
