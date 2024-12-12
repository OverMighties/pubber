package com.overmighties.pubber.feature.pubdetails.stateholders;

import android.graphics.Bitmap;

import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Ratings;
import com.overmighties.pubber.core.model.Tag;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PubDetailsUiState {
    private Long id;
    private String city;
    private String name;
    private String address;
    private String phoneNumber;
    private String websiteUrl;
    private String iconPath;
    private String description;
    private Boolean reservable;
    private Boolean takeout;
    private Ratings ratings;
    private List<OpeningHours> openingHours;
    private List<Drink> drinks;
    private List<Photo> photos;
    private Bitmap CurrentScreen;
    private List<Tag> tags;
    private String TimeOpenToday;
    private List <Integer> idsOfBeerChips;
    private List <Integer> idsOfDrinkChips;
    private Float distance;
    private boolean isFavourite;
}
