package com.overmighties.pubber.feature.search.stateholders;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class FilterFragmentUiState {

    private List<Boolean> expandVisibilyStateList = new ArrayList<>(); //in order: rating, distance, price, time, beer, breweries, styles, particularBeers, drinks, tags


    // position in layout
    private Pair<Integer, Integer> popUpTimeIds;
    public FilterFragmentUiState(){
        for(int i=0;i<10;i++){
            expandVisibilyStateList.add(false);
        }
        popUpTimeIds = new Pair<>(-1, -1);
    }
}
