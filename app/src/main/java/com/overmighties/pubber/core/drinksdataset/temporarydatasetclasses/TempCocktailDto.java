package com.overmighties.pubber.core.drinksdataset.temporarydatasetclasses;

import com.google.gson.annotations.SerializedName;

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
public class TempCocktailDto {
    @SerializedName("cocktailId")
    private Long cocktailId;
    @SerializedName("switness")
    private String switness;
    @SerializedName("booziness")
    private String booziness;
}
