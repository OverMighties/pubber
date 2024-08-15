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
public class RatingsDto {
    @SerializedName("google")
    private Float google;
    @SerializedName("googleCount")
    private Integer googleCount;
    @SerializedName("facebook")
    private Float facebook;
    @SerializedName("facebookCount")
    private Integer facebookCount;
    @SerializedName("tripAdvisor")
    private Float tripadvisor;
    @SerializedName("tripAdvisorCount")
    private Integer tripadvisorCount;
    @SerializedName("untapped")
    private Float untapped ;
    @SerializedName("untappedCount")
    private Integer untappedCount;
    @SerializedName("ourDrinksQuality")
    private Float ourDrinksQuality;
    @SerializedName("ourServiceQuality")
    private Float ourServiceQuality;
    @SerializedName("ourCost")
    private Integer ourCost;
}
