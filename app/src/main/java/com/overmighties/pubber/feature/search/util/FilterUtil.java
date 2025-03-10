package com.overmighties.pubber.feature.search.util;

import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_BOTTOM_RATING;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_DISTANCE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.NONE_PRICE;
import static com.overmighties.pubber.feature.search.data.FilterConstants.BASE_UPPER_RATING;

import android.util.Pair;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.util.DateConverter;
import com.overmighties.pubber.util.DayOfWeek;


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
    private List<Pair<Pub, PubFiltrationState>> filteredPubs = new ArrayList<>();
    @Getter
    private Integer allConditions = 0;

    public FilterUtil(@NonNull FilterUiState filter, @NonNull List<Pub> filteredPubs, Float distance, String city)
    {
        this.filterUiState =filter;
        for(var pub: filteredPubs){
            this.filteredPubs.add(new Pair<>(pub, new PubFiltrationState(0, null, null)));
        }
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
                .isTimeFilter()
                .hasAnyCondition()
                .setAllConditions();
    }
    public FilterUtil ratingFilter()
    {
        if(filterUiState.getBottomAverageRating()==null || (filterUiState.getBottomAverageRating()==BASE_BOTTOM_RATING && filterUiState.getUpperAverageRating()==BASE_UPPER_RATING)) {
            return this;
        }
        int n = 0;
        for(var pubData: filteredPubs)
        {
            Ratings ratings=pubData.first.getRatings();
            Float average = ratings.getAverageRating();
            if (average==null || (filterUiState.getBottomAverageRating() <= average && filterUiState.getUpperAverageRating() >= average)) {
                conditionPlusOne(n, pubData.first);
            }
            n+=1;
        }
        allConditions+=1;
        return this;
    }
    public FilterUtil distanceFilter()
    {
        if(filterUiState.getDistance() == BASE_DISTANCE) {
            return this;
        }
        for(var pubData: filteredPubs) {
            if (filterUiState.getDistance() >=distance) {
               // filteredNow.add(pubData);
            }
        }
        return this;
    }

    public FilterUtil tagsFilter(){

        if(filterUiState.getTags() == null || filterUiState.getTags().isEmpty())
            return this;

        int n=0;
        for(var pubData:filteredPubs){
            if(pubData.first.getTags() != null) {
                for (var tagPub : pubData.first.getTags()) {
                    for (var tagFilter : Objects.requireNonNull(filterUiState.getTags())) {
                        if (tagPub.getName().equals(tagFilter)) {
                            conditionPlusOne(n, pubData.first);
                        }
                    }
                }
            }
            n+=1;
        }
        if(filterUiState.getTags() != null)
            allConditions += filterUiState.getTags().size();
        return this;
    }

    public FilterUtil drinksFilter()
    {
        if(filterUiState.getBeers()!= null && filterUiState.getBeers().isEmpty()
                && filterUiState.getOtherDrinks()!= null && filterUiState.getOtherDrinks().isEmpty() ||
                (filterUiState.getBeers()== null && filterUiState.getOtherDrinks()== null)) {
            return this;
        }
        int n = 0;
        for(var pubData: filteredPubs) {
            if(pubData.first.getDrinks()!=null){
                for( var drinkPub:pubData.first.getDrinks()) {
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getBeers())) {
                        if (drinkPub.getName().equals(drinkFilter)) {
                            conditionPlusOne(n, pubData.first);
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getOtherDrinks())) {
                        if (drinkPub.getName().equals(drinkFilter)) {
                            conditionPlusOne(n, pubData.first);
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getStyles())) {
                        for (var drinkType : Objects.requireNonNull(drinkPub.getDrinkStyles())) {
                            if (drinkType.getName().equals(drinkFilter)) {
                                conditionPlusOne(n, pubData.first);
                            }
                        }
                    }
                    for (var drinkFilter : Objects.requireNonNull(filterUiState.getParticularBeers())) {
                        if (drinkFilter.first.equals(drinkPub.getName())) {
                            for (var drinkType : Objects.requireNonNull(drinkPub.getDrinkStyles())) {
                                if (drinkType.getName().equals(drinkFilter.second)) {
                                    conditionPlusOne(n, pubData.first);
                                }
                            }
                        }
                    }
                }
            }
            n+=1;
        }
        if(filterUiState.getBeers() != null)
            allConditions += filterUiState.getBeers().size();
        if(filterUiState.getOtherDrinks() != null)
            allConditions += filterUiState.getOtherDrinks().size();
        if(filterUiState.getStyles() != null)
            allConditions += filterUiState.getStyles().size();
        if(filterUiState.getStyles() != null)
            allConditions += filterUiState.getStyles().size();
        return this;
    }
    public FilterUtil priceFilter()
    {
        if(filterUiState.getPriceType()==null || filterUiState.getPriceType().getIcon().equals(NONE_PRICE)) {
            return this;
        }
        int n = 0;
        for(var pubData: filteredPubs) {
            if (filterUiState.getPriceType().getId()
                    .equals(pubData.first.getRatings().getOurCost())) {
                conditionPlusOne(n, pubData.first);
            }
            n += 1;
        }
        allConditions += 1;
        return this;
    }
    public FilterUtil isTimeFilter()
    {
        Integer dayOfWeekNow=LocalDateTime.now().getDayOfWeek().getValue();

        if((filterUiState.getOpenNow()!=null && filterUiState.getOpenNow())){
            LocalTime timeNow=LocalTime.now();
            int n = 0;
            allConditions += 1;
            for(var pubData: filteredPubs) {
                changeDoesTimeFit(n, pubData.first, false);
                if(pubData.first.getOpeningHours() != null) {
                    for (var time: pubData.first.getOpeningHours()) {
                        if(time.getDayOfWeekConverter().getNumeric().equals(dayOfWeekNow) && timeNow.isAfter(time.getLocalTimeOpen()) &&
                                time.getLocalTimeClose().isAfter(timeNow)) {
                            changeDoesTimeFit(n, pubData.first, true);
                        }
                    }
                }
                n+=1;
            }
        }
        if(filterUiState.getCustomOpeningHours() == null)
            return this;
        if ( filterUiState.getCustomOpeningHours().getWeekDay() != null) {
            allConditions += 1;
            Integer dayIndex = DayOfWeek.getByCamelCase(filterUiState.getCustomOpeningHours().getWeekDay()).getNumeric();
            if(dayIndex == null)
                dayIndex = dayOfWeekNow;
            if(filterUiState.getCustomOpeningHours().getFromTime() == null && filterUiState.getCustomOpeningHours().getToTime() == null){
                int n = 0;
                for(var pubData: filteredPubs){
                    changeDoesTimeFit(n, pubData.first, false);
                    if(pubData.first.getOpeningHours() != null) {
                        for (var time : pubData.first.getOpeningHours()) {
                            if (time.getDayOfWeekConverter().getNumeric().equals(dayIndex) &&
                                    pubData.first.getOpeningHours().get(dayIndex-1) != null) {
                                changeDoesTimeFit(n, pubData.first, true);
                                break;
                            }
                        }
                    }
                    n+=1;
                }
            } else {
                LocalDateTime fromTime = DateConverter.stringToLocalDateTime(filterUiState.getCustomOpeningHours().getFromTime());
                LocalDateTime toTime =  DateConverter.stringToLocalDateTime(filterUiState.getCustomOpeningHours().getToTime());
                int n =0;
                for (var pubData : filteredPubs) {
                    changeDoesTimeFit(n, pubData.first, false);
                    if(pubData.first.getOpeningHours() != null) {
                        for (var time : pubData.first.getOpeningHours()) {
                            if (time.getDayOfWeekConverter().getNumeric().equals(dayIndex)) {
                                LocalDateTime timeOpen =  DateConverter.stringToLocalDateTime(time.getTimeOpen());
                                LocalDateTime timeClose = DateConverter.stringToLocalDateTime(time.getTimeClose());
                                if(timeOpen.isAfter(DateConverter.stringToLocalDateTime("00:00"))){
                                    if(fromTime.isAfter(DateConverter.stringToLocalDateTime("00:00"))){
                                        if(fromTime.isAfter(timeOpen) && toTime.isBefore(timeClose))
                                            changeDoesTimeFit(n, pubData.first, true);
                                    }
                                    else{
                                        break;
                                    }
                                }
                                if(fromTime.isAfter(toTime))
                                    toTime = toTime.plusDays(1);
                                if(timeOpen.isAfter(timeClose))
                                    timeClose = timeClose.plusDays(1);
                                if(fromTime.isAfter(timeOpen) && toTime.isBefore(timeClose))
                                    changeDoesTimeFit(n, pubData.first, true);
                                break;
                            }
                        }
                    }
                    n+=1;
                }
            }
        } else{
            return this;
        }

        return this;
    }
    public FilterUtil cityFilter()
    {
        List<Pair<Pub, PubFiltrationState>> filteredNow=new ArrayList<>();
        if( city==null ) {
            return this;
        }
        for(var pubData: filteredPubs) {
            if(pubData.first.getCity()==null || pubData.first.getCity().equals(city))
            {
                filteredNow.add(pubData);
            }
        }

        filteredPubs=filteredNow;
        return this;
    }

    private FilterUtil hasAnyCondition(){
        List<Pair<Pub, PubFiltrationState>> filteredNow=new ArrayList<>();
        for(var pubData: filteredPubs){
            if(pubData.second.getCompatibility() != 0)
                filteredNow.add(pubData);

        }
        filteredPubs = filteredNow;
        return this;
    }

    private void conditionPlusOne(Integer n, Pub pubData){
        filteredPubs.set(n, new Pair<>(pubData,
                new PubFiltrationState(
                        filteredPubs.get(n).second.getCompatibility() + 1,
                            filteredPubs.get(n).second.getDoesTimeFit(),
                            filteredPubs.get(n).second.getDoesDistancefit()
                )));
    }

    private void changeDoesTimeFit(Integer n, Pub pubData, Boolean state){
        filteredPubs.set(n, new Pair<>(pubData,
                new PubFiltrationState(
                        filteredPubs.get(n).second.getCompatibility(),
                        state,
                        filteredPubs.get(n).second.getDoesDistancefit()
                )));
    }

    private void changeDoesDistanceFit(Integer n, Pub pubData, Boolean state){
        filteredPubs.set(n, new Pair<>(
                pubData,
                new PubFiltrationState(
                        filteredPubs.get(n).second.getCompatibility(),
                        filteredPubs.get(n).second.getDoesTimeFit(),
                        state
                )));
    }

    private FilterUtil setAllConditions(){
        for(var pubData: filteredPubs){
            pubData.second.setAllCompatibility(allConditions);
        }
        return this;
    }

}
