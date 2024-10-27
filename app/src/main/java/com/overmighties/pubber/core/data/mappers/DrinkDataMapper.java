package com.overmighties.pubber.core.data.mappers;

import static com.overmighties.pubber.core.data.mappers.PubDataMapper.mapToEntityBeer;

import com.overmighties.pubber.core.database.entities.DrinkEntity;
import com.overmighties.pubber.core.database.entities.DrinkStyleEntity;
import com.overmighties.pubber.core.database.entities.DrinkWithAllEntities;
import com.overmighties.pubber.core.model.Drink;

import java.util.stream.Collectors;

public class DrinkDataMapper {

    private DrinkDataMapper() {throw new AssertionError();}

    public static DrinkWithAllEntities mapEntityToDrink(Drink drink){
        return new DrinkWithAllEntities(
                new DrinkEntity(
                        DrinkEntity.ID_NONE,
                        drink.getName(),
                        drink.getType(),
                        drink.getDescription()
                ),
                drink.getDrinkStyles() == null ? null : drink.getDrinkStyles().stream()
                        .map(style ->
                                new DrinkStyleEntity(DrinkEntity.ID_NONE, DrinkStyleEntity.ID_NONE, style.getName()))
                        .collect(Collectors.toList()),
                mapToEntityBeer(drink.getBeer(), drink.getDrinkId()));
    }
}
