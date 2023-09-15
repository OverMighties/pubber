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
import com.overmighties.pubber.util.DateConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PubDataMapper {
    private PubDataMapper()
    {}
    public static PubWithAllEntities mapToEntity(Pub pub)
    {
        PubEntity pubEntity=new PubEntity(pub.getId(),pub.getName(),pub.getAddress(), DateConverter.getToString(pub.getFetchTime()), pub.getCity(), pub.getPhoneNumber(),
                pub.getWebsiteUrl(), pub.getIconPath(), pub.getDescription(), pub.getReservable(), pub.getTakeout());
        return new PubWithAllEntities(pubEntity,
                mapToEntityRatings(pub.getRatings(),pub.getId()),mapToEntityOpeningHours(pub.getOpeningHours(),pub.getId()),
                mapToEntityDrinks(pub.getDrinks(),pub.getId()),mapToEntityPhotos(pub.getPhotos(),pub.getId()));
    }
    public static RatingsEntity mapToEntityRatings(Ratings ratings, Long pubId)
    {
        return ratings==null?new RatingsEntity():new RatingsEntity( 0L,pubId,ratings.getGoogle(), ratings.getGoogleCount(), ratings.getFacebook(), ratings.getFacebookReviewsCount(),
                ratings.getTripadvisor(), ratings.getTripadvisorCount(), ratings.getUntappd(), ratings.getUntappdCount(),
                ratings.getOurDrinksQuality(), ratings.getOurServiceQuality(), ratings.getOurCost());
    }
    public static List<DrinkEntity> mapToEntityDrinks(List<Drink> drinks, Long pubId)
    {
        return drinks==null?new ArrayList<DrinkEntity>():drinks.stream().map(data -> new DrinkEntity( 0L,data.getName(),data.getType())).collect(Collectors.toList());
    }
    public static List<OpeningHoursEntity> mapToEntityOpeningHours(List<OpeningHours> openingHours, Long pubId)
    {
        return openingHours==null?new ArrayList<OpeningHoursEntity>():openingHours.stream().map(data -> new OpeningHoursEntity( 0L,pubId,data.getWeekday(),data.getTimeOpen(), data.getTimeClose())).collect(Collectors.toList());
    }
    public static List<PhotoEntity> mapToEntityPhotos(List<Photo> photos, Long pubId)
    {
        return photos==null?new ArrayList<PhotoEntity>():photos.stream().map(data ->new PhotoEntity( 0L,pubId,data.getTitle(), data.getPhotoPath())).collect(Collectors.toList());
    }
}
