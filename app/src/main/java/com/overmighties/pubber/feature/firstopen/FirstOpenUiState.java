package com.overmighties.pubber.feature.firstopen;

import com.overmighties.pubber.app.settings.SettingsHandler;

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
