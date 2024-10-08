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
    private List<String> names = new ArrayList<>();
    private List<String> short_des = new ArrayList<>();
    private List<String> long_des = new ArrayList<>();
    private List<String> photo_path = new ArrayList<>();
}
