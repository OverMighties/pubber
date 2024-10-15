package com.overmighties.pubber.feature.search.stateholders;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.databinding.FragmentSearcherBinding;
import com.overmighties.pubber.feature.search.ListPubAdapter;
import com.overmighties.pubber.feature.search.util.FilterUtil;
import com.overmighties.pubber.feature.search.util.PubFiltrationState;
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
    private FragmentSearcherBinding binding;
    private boolean isFirstTime;
    private ListPubAdapter listPubAdapter;
    private SortPubsBy lastSortPubsBy;
    private MutableLiveData<SortPubsBy> sortPubsBy = new MutableLiveData<>();
    private List<Pair<Pub, PubFiltrationState>> Pubs;

    public SearcherUiState(){
        ChipTag = "Normal";
        searchviewClicked = false;
        linkPubId = null;
        binding = null;
        isFirstTime = false;
        listPubAdapter = null;
        lastSortPubsBy = SortPubsBy.RELEVANCE;
        sortPubsBy.setValue(SortPubsBy.RELEVANCE);
    }
}
