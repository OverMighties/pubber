package com.overmighties.pubber.feature.search.filterselect;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;

import lombok.Getter;
import lombok.Setter;

public class FilterSelectViewModel extends ViewModel {

    @Getter
    @Setter
    private listDataType dataType;

    public enum listDataType{
        Breweries,
        Styles
    }


    public static final ViewModelInitializer<FilterSelectViewModel> initializer = new ViewModelInitializer<>(
            FilterSelectViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new FilterSelectViewModel(savedStateHandle);

            }
    );

    public FilterSelectViewModel(SavedStateHandle savedStateHandle){}


}
