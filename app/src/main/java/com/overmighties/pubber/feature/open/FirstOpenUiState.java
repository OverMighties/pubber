package com.overmighties.pubber.feature.open;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstOpenUiState {
    private FirstOpenViewModel.Language language;
    private FirstOpenViewModel.Theme theme;
}
