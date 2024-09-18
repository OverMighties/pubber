package com.overmighties.pubber.feature.search.stateholders;

import androidx.core.util.Pair;

import com.overmighties.pubber.feature.search.util.PriceType;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class FilterUiState {

    public static final float BASE_BOTTOM_RATING=0.0f;
    public static final float BASE_UPPER_RATING=5.0f;
    public static final float NONE_DISTANCE=-1.0f;
    public static final String NONE_PRICE ="-$";
    public static final Boolean NONE_OPEN_NOW =null;
    public static final CustomOpeningHours NONE_CUSTOM_OPENING_HOURS=null;
    public static final List<String> NONE_BEERS=null;//new ArrayList<>();
    public static final List<String> NONE_STYLES=null;
    public static final List<Pair<String, String>> NONE_PARTICULAR_BEERS=null;
    public static final List<String> NONE_DRINKS=null;//new ArrayList<>();
    public static final List<String> NONE_TAGS=null;//new ArrayList<>();
    private Float upperAverageRating;
    private Float bottomAverageRating;
    private Float distance;
    private Boolean openNow;
    private CustomOpeningHours customOpeningHours;
    private PriceType priceType;
    private List<String> beers;
    private List<String> styles;
    private List<Pair<String, String>> particularBeers;
    private List<String> otherDrinks;
    private List<String> tags;
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CustomOpeningHours
    {
        String weekDay;
        String fromTime;
        String toTime;
    }
    public FilterUiState()
    {
        this.upperAverageRating=BASE_UPPER_RATING;
        this.bottomAverageRating=BASE_BOTTOM_RATING;
        this.distance=NONE_DISTANCE;
        this.openNow=NONE_OPEN_NOW;
        this.customOpeningHours=NONE_CUSTOM_OPENING_HOURS;
        this.priceType=PriceType.NONE;
        this.beers=NONE_BEERS;
        this.styles = NONE_STYLES;
        this.particularBeers = NONE_PARTICULAR_BEERS;
        this.otherDrinks=NONE_DRINKS;
        this.tags = NONE_TAGS;
    }

}
