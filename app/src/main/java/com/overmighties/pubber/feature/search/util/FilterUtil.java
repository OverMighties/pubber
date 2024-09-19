package com.overmighties.pubber.feature.search.util;

import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_BOTTOM_RATING;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_DISTANCE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.NONE_PRICE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_UPPER_RATING;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.util.DateTimeConverter;
import com.overmighties.pubber.util.DayOfWeekConverter;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;

public class FilterUtil {


    private final FilterUiState filterUiState;
    private final String city;
    private final Float distance;
    @Getter
    private List<Pub> filteredPubs;

    public FilterUtil(@NonNull FilterUiState filter, @NonNull List<Pub> filteredPubs, Float distance, String city)
    {
        this.filterUiState =filter;
        this.filteredPubs=filteredPubs;
        this.city=city;
        this.distance=distance;
    }
    public FilterUtil filterByAll()
    {
        return ratingFilter()
                .drinksFilter()
                .priceFilter()
                .tagsFilter()
                .cityFilter()
                .isTimeFilter();
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

    public FilterUtil tagsFilter(){
        List<Pub> filteredNow=new ArrayList<>();

        if(filterUiState.getTags() == null||(filterUiState.getTags() != null && filterUiState.getTags().isEmpty()))
            return this;

        for(var pubData:filteredPubs){
            tonext:
            if(pubData.getTags() != null) {
                for (var tagPub : pubData.getTags()) {
                    for (var tagFilter : Objects.requireNonNull(filterUiState.getTags())) {
                        if (tagPub.getName().equals(tagFilter)) {
                            filteredNow.add(pubData);
                            break tonext;
                        }
                    }
                }
            }
        }
        filteredPubs = filteredNow;
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
            if(pubData.getDrinks()!=null){
                for( var drinkPub:pubData.getDrinks()) {
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getBeers())) {
                        if (drinkPub.getName().equals(drinkFilter)) {
                            filteredNow.add(pubData);
                            break tonext;
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getOtherDrinks())) {
                        if (drinkPub.getName().equals(drinkFilter)) {
                            filteredNow.add(pubData);
                            break tonext;
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getStyles())) {
                        for (var drinkType : Objects.requireNonNull(drinkPub.getDrinkStyles())) {
                            if (drinkType.getName().equals(drinkFilter)) {
                                filteredNow.add(pubData);
                                break tonext;
                            }
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getParticularBeers())) {
                        if (drinkFilter.first.equals(drinkPub.getName())) {
                            for (var drinkType : Objects.requireNonNull(drinkPub.getDrinkStyles())) {
                                if (drinkType.getName().equals(drinkFilter.second)) {
                                    filteredNow.add(pubData);
                                    break tonext;
                                }
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
    public FilterUtil isTimeFilter()
    {
        List<Pub> filteredNow=new ArrayList<>();
        Integer dayOfWeekNow=LocalDateTime.now().getDayOfWeek().getValue();

        if((filterUiState.getOpenNow()!=null && filterUiState.getOpenNow())){
            LocalTime timeNow=LocalTime.now();
            for(var pubData: filteredPubs) {
                if(pubData.getOpeningHours() != null) {
                    for (var time: pubData.getOpeningHours()) {
                        if(time.getDayOfWeekConverter().getNumeric().equals(dayOfWeekNow) &&
                                timeNow.isAfter(time.getLocalTimeOpen()) &&
                                time.getLocalTimeClose().isAfter(timeNow) )
                            filteredNow.add(pubData);
                    }
                }
            }
        }
        if(filterUiState.getCustomOpeningHours() != null)
            return this;
        if ( filterUiState.getCustomOpeningHours().getWeekDay() != null) {
            Integer dayIndex = DayOfWeekConverter.getByCamelCase(filterUiState.getCustomOpeningHours().getWeekDay()).getNumeric();
            if(dayIndex == null)
                dayIndex = dayOfWeekNow;

            if(filterUiState.getCustomOpeningHours().getFromTime() == null && filterUiState.getCustomOpeningHours().getToTime() == null){
                for(var pubData: filteredPubs){
                    if(pubData.getOpeningHours() != null) {
                        for (var time : pubData.getOpeningHours()) {
                            if (time.getDayOfWeekConverter().getNumeric().equals(dayIndex) &&
                                    pubData.getOpeningHours().get(dayIndex) != null) {
                                filteredNow.add(pubData);
                                break;
                            }
                        }
                    }
                }
            } else {
                LocalDateTime fromTime = DateTimeConverter.stringToLocalDateTime(filterUiState.getCustomOpeningHours().getFromTime());
                LocalDateTime toTime =  DateTimeConverter.stringToLocalDateTime(filterUiState.getCustomOpeningHours().getToTime());
                for (var pubData : filteredPubs) {
                    tonext:
                    if(pubData.getOpeningHours() != null) {
                        for (var time : pubData.getOpeningHours()) {
                            if (time.getDayOfWeekConverter().getNumeric().equals(dayIndex)) {
                                LocalDateTime timeOpen =  DateTimeConverter.stringToLocalDateTime(time.getTimeOpen());
                                LocalDateTime timeClose = DateTimeConverter.stringToLocalDateTime(time.getTimeClose());
                                if(timeOpen.isAfter(DateTimeConverter.stringToLocalDateTime("00:00"))){
                                    if(fromTime.isAfter(DateTimeConverter.stringToLocalDateTime("00:00"))){
                                        if(fromTime.isAfter(timeOpen) && toTime.isBefore(timeClose))
                                            filteredNow.add(pubData);
                                    }
                                    else{
                                        break tonext;
                                    }
                                }
                                if(fromTime.isAfter(toTime))
                                    toTime = toTime.plusDays(1);
                                if(timeOpen.isAfter(timeClose))
                                    timeClose = timeClose.plusDays(1);
                                if(fromTime.isAfter(timeOpen) && toTime.isBefore(timeClose))
                                    filteredNow.add(pubData);
                                break tonext;
                            }
                        }
                    }
                }
            }
        }
        else{
            return this;
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
