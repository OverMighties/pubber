package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.core.network.model.PhotoDto;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.network.model.RatingsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PubDtoMapper {
    public static Pub mapFromDto(PubDto pub, LocalDateTime time)
    {
        return new Pub(pub.getId(), pub.getName(),pub.getAddress(),time, pub.getCity(), pub.getPhoneNumber(),
                pub.getWebsiteUrl(), pub.getIconUrl(), pub.getDescription(), pub.getReservable(), pub.getTakeout(),
                mapFromDtoRatings(pub.getRatings()),mapFromDtoOpeningHours(pub.getOpeningHours()),
                mapFromDtoDrinks(pub.getDrinks()),mapFromDtoPhotos(pub.getPhotos()),null);
    }
    public static Ratings mapFromDtoRatings(RatingsDto ratingsDto)
    {
        return ratingsDto==null?new Ratings():new Ratings(ratingsDto.getGoogle(), ratingsDto.getGoogleCount(), ratingsDto.getFacebook(), ratingsDto.getFacebookCount(),
                ratingsDto.getTripadvisor(), ratingsDto.getTripadvisorCount(), ratingsDto.getUntapped(), ratingsDto.getUntappedCount(),
                ratingsDto.getOurDrinksQuality(), ratingsDto.getOurServiceQuality(), ratingsDto.getOurCost());
    }
    public static List<Drink> mapFromDtoDrinks(List<DrinkDto> drinkDtos)
    {
        return drinkDtos==null || drinkDtos.isEmpty()?null:drinkDtos.stream().map(drinkDto -> new Drink(drinkDto.getName(),drinkDto.getType())).collect(Collectors.toList());
    }
    public static List<OpeningHours> mapFromDtoOpeningHours(List<OpeningHoursDto> openingHoursDtos)
    {
        return openingHoursDtos==null || openingHoursDtos.isEmpty()?null:openingHoursDtos.stream().map(dto -> new OpeningHours(dto.getWeekday(),dto.getTimeOpen(), dto.getTimeClose())).collect(Collectors.toList());
    }
    public static List<Photo> mapFromDtoPhotos(List<PhotoDto> photoDtos)
    {
        return photoDtos==null || photoDtos.isEmpty()?null:photoDtos.stream().map(dto ->new Photo(dto.getTitle(), dto.getPhotoUrl())).collect(Collectors.toList());
    }
}
