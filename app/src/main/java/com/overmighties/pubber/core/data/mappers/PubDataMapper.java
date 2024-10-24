package com.overmighties.pubber.core.data.mappers;


import androidx.annotation.Nullable;

import com.overmighties.pubber.core.database.entities.BeerEntity;
import com.overmighties.pubber.core.database.entities.DrinkEntity;
import com.overmighties.pubber.core.database.entities.DrinkWithAllEntities;
import com.overmighties.pubber.core.database.entities.OpeningHoursEntity;
import com.overmighties.pubber.core.database.entities.PhotoEntity;
import com.overmighties.pubber.core.database.entities.PubEntity;
import com.overmighties.pubber.core.database.entities.PubWithAllEntities;
import com.overmighties.pubber.core.database.entities.RatingsEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleEntity;
import com.overmighties.pubber.core.database.entities.TagEntity;
import com.overmighties.pubber.core.model.Beer;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.model.Tag;
import com.overmighties.pubber.util.DateTimeConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PubDataMapper {
    //Blocking default constructor
    private PubDataMapper() {
        throw new AssertionError();
    }

    public static PubWithAllEntities mapToEntity(Pub pub) {
        PubEntity pubEntity = new PubEntity(
                pub.getPubId(),
                pub.getName(),
                pub.getAddress(),
                DateTimeConverter.getToStringDate(pub.getFetchTime()),
                pub.getCity(),
                pub.getPhoneNumber(),
                pub.getWebsiteUrl(),
                pub.getIconPath(),
                pub.getDescription(),
                pub.getReservable(),
                pub.getTakeout(),
                pub.getLatitude(),
                pub.getLongitude()
        );
        return new PubWithAllEntities(
                pubEntity,
                mapToEntityRatings(
                        pub.getRatings(),
                        pub.getPubId()
                ),
                mapToEntityOpeningHours(
                        pub.getOpeningHours(),
                        pub.getPubId()
                ),
                mapToEntityDrinks(
                        pub.getDrinks(),
                        pub.getPubId()
                ),
                mapToEntityPhotos(
                        pub.getPhotos(),
                        pub.getPubId()
                ),
                mapToEntityTags(
                        pub.getTags(),
                        pub.getPubId()
                )
        );
    }

    public static RatingsEntity mapToEntityRatings(Ratings ratings, Long pubId) {
        return ratings == null ? new RatingsEntity() :
                new RatingsEntity(
                        RatingsEntity.ID_NONE,
                        pubId,
                        ratings.getGoogle(),
                        ratings.getGoogleCount(),
                        ratings.getFacebook(),
                        ratings.getFacebookReviewsCount(),
                        ratings.getTripadvisor(),
                        ratings.getTripadvisorCount(),
                        ratings.getUntappd(),
                        ratings.getUntappdCount(),
                        ratings.getOurDrinksQuality(),
                        ratings.getOurServiceQuality(),
                        ratings.getOurCost()
                );
    }

    public static BeerEntity mapToEntityBeer(@Nullable Beer beer, Long drinkId) {
        return beer == null ? null : new BeerEntity(
                beer.getBeerId(),
                drinkId,
                beer.getLongDescription(),
                beer.getShortDescription(),
                beer.getPhotoUrl(),
                beer.getMaltiness(),
                beer.getBlg(),
                beer.getAlcoholContent()
        );
    }
    public static List<DrinkWithAllEntities> mapToEntityDrinks(List<Drink> drinks, Long pubId) {
        return drinks == null ? null : drinks.stream()
                .map(data -> new DrinkWithAllEntities(
                                new DrinkEntity(
                                        DrinkEntity.ID_NONE,
                                        data.getName(),
                                        data.getType(),
                                        data.getDescription()
                                ),
                                data.getDrinkStyles() == null ? null : data.getDrinkStyles().stream()
                                        .map(style ->
                                                new DrinkStyleEntity(DrinkEntity.ID_NONE, DrinkStyleEntity.ID_NONE, style.getName()))
                                        .collect(Collectors.toList()),
                                mapToEntityBeer(data.getBeer(), data.getDrinkId())
                        )
                )
                .collect(Collectors.toList());
    }

    public static List<OpeningHoursEntity> mapToEntityOpeningHours(List<OpeningHours> openingHours, Long pubId) {
        return openingHours == null ? null : openingHours.stream()
                .map(data -> new OpeningHoursEntity(
                                OpeningHoursEntity.ID_NONE,
                                pubId,
                                data.getWeekday(),
                                data.getTimeOpen(),
                                data.getTimeClose()
                        )
                )
                .collect(Collectors.toList());
    }

    public static List<PhotoEntity> mapToEntityPhotos(List<Photo> photos, Long pubId) {
        return photos == null ? null : photos.stream()
                .map(data -> new PhotoEntity(
                                PhotoEntity.ID_NONE,
                                pubId, data.getTitle(),
                                data.getPhotoPath()
                        )
                )
                .collect(Collectors.toList());
    }

    public static List<TagEntity> mapToEntityTags(List<Tag> tags, Long pubId) {
        return tags == null ? null : tags.stream()
                .map(data -> new TagEntity(
                        PhotoEntity.ID_NONE,
                        pubId,
                        data.getName()))
                .collect(Collectors.toList());
    }
}
