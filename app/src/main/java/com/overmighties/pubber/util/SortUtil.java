package com.overmighties.pubber.util;

import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.data.model.PubUiState;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;

public class SortUtil
{
    public static void sortingPubData(SortPubsBy sortingBy)
    {
        List<PubUiState> pubDataList = AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue();
        switch(sortingBy)
        {
            case RELEVANCE:
                break;
            case ALPHABETICAL:
                sortByPubNameAlphabetical(pubDataList);
                break;
            case RATING:
                sortByRatingsDesc(pubDataList);
                break;
            case DISTANCE:
                sortByDistanceAsc(pubDataList);
                break;
        }
        AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().setValue(pubDataList);
    }

    public static  void sortByRatingsDesc( @NonNull List<PubUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubUiState::getRatingOwn).reversed());
    }
    public static void sortByDistanceAsc(@NonNull List<PubUiState> list)
    {
        list.sort(Comparator.comparingDouble(PubUiState::getDistance).reversed());
    }
    public static void sortByPubNameAlphabetical(@NonNull List<PubUiState> list)
    {
        //poprzednia wersja sortowania stawiała duże litery nad małymi
        Collections.sort(list, new Comparator<PubUiState>() {
            @Override
            public int compare(final PubUiState object1, final PubUiState object2) {
                return (((object1.getName()).toLowerCase())).compareTo((object2.getName().toLowerCase()));
            }
        });
    }
}
