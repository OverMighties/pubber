package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.database.model.DrinkEntity;
import com.overmighties.pubber.core.database.model.DrinkWithStyleEntity;
import com.overmighties.pubber.core.database.model.OpeningHoursEntity;
import com.overmighties.pubber.core.database.model.PhotoEntity;
import com.overmighties.pubber.core.database.model.PubEntity;
import com.overmighties.pubber.core.database.model.PubWithAllEntities;
import com.overmighties.pubber.core.database.model.RatingsEntity;
import com.overmighties.pubber.core.database.model.DrinkStyleEntity;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.util.DateTimeConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PubDataMapper {
    //Blocking default constructor
    private PubDataMapper(){
        throw new AssertionError();
    }
    public static PubWithAllEntities mapToEntity(Pub pub)
    {
        PubEntity pubEntity=new PubEntity(pub.getId(),pub.getName(),pub.getAddress(), DateTimeConverter.getToString(pub.getFetchTime()), pub.getCity(), pub.getPhoneNumber(),
                pub.getWebsiteUrl(), pub.getIconPath(), pub.getDescription(), pub.getReservable(), pub.getTakeout(), pub.getLatitude(), pub.getLongitude());
        return new PubWithAllEntities(pubEntity,
                mapToEntityRatings(pub.getRatings(),pub.getId()),mapToEntityOpeningHours(pub.getOpeningHours(),pub.getId()),
                mapToEntityDrinks(pub.getDrinks(),pub.getId()),mapToEntityPhotos(pub.getPhotos(),pub.getId()));
    }
    public static RatingsEntity mapToEntityRatings(Ratings ratings, Long pubId)
    {
        return ratings==null?new RatingsEntity():new RatingsEntity(  RatingsEntity.ID_NONE,pubId,ratings.getGoogle(), ratings.getGoogleCount(), ratings.getFacebook(), ratings.getFacebookReviewsCount(),
                ratings.getTripadvisor(), ratings.getTripadvisorCount(), ratings.getUntappd(), ratings.getUntappdCount(),
                ratings.getOurDrinksQuality(), ratings.getOurServiceQuality(), ratings.getOurCost());
    }
    public static List<DrinkWithStyleEntity> mapToEntityDrinks(List<Drink> drinks, Long pubId)
    {
        return drinks==null?null:drinks.stream().map(data -> new DrinkWithStyleEntity(new DrinkEntity(DrinkEntity.ID_NONE,data.getName(),data.getType(),data.getDescription()), data.getDrinkStyles()==null?null:
                data.getDrinkStyles().stream()
                        .map(style->
                                new DrinkStyleEntity(DrinkEntity.ID_NONE, DrinkStyleEntity.ID_NONE, style.getName()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
    public static List<OpeningHoursEntity> mapToEntityOpeningHours(List<OpeningHours> openingHours, Long pubId)
    {
        return openingHours==null?null:openingHours.stream().map(data -> new OpeningHoursEntity( OpeningHoursEntity.ID_NONE,pubId,data.getWeekday(),data.getTimeOpen(), data.getTimeClose())).collect(Collectors.toList());
    }
    public static List<PhotoEntity> mapToEntityPhotos(List<Photo> photos, Long pubId)
    {
        return photos==null?null:photos.stream().map(data ->new PhotoEntity( PhotoEntity.ID_NONE,pubId,data.getTitle(), data.getPhotoPath())).collect(Collectors.toList());
    }
}
