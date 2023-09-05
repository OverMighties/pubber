package com.overmighties.pubber.core.database.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PubAndRatings {
    @Embedded
    public PubEntity pub;
    @Relation(
            parentColumn = "id",
            entityColumn = "pubId"
    )
    public RatingsEntity ratings;

}
