package com.overmighties.pubber.core.data.mappers;

import androidx.annotation.Nullable;

import com.overmighties.pubber.core.database.entities.BeerEntity;
import com.overmighties.pubber.core.database.entities.DrinkWithAllEntities;
import com.overmighties.pubber.core.database.entities.OpeningHoursEntity;
import com.overmighties.pubber.core.database.entities.PhotoEntity;
import com.overmighties.pubber.core.database.entities.PubEntity;
import com.overmighties.pubber.core.database.entities.PubWithAllEntities;
import com.overmighties.pubber.core.database.entities.RatingsEntity;
import com.overmighties.pubber.core.database.entities.TagEntity;
import com.overmighties.pubber.core.model.Beer;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.model.DrinkStyle;
import com.overmighties.pubber.core.model.Tag;
import com.overmighties.pubber.util.DateTimeConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PubEntityMapper {
    //Blocking default constructor
    private PubEntityMapper() {
        throw new AssertionError();
    }

    public static Pub mapFromEntity(PubWithAllEntities pubWithAllEntities) {
        PubEntity pubEntity = pubWithAllEntities.pub;
        return new Pub(
                pubEntity.getPubId(),
                pubEntity.getName(),
                pubEntity.getAddress(),
                DateTimeConverter.getFromStringDate(pubEntity.getFetchTime()),
                pubEntity.getCity(),
                pubEntity.getPhoneNumber(),
                pubEntity.getWebsiteUrl(),
                pubEntity.getIconPath(),
                pubEntity.getDescription(),
                pubEntity.getReservable(),
                pubEntity.getTakeout(),
                pubEntity.getLatitude(),
                pubEntity.getLongitude(),
                mapFromEntityRatings(pubWithAllEntities.getRatings()),
                mapFromEntityOpeningHours(pubWithAllEntities.getOpeningHours()),
                mapFromEntityDrinks(pubWithAllEntities.getDrinks()),
                mapFromEntityPhotos(pubWithAllEntities.getPhotos()),
                mapFromEntityTags(pubWithAllEntities.getTags()),
                null,
                null
        );
    }

    public static Ratings mapFromEntityRatings(RatingsEntity ratingsEntity) {
        return ratingsEntity == null ? new Ratings() : new Ratings(ratingsEntity.getGoogle(), ratingsEntity.getGoogleCount(), ratingsEntity.getFacebook(), ratingsEntity.getFacebookCount(),
                ratingsEntity.getTripadvisor(), ratingsEntity.getTripadvisorCount(), ratingsEntity.getUntappd(), ratingsEntity.getUntappdCount(),
                ratingsEntity.getOurDrinksQuality(), ratingsEntity.getOurServiceQuality(), ratingsEntity.getOurCost());
    }

    public static Beer mapFromEntityBeer(@Nullable BeerEntity beerEntity) {
        return beerEntity == null ? null : new Beer(
                beerEntity.getBeerId(),
                beerEntity.getLongDescription(),
                beerEntity.getShortDescription(),
                beerEntity.getPhotoUrl(),
                beerEntity.getMaltiness(),
                beerEntity.getBlg(),
                beerEntity.getAlcoholContent()
        );
    }

    public static List<Drink> mapFromEntityDrinks(List<DrinkWithAllEntities> drinkEntities) {
        return drinkEntities == null ? null : drinkEntities.stream()
                .map(entity -> new Drink(
                        entity.drink.getDrinkId(),
                        entity.drink.getName(),
                        entity.getDrink().getType(),
                        entity.getDrink().getDescription(),
                        entity.getDrinkStyles()
                                .stream().map(style -> new DrinkStyle(style.getStyleName()))
                                .collect(Collectors.toList()),
                        mapFromEntityBeer(entity.getBeer())))
                .collect(Collectors.toList());
    }

    public static List<OpeningHours> mapFromEntityOpeningHours(List<OpeningHoursEntity> openingHoursEntities) {
        return openingHoursEntities == null ? null : openingHoursEntities.stream().map(entity -> new OpeningHours(entity.getWeekday(), entity.getTimeOpen(), entity.getTimeClose())).collect(Collectors.toList());
    }

    public static List<Photo> mapFromEntityPhotos(List<PhotoEntity> photoEntities) {
        return photoEntities == null ? null : photoEntities.stream().map(entity -> new Photo(entity.getTitle(), entity.getPhotoPath())).collect(Collectors.toList());
    }

    public static List<Tag> mapFromEntityTags(List<TagEntity> tagEntities) {
        return tagEntities == null ? null : tagEntities.stream()
                .map(entity -> new Tag(entity.getName()))
                .collect(Collectors.toList());
    }

}
