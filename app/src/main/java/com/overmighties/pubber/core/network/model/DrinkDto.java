package com.overmighties.pubber.core.network.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
}
