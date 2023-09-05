package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.database.PubberLocalDataSource;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.network.PubberNetworkDataSource;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.core.network.model.PhotoDto;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.network.model.RatingsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PubsRepository {
    private PubberNetworkDataSource remoteDataSource;
    private PubberLocalDataSource localDataSource;

    public Single<List<Pub>> getPubs()
    {
        if(localDataSource.getPubs()==null)
        {
            LocalDateTime localDateTime=LocalDateTime.now();
            localDataSource.updatePubs( remoteDataSource.getPubs().map(list->
            {
                List<Pub> pubList=new ArrayList<>();
                for(var el: list)
                {
                    pubList.add(mapFromDto(el,localDateTime));
                }
                return pubList;
            })
            );
        }
        return localDataSource.getPubs();
    }

    public Pub mapFromDto(PubDto pub, LocalDateTime time)
    {
        return new Pub(pub.getId(), time,pub.getName(),pub.getAddress(), pub.getCity(), pub.getPhoneNumber(),
                pub.getWebsiteUrl(), pub.getIconUrl(), pub.getDescription(), pub.getReservable(), pub.getTakeout(),
                mapFromDtoRatings(pub.getRatings()),mapFromDtoOpeningHours(pub.getOpeningHours()),
                mapFromDtoDrinks(pub.getDrinks()),mapFromDtoPhotos(pub.getPhotos()));
    }
    public Ratings mapFromDtoRatings(RatingsDto ratingsDto)
    {
        return new Ratings(ratingsDto.getGoogle(), ratingsDto.getGoogleCount(), ratingsDto.getFacebook(), ratingsDto.getFacebookCount(),
                ratingsDto.getTripadvisor(), ratingsDto.getTripadvisorCount(), ratingsDto.getUntapped(), ratingsDto.getUntappedCount(),
                ratingsDto.getOurDrinksQuality(), ratingsDto.getOurServiceQuality(), ratingsDto.getOurCost());
    }
    public List<Drink> mapFromDtoDrinks(List<DrinkDto> drinkDtos)
    {
        return drinkDtos.stream().map(drinkDto -> new Drink(drinkDto.getName(),drinkDto.getType())).collect(Collectors.toList());
    }
    public List<OpeningHours> mapFromDtoOpeningHours(List<OpeningHoursDto> openingHoursDtos)
    {
        return openingHoursDtos.stream().map(dto -> new OpeningHours(dto.getWeekday(),dto.getTimeOpen(), dto.getTimeClose())).collect(Collectors.toList());
    }
    public List<Photo> mapFromDtoPhotos( List<PhotoDto> photoDtos)
    {
        return photoDtos.stream().map(dto ->new Photo(dto.getTitle(), dto.getPhotoUrl())).collect(Collectors.toList());
    }
}
