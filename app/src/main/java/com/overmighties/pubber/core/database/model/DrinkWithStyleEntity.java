package com.overmighties.pubber.core.database.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkWithStyleEntity {
    @Ignore
    public static final Long ID_NONE =null;
    @Embedded
    public DrinkEntity drink;
    @Relation(
            parentColumn = "drink_id",
            entityColumn = "style_id",
            associateBy = @Junction(DrinkStyleCrossRefEntity.class)
    )
    public List<StyleEntity> styles;
}
