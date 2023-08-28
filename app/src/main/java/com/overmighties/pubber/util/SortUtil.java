package com.overmighties.pubber.util;

import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.data.PubData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;

public class SortUtil
{
    public static void sortUtli(int sorting)
    {
        List<PubData> pubDataList = AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue();
        switch(sorting)
        {
            case 1:
                break;
            case 2:
                sortByPubNameAlphabetical(pubDataList);
                break;
            case 3:
                sortByRatingsDesc(pubDataList);
                break;
            case 4:
                sortByDistanceAsc(pubDataList);
                break;
        }
        AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().setValue(pubDataList);
    }

    public static  void sortByRatingsDesc(List<PubData> list)
    {
        list.sort(Comparator.comparingDouble(PubData::getRatingOwn).reversed());
    }
    public static void sortByDistanceAsc(List<PubData> list)
    {
        list.sort(Comparator.comparingDouble(PubData::getDistance).reversed());
    }
    public static void sortByPubNameAlphabetical(List<PubData> list)
    {
        //poprzednia wersja sortowania stawiała duże litery nad małymi
        Collections.sort(list, new Comparator<PubData>() {
            @Override
            public int compare(final PubData object1, final PubData object2) {
                return (((object1.getName()).toLowerCase())).compareTo((object2.getName().toLowerCase()));
            }
        });
    }
}
