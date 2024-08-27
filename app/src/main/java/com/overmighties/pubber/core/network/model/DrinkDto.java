package com.overmighties.pubber.core.network.model;

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
public class DrinkDto {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("drinkStyles")
    private List<DrinkStyleDto> drinkStyles;
}
