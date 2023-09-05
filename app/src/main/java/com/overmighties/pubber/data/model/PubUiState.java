package com.overmighties.pubber.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PubUiState
{
    private Long id;

    private String name;

    private String address;

    private String city;

    private String phoneNumber;

    private String websiteUrl;

    private String iconViewId;
    private String description;
    private Boolean reservable;
    private Boolean takeout;
    private Float distance;
    private RatingsUiState ratings;
    private List<OpeningHoursUiState> openingHours;
    private List<DrinkUiState> drinks;
    private List<PhotoUiState> photos;

}
