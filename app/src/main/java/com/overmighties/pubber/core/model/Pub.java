package com.overmighties.pubber.core.model;


import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pub {
    @NonNull
    private Long pubId;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private LocalDateTime fetchTime;
    private String city;
    private String phoneNumber;
    private String websiteUrl;
    private String iconPath;
    private String description;
    private Boolean reservable;
    private Boolean takeout;
    private Double latitude;
    private Double longitude;
    private Ratings ratings;
    private List<OpeningHours> openingHours;
    private List<Drink> drinks;
    private List<Photo> photos;
    private List<Tag> tags;
    private String timeOpenToday;
    private Float walkingDistance;
    private boolean isFavourite;
}
