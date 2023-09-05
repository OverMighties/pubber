package com.overmighties.pubber.core.model;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Ratings {

    private Float google;

    private Integer googleCount;
    private Float facebook;
    private Integer facebookReviewsCount;
    private Float tripAdvisor;
    private Integer tripAdvisorCount;
    private Float untappd;
    private Integer untappdCount;
    private Float ourDrinksQuality;
    private Float ourServiceQuality;
    private Integer ourCost;
    /*
       private Integer facebookLikes;
       private Integer instagramFollowers;
     */
}
