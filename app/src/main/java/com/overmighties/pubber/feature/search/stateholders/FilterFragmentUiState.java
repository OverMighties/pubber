package com.overmighties.pubber.feature.search.stateholders;

import androidx.core.util.Pair;

import com.overmighties.pubber.app.settings.SettingsHandler;

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
    private String language;
    private String breweryTextview;
    private String styleTextview;

    // position in layout
    private Pair<Integer, Integer> popUpTimeIds;
    public FilterFragmentUiState(){
        this.breweryTextview = null;
        this.styleTextview = null;
        for(int i=0;i<10;i++){
            expandVisibilyStateList.add(false);
        }
        popUpTimeIds = new Pair<>(-1, -1);
    }
}
