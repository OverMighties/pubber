package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.database.model.DrinkEntity;
import com.overmighties.pubber.core.database.model.OpeningHoursEntity;
import com.overmighties.pubber.core.database.model.PhotoEntity;
import com.overmighties.pubber.core.database.model.PubEntity;
import com.overmighties.pubber.core.database.model.PubWithAllEntities;
import com.overmighties.pubber.core.database.model.RatingsEntity;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.util.DateTimeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PubEntityMapper {
    private PubEntityMapper()
    {}
    public static Pub mapFromEntity(PubWithAllEntities pubWithAllEntities)
    {
        PubEntity pubEntity=pubWithAllEntities.pub;
        return new Pub(pubEntity.getPubId(), pubEntity.getName(),pubEntity.getAddress(), DateTimeConverter.getFromString(pubEntity.getFetchTime()), pubEntity.getCity(), pubEntity.getPhoneNumber(),
                pubEntity.getWebsiteUrl(), pubEntity.getIconPath(), pubEntity.getDescription(), pubEntity.getReservable(), pubEntity.getTakeout(),
                mapFromEntityRatings(pubWithAllEntities.getRatings()),mapFromEntityOpeningHours(pubWithAllEntities.getOpeningHours()),
                mapFromEntityDrinks(pubWithAllEntities.getDrinks()),mapFromEntityPhotos(pubWithAllEntities.getPhotos()));
    }
    public static Ratings mapFromEntityRatings(RatingsEntity ratingsEntity)
    {
        return ratingsEntity==null?new Ratings():new Ratings(ratingsEntity.getGoogle(), ratingsEntity.getGoogleCount(), ratingsEntity.getFacebook(), ratingsEntity.getFacebookCount(),
                ratingsEntity.getTripadvisor(), ratingsEntity.getTripadvisorCount(), ratingsEntity.getUntappd(), ratingsEntity.getUntappdCount(),
                ratingsEntity.getOurDrinksQuality(), ratingsEntity.getOurServiceQuality(), ratingsEntity.getOurCost());
    }
    public static List<Drink> mapFromEntityDrinks(List<DrinkEntity> drinkEntities)
    {
        return drinkEntities==null?null:drinkEntities.stream().map(entity -> new Drink(entity.getName(),entity.getType())).collect(Collectors.toList());
    }
    public static List<OpeningHours> mapFromEntityOpeningHours(List<OpeningHoursEntity> openingHoursEntities)
    {
        return openingHoursEntities==null?null:openingHoursEntities.stream().map(entity -> new OpeningHours(entity.getWeekday(),entity.getTimeOpen(), entity.getTimeClose())).collect(Collectors.toList());
    }
    public static List<Photo> mapFromEntityPhotos(List<PhotoEntity> photoEntities)
    {
        return photoEntities==null?null:photoEntities.stream().map(entity ->new Photo(entity.getTitle(), entity.getPhotoPath())).collect(Collectors.toList());
    }
}
