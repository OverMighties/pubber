package com.overmighties.pubber.core.data.mappers;

import androidx.annotation.Nullable;

import com.overmighties.pubber.core.model.Beer;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.model.DrinkStyle;
import com.overmighties.pubber.core.model.Tag;
import com.overmighties.pubber.core.network.model.BeerDto;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.core.network.model.PhotoDto;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.network.model.RatingsDto;
import com.overmighties.pubber.core.network.model.TagDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PubDtoMapper {
    //Blocking default constructor
    private PubDtoMapper() {
        throw new AssertionError();
    }

    public static Pub mapFromDto(PubDto pub, LocalDateTime time) {
        return new Pub(
                pub.getPubId(),
                pub.getName(),
                pub.getAddress(),
                time,
                pub.getCity(),
                pub.getPhoneNumber(),
                pub.getWebsiteUrl(),
                pub.getIconUrl(),
                pub.getDescription(),
                pub.getReservable(),
                pub.getTakeout(),
                pub.getLatitude(),
                pub.getLongitude(),
                mapFromDtoRatings(pub.getRatings()),
                mapFromDtoOpeningHours(pub.getOpeningHours()),
                mapFromDtoDrinks(pub.getDrinks()),
                mapFromDtoPhotos(pub.getPhotos()),
                mapFromDtoTags(pub.getTags()),
                null,
                null
        );
    }

    public static Ratings mapFromDtoRatings(RatingsDto ratingsDto) {
        return ratingsDto == null ? new Ratings() : new Ratings(
                ratingsDto.getGoogle(),
                ratingsDto.getGoogleCount(),
                ratingsDto.getFacebook(),
                ratingsDto.getFacebookCount(),
                ratingsDto.getTripadvisor(),
                ratingsDto.getTripadvisorCount(),
                ratingsDto.getUntapped(),
                ratingsDto.getUntappedCount(),
                ratingsDto.getOurDrinksQuality(),
                ratingsDto.getOurServiceQuality(),
                ratingsDto.getOurCost()
        );
    }

    public static Beer mapFromDtoBeer(@Nullable BeerDto beer) {
        return beer == null ? null : new Beer(
                beer.getBeerId(),
                beer.getLongDescription(),
                beer.getShortDescription(),
                beer.getPhotoUrl(),
                beer.getMaltiness(),
                beer.getBlg(),
                beer.getAlcoholContent()
        );
    }

    public static List<Drink> mapFromDtoDrinks(List<DrinkDto> drinkDtos) {
        return drinkDtos == null || drinkDtos.isEmpty() ? null : drinkDtos.stream()
                .map(drinkDto -> new Drink(
                        drinkDto.getDrinkId(),
                        drinkDto.getName(),
                        drinkDto.getType(),
                        drinkDto.getDescription(),
                        drinkDto.getDrinkStyles() == null ? null : drinkDto.getDrinkStyles()
                                .stream()
                                .map(style -> new DrinkStyle(style.getStyleName()))
                                .collect(Collectors.toList()),
                        mapFromDtoBeer(drinkDto.getBeer())
                ))
                .collect(Collectors.toList());
    }

    public static List<OpeningHours> mapFromDtoOpeningHours(List<OpeningHoursDto> openingHoursDtos) {
        return openingHoursDtos == null || openingHoursDtos.isEmpty() ? null : openingHoursDtos.stream()
                .map(dto -> new OpeningHours(
                                dto.getWeekday(),
                                dto.getTimeOpen(),
                                dto.getTimeClose()
                        )
                )
                .collect(Collectors.toList());
    }

    public static List<Photo> mapFromDtoPhotos(List<PhotoDto> photoDtos) {
        return photoDtos == null || photoDtos.isEmpty() ? null : photoDtos.stream()
                .map(dto -> new Photo(dto.getTitle(), dto.getPhotoUrl()))
                .collect(Collectors.toList());
    }

    public static List<Tag> mapFromDtoTags(List<TagDto> tagDtos) {
        return tagDtos == null || tagDtos.isEmpty() ? null : tagDtos.stream()
                .map(dto -> new Tag(dto.getName()))
                .collect(Collectors.toList());
    }

}
