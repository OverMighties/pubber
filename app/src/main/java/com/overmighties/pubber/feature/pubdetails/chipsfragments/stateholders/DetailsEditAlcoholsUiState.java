package com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders;

import com.overmighties.pubber.core.model.Drink;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsEditAlcoholsUiState {
    List<Drink> addBeerListName = new ArrayList<>();
    List<Drink> removeBeerListName = new ArrayList<>();
    List<Drink> addDrinkListName = new ArrayList<>();
    List<Drink> removeDrinkListName = new ArrayList<>();
    //[0] beer add layout
    //[1] drink add layout
    List<Boolean> visibilityState = new ArrayList<>() ;

}
