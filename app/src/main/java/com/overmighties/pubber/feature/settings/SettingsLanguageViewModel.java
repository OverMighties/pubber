package com.overmighties.pubber.feature.settings;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;

public class SettingsLanguageViewModel extends ViewModel {

    public static final ViewModelInitializer<SettingsLanguageViewModel> initializer = new ViewModelInitializer<>(
            SettingsLanguageViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new SettingsLanguageViewModel(savedStateHandle);
            }
    );

    public SettingsLanguageViewModel(SavedStateHandle savedStateHandle) {
    }

}