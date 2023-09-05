package com.overmighties.pubber.core.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class DrinkEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String name;

    private String type;
}
