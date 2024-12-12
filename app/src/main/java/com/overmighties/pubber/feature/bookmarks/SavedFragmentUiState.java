package com.overmighties.pubber.feature.bookmarks;

import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedFragmentUiState {
    private PubsCardViewUiState pubItemCardViewUiState;
}
