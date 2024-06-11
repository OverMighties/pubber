package com.overmighties.pubber.feature.pubdetails;

import android.graphics.Bitmap;

import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Photo;
import com.overmighties.pubber.core.model.Ratings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PubDetailsUiState {
    private Long id;
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
    private String TimeOpenToday;
}
