package com.overmighties.pubber.feature.owner;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;

import lombok.Getter;
import lombok.Setter;

public class OwnerViewModel extends ViewModel {


    public static final ViewModelInitializer<OwnerViewModel> initializer = new ViewModelInitializer<>(
            OwnerViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new OwnerViewModel(savedStateHandle);

            }
    );

    public OwnerViewModel(SavedStateHandle savedStateHandle){}
}
