package com.overmighties.pubber.feature.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholCardViewUiState {
    private String name;
    private String short_des;
    private String long_des;
    private String photo_path;
}
