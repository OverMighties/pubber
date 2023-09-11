package com.overmighties.pubber.util;

import com.overmighties.pubber.data.model.PubUiState;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;

public class SortUtil
{
    public static List<PubItemCardViewUiState>  sortingPubData(List<PubItemCardViewUiState> pubList, SortPubsBy sortingBy)
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
        return pubList;
    }

    public static  void sortByRatingsDesc( @NonNull List<PubItemCardViewUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubItemCardViewUiState::getAverageRatingFromServices).reversed());
    }
    public static void sortByDistanceAsc(@NonNull List<PubItemCardViewUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubItemCardViewUiState::getCarDistance).reversed());
    }
    public static void sortByPubNameAlphabetical(@NonNull List<PubItemCardViewUiState> list)
    {
     
        Collections.sort(list, new Comparator<PubItemCardViewUiState>() {
            @Override
            public int compare(final PubItemCardViewUiState object1, final PubItemCardViewUiState object2) {
                return (((object1.getName()).toLowerCase())).compareTo((object2.getName().toLowerCase()));
            }
        });
    }
}
