package com.overmighties.pubber.feature.search.stateholders;

import android.util.Pair;

import com.overmighties.pubber.core.model.Drink;

import java.util.List;

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
    private boolean isOpenNow;
    private Float walkDistance;
    private String costRating;
    private Float qualityRating;
    private int ratingCount;
    private String address;
    private List<Drink> alcohol;
    private Boolean isBreakThrough;
    private Pair<Integer, Integer> compatibility;
    private boolean isFavourite;
    public boolean getIsOpenNow() {
        return isOpenNow;
    }
}
