package com.overmighties.pubber.util;


import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;

public class SortUtil
{
    public static void sortingPubData(List<PubItemCardViewUiState> pubList, SortPubsBy sortingBy)
    {
        switch(sortingBy)
        {
            case RELEVANCE:
                break;
            case ALPHABETICAL:
                sortByPubNameAlphabetical(pubList);
                break;
            case RATING:
                sortByRatingsDesc(pubList);
                break;
            case DISTANCE:
                sortByDistanceAsc(pubList);
                break;
        }
    }

    public static  void sortByRatingsDesc( @NonNull List<PubItemCardViewUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubItemCardViewUiState::getQualityRating).reversed());
    }
    public static void sortByDistanceAsc(@NonNull List<PubItemCardViewUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubItemCardViewUiState::getWalkDistance).reversed());
    }
    public static void sortByPubNameAlphabetical(@NonNull List<PubItemCardViewUiState> list)
    {
        list.sort(Comparator.comparing(object -> object.getName().toLowerCase()));
    }
}
