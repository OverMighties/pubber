package com.overmighties.pubber.core.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    private Float google;
    private Integer googleCount;
    private Float facebook;
    private Integer facebookReviewsCount;
    private Float tripadvisor;
    private Integer tripadvisorCount;
    private Float untappd;
    private Integer untappdCount;
    private Float ourDrinksQuality;
    private Float ourServiceQuality;
    private Integer ourCost;
    /*
       private Integer facebookLikes;
       private Integer instagramFollowers;
     */
    public Float getAverageRating()
    {
        int count=0;
        float sum=0.f;
        if(facebook!=null){
            count++;
            sum+=facebook;
        }
        if(tripadvisor !=null){
            count++;
            sum+=google;
        }
        if(untappd!=null){
            count++;
            sum+=untappd;
        }
        if(ourDrinksQuality!=null){
            count++;
            sum+=ourDrinksQuality;
        }
        if(ourServiceQuality!=null){
            count++;
            sum+=ourServiceQuality;
        }
        return count==0?null:Math.round(sum*100.f)/100.f;

    }
}
