package com.overmighties.pubber.core.database.entities;

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
@Entity(tableName = "tags")
public class TagEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @PrimaryKey(autoGenerate = true)
    //@NonNull
    public Long id;
    @ColumnInfo(name="pub_id")
    public Long pubId;
    public String name;
}
