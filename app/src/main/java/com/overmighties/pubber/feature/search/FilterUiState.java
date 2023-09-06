package com.overmighties.pubber.feature.search;

import com.overmighties.pubber.util.PriceType;
import com.overmighties.pubber.util.SortPubsBy;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterUiState {
    private Float upperAverageRating;
    private Float bottomAverageRating;
    private Float currentDistance;
    private Boolean openNow;
    private CustomOpeningHours customOpeningHours;
    private Boolean anyHour;
    private PriceType priceType;
    private List<String> beers;
    private List<String> otherDrinks;

    public static class CustomOpeningHours
    {

    }

}
