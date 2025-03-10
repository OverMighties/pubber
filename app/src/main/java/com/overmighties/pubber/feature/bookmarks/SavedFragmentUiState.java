package com.overmighties.pubber.feature.bookmarks;

import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedFragmentUiState {
    private PubsCardViewUiState pubItemCardViewUiState;
}
