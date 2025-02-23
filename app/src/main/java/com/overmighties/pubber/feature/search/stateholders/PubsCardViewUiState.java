package com.overmighties.pubber.feature.search.stateholders;

import android.util.Pair;

import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.util.PubFiltrationState;

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
