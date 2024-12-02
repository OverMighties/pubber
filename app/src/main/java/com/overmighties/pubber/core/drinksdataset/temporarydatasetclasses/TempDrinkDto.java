package com.overmighties.pubber.core.drinksdataset.temporarydatasetclasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TempDrinkDto {
    @SerializedName("drinkId")
    private Long drinkId;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("longDescription")
    private String longDescription;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("photoUrl")
    private String photoUrl;
    @SerializedName("alcoholContent")
    private String alcoholContent;
    @SerializedName("linkToMoreInformation")
    private String linkToMoreInformation;
    @SerializedName("drinkStyles")
    private List<TempDrinkStyleDto> drinkStyles;
    @SerializedName("beer")
    private TempBeerDto beer;
    @SerializedName("cocktail")
    private TempCocktailDto cocktail;
}
