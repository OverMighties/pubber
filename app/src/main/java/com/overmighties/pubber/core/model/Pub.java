package com.overmighties.pubber.core.model;


import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Pub {

    private Long id;
    private String name;

    private String address;
    private LocalDateTime fetchTime;
    private String city;

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
}
