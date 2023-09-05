package com.overmighties.pubber.feature.search;

import lombok.Data;

@Data
public class PubCardViewUiState{
    private Long pubId;
    private String name;
    private Integer imageViewId;
    private String timeOpenToday;
    private String carDistance;
    private Float costRating;
    private Float qualityRating;
    private Float averageFromServicesRating;

}
