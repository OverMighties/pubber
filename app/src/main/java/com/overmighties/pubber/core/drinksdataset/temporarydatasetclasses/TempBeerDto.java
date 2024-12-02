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
public class TempBeerDto {
    @SerializedName("beerId")
    private Long beerId;
    @SerializedName("hopiness")
    private String hopiness;
    @SerializedName("maltiness")
    private String maltiness;
    @SerializedName("blg")
    private String blg;
}
