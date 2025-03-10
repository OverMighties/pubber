package com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsEditTagsUiState {
    private List<DetailsEditTagsCardViewUiState> addTagsListName = new ArrayList<>();
    private List<DetailsEditTagsCardViewUiState> removeTagsListName = new ArrayList<>();
}
