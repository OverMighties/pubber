package com.overmighties.pubber.feature.search.stateholders;

import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PubsCardViewUiState {
    private Boolean isLoading;
    private String message;
    private List<PubItemCardViewUiState> pubItems;
    public PubsCardViewUiState()
    {
        isLoading=true;
        message="No content available";
    }
}
