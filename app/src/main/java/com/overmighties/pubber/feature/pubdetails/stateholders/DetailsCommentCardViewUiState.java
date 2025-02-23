package com.overmighties.pubber.feature.pubdetails.stateholders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsCommentCardViewUiState {
    private String nickName;
    private String origin;
    private float rating;
    private String priceRange;
    private String content;

}
