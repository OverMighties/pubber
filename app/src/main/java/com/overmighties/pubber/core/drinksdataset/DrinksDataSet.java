package com.overmighties.pubber.core.drinksdataset;

import com.overmighties.pubber.core.network.model.BeerDto;
import com.overmighties.pubber.core.network.model.DrinkDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DrinksDataSet {
    private List<DrinkDto> dataSet;

    private final static DrinksDataSet INSTANCE=new DrinksDataSet();
    public DrinksDataSet()
    {
        init();
    }
    public static DrinksDataSet getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        dataSet = new ArrayList<>();
        dataSet.add(DrinkDto.builder()
                .name("test")
                .type("Beer")
                .beer(BeerDto.builder()
                        .beerId(1L)
                        .longDescription("A light and crisp lager with a smooth finish.")
                        .shortDescription("Crisp lager")
                        .photoUrl("/images/lager.jpg")
                        .maltiness("Low")
                        .blg("12")
                        .alcoholContent("5%")
                        .build())
                .build());
    }
}
