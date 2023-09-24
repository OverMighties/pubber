package com.overmighties.pubber.feature.search.stateholders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PubItemCardViewUiState {
    private Long pubId;
    private Boolean bookmarked;
    private String name;
    private String iconUrl;
    private String timeOpenToday;
    private Float carDistance;
    private String costRating;
    private Float qualityRating;
    private Float averageRatingFromServices;

}
