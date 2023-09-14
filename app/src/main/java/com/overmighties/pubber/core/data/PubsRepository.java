package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.database.DbResponse;
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
import com.overmighties.pubber.sync.Result;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PubsRepository {
    public static final String TAG="PubsRepository";
    private PubberNetworkDataSource remoteDataSource;
    private PubberLocalDataSource localDataSource;


    public Result<DbResponse> sync() throws RuntimeException
    {

        try {
            LocalDateTime localDateTime=LocalDateTime.now();
            localDataSource.updatePubs(remoteDataSource.getPubs()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(list ->
                    {
                        List<Pub> pubList = new ArrayList<>();
                        for (var el : list) {
                            pubList.add(mapFromDto(el, localDateTime));
                        }
                        return pubList;
                    }));
        }catch(Exception exception)
        {
            return  new Result.Error<>(exception, exception.getLocalizedMessage());
        }
        return  new Result.Success<>(new DbResponse("Data synchronized", DbResponse.Status.OK));
    }
    public Single<List<Pub>> getPubs()
    {
        return localDataSource.getPubs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
        return ratingsDto==null?new Ratings():new Ratings(ratingsDto.getGoogle(), ratingsDto.getGoogleCount(), ratingsDto.getFacebook(), ratingsDto.getFacebookCount(),
                ratingsDto.getTripadvisor(), ratingsDto.getTripadvisorCount(), ratingsDto.getUntapped(), ratingsDto.getUntappedCount(),
                ratingsDto.getOurDrinksQuality(), ratingsDto.getOurServiceQuality(), ratingsDto.getOurCost());
    }
    public List<Drink> mapFromDtoDrinks(List<DrinkDto> drinkDtos)
    {
        return drinkDtos==null?new ArrayList<Drink>():drinkDtos.stream().map(drinkDto -> new Drink(drinkDto.getName(),drinkDto.getType())).collect(Collectors.toList());
    }
    public List<OpeningHours> mapFromDtoOpeningHours(List<OpeningHoursDto> openingHoursDtos)
    {
        return openingHoursDtos==null?new ArrayList<OpeningHours>():openingHoursDtos.stream().map(dto -> new OpeningHours(dto.getWeekday(),dto.getTimeOpen(), dto.getTimeClose())).collect(Collectors.toList());
    }
    public List<Photo> mapFromDtoPhotos( List<PhotoDto> photoDtos)
    {
        return photoDtos==null?new ArrayList<Photo>():photoDtos.stream().map(dto ->new Photo(dto.getTitle(), dto.getPhotoUrl())).collect(Collectors.toList());
    }
}
