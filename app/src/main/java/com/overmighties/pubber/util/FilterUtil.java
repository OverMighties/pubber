package com.overmighties.pubber.util;

import static com.overmighties.pubber.data.FilterConstants.BASE_BOTTOM_RATING;
import static com.overmighties.pubber.data.FilterConstants.BASE_DISTANCE;
import static com.overmighties.pubber.data.FilterConstants.NONE_PRICE;
import static com.overmighties.pubber.data.FilterConstants.BASE_UPPER_RATING;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class FilterUtil {


    private FilterUiState filterUiState;
    private Float distance;
    @Getter
    private List<Pub> filteredPubs;

    public FilterUtil(FilterUiState filter, List<Pub> filteredPubs, Float distance)
    {
        this.filterUiState =filter;
        this.filteredPubs=filteredPubs;
    }
    public FilterUtil filterByAll()
    {
        return ratingFilter()
                .drinksFilter()
                .priceFilter()
                .isOpenFilter();
    }
    public FilterUtil ratingFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        if(filterUiState.getBottomAverageRating()==BASE_BOTTOM_RATING && filterUiState.getUpperAverageRating()==BASE_UPPER_RATING) {
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
        if(filterUiState.getDistance()==BASE_DISTANCE || filterUiState.getDistance()==null) {
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
        if(filterUiState.getBeers().size()==0 && filterUiState.getOtherDrinks().size()==0) {
            return this;
        }
        for(var pubData: filteredPubs) {
            tonext:
            for( var drinkPub:pubData.getDrinks()) {
                for( var drinkFilter: filterUiState.getBeers()) {
                    if (drinkPub.equals(drinkFilter) ) {
                        filteredNow.add(pubData);
                        break tonext;
                    }
                }
                for( var drinkFilter: filterUiState.getOtherDrinks()) {
                    if (drinkPub.equals(drinkFilter) ) {
                        filteredNow.add(pubData);
                        break tonext;
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
        if(filterUiState.getPriceType().getIcon().equals(NONE_PRICE)) {
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
        if(!filterUiState.getOpenNow() || filterUiState.getOpenNow()==null) {
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

}
