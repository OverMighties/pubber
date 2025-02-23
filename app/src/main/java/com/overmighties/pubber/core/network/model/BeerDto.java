package com.overmighties.pubber.core.network.model;

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
public class BeerDto {
    @SerializedName("beerId")
    private Long beerId;
    @SerializedName("longDescription")
    private String longDescription;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("photoUrl")
    private String photoUrl;
    @SerializedName("maltiness")
    private String maltiness;
    @SerializedName("blg")
    private String blg;
    @SerializedName("alcoholContent")
    private String alcoholContent;
}
