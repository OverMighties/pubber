package com.overmighties.pubber.feature.pubdetails.TabFragments;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TabFragmentsUiState {
    private List <Integer> idsOfBeerChips;
    private List <Integer> idsOfDrinkChips;


    public TabFragmentsUiState(){

    }
}
