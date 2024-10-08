package com.overmighties.pubber.feature.search.util;

import android.util.Pair;

import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortUtilState {
    private Boolean divided = false;
    private List<Pair<PubItemCardViewUiState, PubFiltrationState>> allConditionsPubList = new ArrayList<>();
    private  List<Pair<PubItemCardViewUiState, PubFiltrationState>> otherPubList = new ArrayList<>();
}
