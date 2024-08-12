package com.overmighties.pubber.feature.search.stateholders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticularBeersCardViewUiState {
    private String brewery;
    private String style;
}
