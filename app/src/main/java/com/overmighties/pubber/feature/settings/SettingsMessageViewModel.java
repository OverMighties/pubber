package com.overmighties.pubber.feature.settings;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;

public class SettingsMessageViewModel extends ViewModel {
    public static final ViewModelInitializer<SettingsMessageViewModel> initializer = new ViewModelInitializer<>(
            SettingsMessageViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new SettingsMessageViewModel(savedStateHandle);
            }
    );

    public SettingsMessageViewModel(SavedStateHandle savedStateHandle) {
    }
}