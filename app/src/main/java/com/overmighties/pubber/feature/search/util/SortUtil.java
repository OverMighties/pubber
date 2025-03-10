package com.overmighties.pubber.feature.search.util;



import android.util.Pair;

import com.overmighties.pubber.core.model.Pub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

public class SortUtil
{
    private static SortUtilState sortUtilState = new SortUtilState();

    public static List<Pair<Pub, PubFiltrationState>> sortingPubData(List<Pair<Pub, PubFiltrationState>> pubList, SortPubsBy sortingBy)
    {
        if(pubList.stream().allMatch(pair->pair.second.getAllCompatibility()!=0 && pair.second.getAllCompatibility() != -1))
            divideList(pubList);
        if(sortingBy == SortPubsBy.RELEVANCE){
            if(sortUtilState.getDivided() && !sortUtilState.getOtherPubList().isEmpty()) {
                prepareFiltrationPriorityLists();
                pubList.clear();
            }
            if(!sortUtilState.getOtherPubList().isEmpty())
                pubList.addAll(sortUtilState.getOtherPubList());
            if(!sortUtilState.getAllConditionsPubList().isEmpty())
                pubList.addAll(0, sortUtilState.getAllConditionsPubList());

        } else {
            List<Pair<Pub, PubFiltrationState>> newData = new ArrayList<>();
            if(sortUtilState.getDivided()){
                if(!sortUtilState.getOtherPubList().isEmpty()){
                    switch (sortingBy) {
                        case ALPHABETICAL:
                            sortByPubNameAlphabetical(sortUtilState.getOtherPubList());
                            break;
                        case RATING:
                            sortByRatingsDesc(sortUtilState.getOtherPubList());
                            break;
                        case DISTANCE:
                            sortByDistanceAsc(sortUtilState.getOtherPubList());
                            break;
                    }
                    newData.addAll(sortUtilState.getOtherPubList());
                }
                if(!sortUtilState.getAllConditionsPubList().isEmpty()) {
                    switch (sortingBy) {
                        case ALPHABETICAL:
                            sortByPubNameAlphabetical(sortUtilState.getAllConditionsPubList());
                            break;
                        case RATING:
                            sortByRatingsDesc(sortUtilState.getAllConditionsPubList());
                            break;
                        case DISTANCE:
                            sortByDistanceAsc(sortUtilState.getAllConditionsPubList());
                            break;
                    }
                    newData.addAll(0, sortUtilState.getAllConditionsPubList());
                }
                pubList = newData;
            } else {
                switch (sortingBy) {
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

        }
        sortUtilState = new SortUtilState();
        return pubList;
    }

    public static void divideList(List<Pair<Pub, PubFiltrationState>> pubList){
        sortUtilState.setDivided(true);
        for(var pubData:pubList){
            if(pubData.second.getCompatibility().equals(pubData.second.getAllCompatibility())){
                sortUtilState.getAllConditionsPubList().add(pubData);
            } else {
                sortUtilState.getOtherPubList().add(pubData);
            }
        }
    }

    public static void prepareFiltrationPriorityLists(){
        List<Pair<Pair<Pub, PubFiltrationState>, Float>> newData=  new ArrayList<>();
        for(var pubData:sortUtilState.getOtherPubList()) {
            Float priority = 0f;
            if (pubData.second.getDoesTimeFit() != null && pubData.second.getDoesTimeFit())
                priority += 0.5f;
            if (pubData.second.getDoesDistancefit() != null && pubData.second.getDoesDistancefit())
                priority += 0.25f;
            priority += pubData.second.getCompatibility();
            newData.add(new Pair<>(pubData, priority));
        }
        newData.sort(Comparator.comparingDouble(pair->pair.second));
        Collections.reverse(newData);
        sortUtilState.setOtherPubList(newData.stream().map(pair->pair.first).collect(Collectors.toList()));
    }

    public static  void sortByRatingsDesc( @NonNull List<Pair<Pub, PubFiltrationState>> list)
    {
        list.sort(Comparator.comparingDouble(pair->pair.first.getRatings().getAverageRating()));
        Collections.reverse(list);
    }
    public static void sortByDistanceAsc(@NonNull List<Pair<Pub, PubFiltrationState>> list)
    {
        list.sort(Comparator.comparingDouble(pair->pair.first.getWalkingDistance()));
    }
    public static void sortByPubNameAlphabetical(@NonNull List<Pair<Pub, PubFiltrationState>> list)
    {
        list.sort(Comparator.comparing(pair -> pair.first.getName().toLowerCase()));
    }
}
