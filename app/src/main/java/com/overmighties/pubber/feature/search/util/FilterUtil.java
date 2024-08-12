package com.overmighties.pubber.feature.search.util;

import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_BOTTOM_RATING;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_DISTANCE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.NONE_PRICE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_UPPER_RATING;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;

public class FilterUtil {


    private final FilterUiState filterUiState;
    private String city;
    private Float distance;
    @Getter
    private List<Pub> filteredPubs;

    public FilterUtil(@NonNull FilterUiState filter, @NonNull List<Pub> filteredPubs, Float distance, String city)
    {
        this.filterUiState =filter;
        this.filteredPubs=filteredPubs;
    }
    public FilterUtil filterByAll()
    {
        return ratingFilter()
                .drinksFilter()
                .priceFilter()
                .cityFilter()
                .isOpenFilter();
    }
    public FilterUtil ratingFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if(filterUiState.getBottomAverageRating()==null || (filterUiState.getBottomAverageRating()==BASE_BOTTOM_RATING && filterUiState.getUpperAverageRating()==BASE_UPPER_RATING)) {
            return this;
        }
        for(var pubData: filteredPubs)
        {
            Ratings ratings=pubData.getRatings();
            Float average = ratings.getAverageRating();
            if (average==null || (filterUiState.getBottomAverageRating() <= average && filterUiState.getUpperAverageRating() >= average)) {
                filteredNow.add(pubData);
            }
        }
        filteredPubs=filteredNow;
        return this;
    }
    public FilterUtil distanceFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if(filterUiState.getDistance() == BASE_DISTANCE) {
            return this;
        }
        for(var pubData: filteredPubs) {
            if (filterUiState.getDistance() >=distance) {
                filteredNow.add(pubData);
            }
        }
        filteredPubs=filteredNow;
        return this;
    }

    public FilterUtil drinksFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if(filterUiState.getBeers()!= null && filterUiState.getBeers().isEmpty()
                && filterUiState.getOtherDrinks()!= null && filterUiState.getOtherDrinks().isEmpty() ||
                (filterUiState.getBeers()== null && filterUiState.getOtherDrinks()== null)) {
            return this;
        }
        for(var pubData: filteredPubs) {
            tonext:
            for( var drinkPub:pubData.getDrinks()) {
                for( var drinkFilter: Objects.requireNonNull(filterUiState.getBeers())) {
                    if (drinkPub.getName().equals(drinkFilter) ) {
                        filteredNow.add(pubData);
                        break tonext;
                    }
                }
                for( var drinkFilter: Objects.requireNonNull(filterUiState.getOtherDrinks())) {
                    if (drinkPub.getName().equals(drinkFilter) ) {
                        filteredNow.add(pubData);
                        break tonext;
                    }
                }
                for(var drinkFilter: Objects.requireNonNull(filterUiState.getStyles())){
                    for(var drinkType: Objects.requireNonNull(drinkPub.getStyles())){
                        if(drinkType.getName().equals(drinkFilter)){
                            filteredNow.add(pubData);
                            break tonext;
                        }
                    }
                }
                for(var drinkFilter: Objects.requireNonNull(filterUiState.getParticular_beers())){
                    if(drinkFilter.first.equals(drinkPub.getName())){
                        for(var drinkType:Objects.requireNonNull(drinkPub.getStyles())){
                            if(drinkType.getName().equals(drinkFilter.second)){
                                filteredNow.add(pubData);
                                break tonext;
                            }
                        }
                    }
                }
            }

        }
        filteredPubs=filteredNow;
        return this;
    }
    public FilterUtil priceFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if(filterUiState.getPriceType()==null || filterUiState.getPriceType().getIcon().equals(NONE_PRICE)) {
            return this;
        }
        for(var pubData: filteredPubs) {
            if (filterUiState.getPriceType().getId()
                    .equals(pubData.getRatings().getOurCost())) {
                filteredNow.add(pubData);
            }
        }
        filteredPubs=filteredNow;
        return this;
    }
    public FilterUtil isOpenFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if( filterUiState.getOpenNow()==null || !filterUiState.getOpenNow() ) {
            return this;
        }
        Integer dayOfWeekNow=LocalDateTime.now(). getDayOfWeek().getValue();
        LocalTime timeNow=LocalTime.now();
        for(var pubData: filteredPubs) {
            for (var time: pubData.getOpeningHours()) {
                if(time.getDayOfWeekConverter().getNumeric().equals(dayOfWeekNow) &&
                        timeNow.isAfter(time.getLocalTimeOpen()) &&
                       time.getLocalTimeClose().isAfter(timeNow) )
                    filteredNow.add(pubData);
            }
        }

        filteredPubs=filteredNow;
        return this;
    }
    public FilterUtil cityFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if( city==null ) {
            return this;
        }
        for(var pubData: filteredPubs) {
            if(pubData.getCity()==null || pubData.getCity().equals(city))
            {
                filteredNow.add(pubData);
            }
        }

        filteredPubs=filteredNow;
        return this;
    }

}
