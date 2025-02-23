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
public class PubDto {
    @SerializedName("pubId")
    private Long pubId;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("placeId")
    private String placeId;
    @SerializedName("city")
    private String city;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("websiteUrl")
    private String websiteUrl;
    @SerializedName("iconUrl")
    private String iconUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("reservable")
    private Boolean reservable;
    @SerializedName("takeout")
    private Boolean takeout;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("ratings")
    private RatingsDto ratings;
    @SerializedName("openingHours")
    private List<OpeningHoursDto> openingHours;
    @SerializedName("drinks")
    private List<DrinkDto> drinks;
    @SerializedName("photos")
    private List<PhotoDto> photos;
    @SerializedName("tags")
    private List<TagDto> tags;
}
