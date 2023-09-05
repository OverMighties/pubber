package com.overmighties.pubber.data;



import com.overmighties.pubber.util.PriceType;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/*

Z wiekszym użyciem Lomboka wygladałoby to  tak
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class FilterData {
    private final float bottomRating;
    private final float upperRating;
    private final float distance;//in kilometers
    private final boolean isOpen;
    private final ArrayList<String> breweries;
    private final ArrayList<String> drinks;
    private final float upPrice;
}
 */
@Data
public class FilterData {
    private final float bottomRating;
    private final float upperRating;
    private final float distance;//in kilometers
    private final boolean isOpen;
    private final List<String> drinks;
    private final PriceType price ;

    public static class Builder {

        private float bottomAverageRating =0.0f;
        private float upperAverageRating=5.0f;
        private float distance=20.0f;//in kilometers


        private boolean isOpen=false;
        private List<String> drinks=new ArrayList<>();
        private PriceType price =PriceType.NONE;

        public Builder(){

        }
        public Builder distance(float distance)
        {
            this.distance=distance;
            return this;
        }
        public Builder bottomAverageRating(float bottomRating) {
            this.bottomAverageRating = bottomRating;
            return this;
        }
        public Builder upperAverageRating(float upperRating) {
            this.upperAverageRating = upperRating;
            return this;
        }
        public Builder isOpen( boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }
        public Builder drinks( List<String> drinks) {
            this.drinks = drinks;
            return this;
        }
        public Builder price( PriceType price) {
            this.price = price;
            return this;
        }
        public FilterData build()
        {
            return new FilterData(this);
        }
    }

    private FilterData(Builder builder)
    {
        bottomRating = builder.bottomAverageRating;
        upperRating = builder.upperAverageRating;
        distance = builder.distance;
        isOpen = builder.isOpen;
        drinks = builder.drinks;
        price= builder.price;
    }
}
