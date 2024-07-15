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
            count += facebookReviewsCount;
            sum+=facebook*facebookReviewsCount;
        }
        if(tripadvisor !=null){
            count += tripadvisorCount;
            sum+=tripadvisor*tripadvisorCount;
        }
        if (google != null){
            count += googleCount;
            sum += google * googleCount;
        }
        if (count == 0){
            return null;
        }

        else{
            sum = sum/count;
            return Math.round(sum*100.f)/100.f;
        }

    }

    public int getRatingsCount()
    {
        int count = 0;
        if (facebookReviewsCount!=null){count += facebookReviewsCount;}
        if (tripadvisorCount!=null){count += tripadvisorCount;}
        if (googleCount!=null){count += googleCount;}
        return count;
    }

}
