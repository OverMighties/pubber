package com.overmighties.pubber.feature.search.stateholders;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.feature.search.util.FilterUtil;
import com.overmighties.pubber.feature.search.util.SortPubsBy;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
public class SearcherUiState {
    private String ChipTag;
    private boolean searchviewClicked;
    private Long linkPubId;
    private MutableLiveData<SortPubsBy> sortPubsBy = new MutableLiveData<>();
    private MutableLiveData<Pair<PubsCardViewUiState, Boolean>> searchedPubs = new MutableLiveData<>();

    public SearcherUiState(){
        this.ChipTag = "Normal";
        this.searchviewClicked = false;
        this.linkPubId = null;
        this.sortPubsBy.setValue(SortPubsBy.RELEVANCE);
    }
}
