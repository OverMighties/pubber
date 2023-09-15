package com.overmighties.pubber.core.database.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "opening_hours")
public class OpeningHoursEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long id=0L;
    public Long pubId;
    public String weekday;
    @ColumnInfo(name = "time_open")
    public String timeOpen;
    @ColumnInfo(name = "time_close")
    public String timeClose;
}
