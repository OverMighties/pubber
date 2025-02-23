package com.overmighties.pubber.feature.search.util;

import android.util.Pair;

import com.overmighties.pubber.core.model.Pub;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortUtilState {
    private Boolean divided = false;
    private List<Pair<Pub, PubFiltrationState>> allConditionsPubList = new ArrayList<>();
    private  List<Pair<Pub, PubFiltrationState>> otherPubList = new ArrayList<>();
}
