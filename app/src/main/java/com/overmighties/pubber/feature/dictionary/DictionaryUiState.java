package com.overmighties.pubber.feature.dictionary;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryUiState {
    private List<AlcoholCardViewUiState> alcoholDataList;
}
