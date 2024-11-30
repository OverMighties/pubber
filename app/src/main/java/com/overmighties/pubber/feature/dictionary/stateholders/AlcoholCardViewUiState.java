package com.overmighties.pubber.feature.dictionary.stateholders;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholCardViewUiState {
    private Long drink_id;
    private String name;
    private String short_des;
    private String long_des;
    private String photo_path;
    private List<String> parametrs;
}
