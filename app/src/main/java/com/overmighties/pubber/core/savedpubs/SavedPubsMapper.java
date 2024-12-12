package com.overmighties.pubber.core.savedpubs;

import com.overmighties.pubber.app.core.savedpubs.BeerProto;
import com.overmighties.pubber.app.core.savedpubs.DrinkProto;
import com.overmighties.pubber.app.core.savedpubs.DrinkStyleProto;
import com.overmighties.pubber.app.core.savedpubs.OpeningHoursProto;
import com.overmighties.pubber.app.core.savedpubs.PhotoProto;
import com.overmighties.pubber.app.core.savedpubs.PubProto;
import com.overmighties.pubber.app.core.savedpubs.RatingsProto;
import com.overmighties.pubber.app.core.savedpubs.TagProto;
import com.overmighties.pubber.core.model.Beer;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.DrinkStyle;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.model.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class SavedPubsMapper {


    public static Pub MapProtoToPub(PubProto favouritePub){
        return new Pub(
                favouritePub.getPubId(),
                favouritePub.getName(),
                favouritePub.getAddress(),
                LocalDateTime.parse(favouritePub.getFetchTime()),
                favouritePub.getCity(),
                favouritePub.getPhoneNumber(),
                favouritePub.getWebsiteUrl(),
                favouritePub.getIconPath(),
                favouritePub.getDescription(),
                favouritePub.getReservable(),
                favouritePub.getTakeout(),
                favouritePub.getLatitude(),
                favouritePub.getLongitude(),
                new Ratings(
                        favouritePub.getRatings().getGoogle(),
                        favouritePub.getRatings().getGoogleCount(),
                        favouritePub.getRatings().getFacebook(),
                        favouritePub.getRatings().getFacebookReviewsCount(),
                        favouritePub.getRatings().getTripadvisor(),
                        favouritePub.getRatings().getTripadvisorCount(),
                        favouritePub.getRatings().getUntappd(),
                        favouritePub.getRatings().getUntappdCount(),
                        favouritePub.getRatings().getOurDrinksQuality(),
                        favouritePub.getRatings().getOurServiceQuality(),
                        favouritePub.getRatings().getOurCost()
                ),
                getOpeningHours(favouritePub),
                getDrinks(favouritePub),
                getPhotos(favouritePub),
                getTags(favouritePub),
                null,
                null,
                true
        );
    }

    public static PubProto MapPubToProto(Pub pub){
        return PubProto.newBuilder()
                .setPubId(pub.getPubId())
                .setName(pub.getName())
                .setAddress(pub.getAddress() == null ? "" : pub.getAddress())
                .setFetchTime(pub.getFetchTime().toString() == null ? "" : pub.getFetchTime().toString())
                .setCity(pub.getCity() == null ? "" : pub.getCity())
                .setPhoneNumber(pub.getPhoneNumber() == null ? "" : pub.getPhoneNumber())
                .setWebsiteUrl(pub.getWebsiteUrl() == null ? "" : pub.getWebsiteUrl())
                .setIconPath(pub.getIconPath() == null ? "" : pub.getIconPath())
                .setDescription(pub.getDescription() == null ? "" : pub.getDescription())
                .setReservable(pub.getReservable() == null ? false : pub.getReservable())
                .setTakeout(pub.getTakeout() == null ? false : pub.getTakeout())
                .setLatitude(pub.getLatitude() == null ? 0 : pub.getLatitude())
                .setLongitude(pub.getLongitude() == null ? 0 : pub.getLongitude())
                .setRatings(RatingsProto.newBuilder()
                        .setGoogle(pub.getRatings().getGoogle() == null ? 0 : pub.getRatings().getGoogle())
                        .setGoogleCount(pub.getRatings().getGoogleCount() == null ? 0 : pub.getRatings().getGoogleCount())
                        .setFacebook(pub.getRatings().getFacebook() == null ? 0 : pub.getRatings().getFacebook())
                        .setFacebookReviewsCount(pub.getRatings().getFacebookReviewsCount() == null ? 0 : pub.getRatings().getFacebookReviewsCount())
                        .setTripadvisor(pub.getRatings().getTripadvisor() == null ? 0 : pub.getRatings().getTripadvisor())
                        .setTripadvisorCount(pub.getRatings().getTripadvisorCount() == null ? 0 : pub.getRatings().getTripadvisorCount())
                        .setUntappd(pub.getRatings().getUntappd() == null ? 0 : pub.getRatings().getUntappd())
                        .setUntappdCount(pub.getRatings().getUntappdCount() == null ? 0 : pub.getRatings().getUntappdCount())
                        .setOurDrinksQuality(pub.getRatings().getOurDrinksQuality() == null ? 0 : pub.getRatings().getOurDrinksQuality())
                        .setOurServiceQuality(pub.getRatings().getOurServiceQuality() == null ? 0 : pub.getRatings().getOurServiceQuality())
                        .setOurCost(pub.getRatings().getOurCost() == null ? 0 : pub.getRatings().getOurCost())
                        .build())
                .addAllOpeningHours(getOpeningHoursProto(pub))
                .addAllDrinks(getDrinksProto(pub))
                .addAllPhotos(getPhotosProto(pub))
                .addAllTags(getTagsProto(pub))
                .build();
    }

    private static List<OpeningHoursProto> getOpeningHoursProto(Pub pub){
        List<OpeningHoursProto> openingHours = new ArrayList<>();
        if(pub.getOpeningHours() == null)
            return openingHours;
        for(var openingHour:pub.getOpeningHours()){
            openingHours.add(OpeningHoursProto.newBuilder()
                    .setWeekday(openingHour.getWeekday())
                    .setTimeClose(openingHour.getTimeClose())
                    .setTimeOpen(openingHour.getTimeOpen())
                    .build()
            );
        }
        return openingHours;
    }

    private static List<DrinkProto> getDrinksProto(Pub pub){
        List<DrinkProto> drinks = new ArrayList<>();
        if(pub.getDrinks() == null)
            return drinks;
        for(var drink: pub.getDrinks()){
            drinks.add(DrinkProto.newBuilder()
                    .setName(drink.getName()== null ? "" : drink.getName())
                    .setDrinkId(drink.getDrinkId()== null ? -1 : drink.getDrinkId())
                    .setType(drink.getType()== null ? "" : drink.getType())
                    .setDescription(drink.getDescription()== null ? "" : drink.getDescription())
                    .addAllDrinkStyles(getDrinkStylesProto(drink))
                    .setBeer(drink.getBeer() == null ? BeerProto.newBuilder().build():
                            BeerProto.newBuilder()
                            .setBeerId(drink.getBeer().getBeerId()== null ? -1 : drink.getBeer().getBeerId())
                            .setLongDescription(drink.getBeer().getLongDescription()== null ? "" : drink.getBeer().getLongDescription())
                            .setPhotoUrl(drink.getBeer().getPhotoUrl()== null ? "" : drink.getBeer().getPhotoUrl())
                            .setMaltiness(drink.getBeer().getMaltiness()== null ? "" : drink.getBeer().getMaltiness())
                            .setBlg(drink.getBeer().getBlg()== null ? "" : drink.getBeer().getBlg())
                            .setAlcoholContent(drink.getBeer().getAlcoholContent()== null ? "" : drink.getBeer().getAlcoholContent())
                            .build()
                        )
                    .build()
            );
        }
        return drinks;
    }

    private static List<DrinkStyleProto> getDrinkStylesProto(Drink drink){
        List<DrinkStyleProto> styles = new ArrayList<>();
        if(drink.getDrinkStyles() == null)
            return styles;

        for(var style:drink.getDrinkStyles()){
            styles.add(DrinkStyleProto.newBuilder()
                    .setName(style.getName())
                    .build()
            );
        }
        return styles;
    }


    private static List<PhotoProto> getPhotosProto(Pub pub) {
        List<PhotoProto> photos = new ArrayList<>();
        if(pub.getPhotos() == null)
            return photos;
        for(var photo: pub.getPhotos()){
            photos.add(PhotoProto.newBuilder()
                    .setTitle(photo.getTitle()== null ? "" : photo.getTitle())
                    .setPhotoPath(photo.getPhotoPath())
                    .build()
            );
        }
        return photos;
    }

    private static List<TagProto> getTagsProto(Pub pub) {
        List<TagProto> tags = new ArrayList<>();
        if(pub.getTags() == null)
            return tags;
        for(var tag: pub.getTags()){
            tags.add(TagProto.newBuilder()
                    .setName(tag.getName())
                    .build());
        }
        return tags;
    }


    private static List<OpeningHours> getOpeningHours(PubProto pub){
        List<OpeningHours> openingHours = new ArrayList<>();
        for(var openingHour:pub.getOpeningHoursList()){
            openingHours.add(new OpeningHours(
                    openingHour.getWeekday(),
                    openingHour.getTimeOpen(),
                    openingHour.getTimeClose()
            ));
        }
        return openingHours;
    }

    private static List<Drink> getDrinks(PubProto pub){
        List<Drink> drinks = new ArrayList<>();
        for(var drink: pub.getDrinksList()){
            drinks.add(new Drink(
                    drink.getDrinkId(),
                    drink.getName(),
                    drink.getType(),
                    drink.getDescription(),
                    getDrinkStyles(drink),
                    new Beer(
                            drink.getBeer().getBeerId(),
                            drink.getBeer().getLongDescription(),
                            drink.getBeer().getShortDescription(),
                            drink.getBeer().getPhotoUrl(),
                            drink.getBeer().getMaltiness(),
                            drink.getBeer().getBlg(),
                            drink.getBeer().getAlcoholContent()
                    )
            ));
        }
        return drinks;
    }

    private static List<DrinkStyle> getDrinkStyles(DrinkProto drink){
        List<DrinkStyle> styles = new ArrayList<>();
        for(var style:drink.getDrinkStylesList()){
            styles.add(new DrinkStyle(
                    style.getName()
            ));
        }
        return styles;
    }


    private static List<Photo> getPhotos(PubProto favouritePub) {
        List<Photo> photos = new ArrayList<>();
        for(var photo: favouritePub.getPhotosList()){
            photos.add(new Photo(
                    photo.getTitle(),
                    photo.getPhotoPath()
            ));
        }
        return photos;
    }

    private static List<Tag> getTags(PubProto favouritePub) {
        List<Tag> tags = new ArrayList<>();
        for(var tag: favouritePub.getTagsList()){
            tags.add(new Tag(
                    tag.getName()
            ));
        }
        return tags;
    }


}
