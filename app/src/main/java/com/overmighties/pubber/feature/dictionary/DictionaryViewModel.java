package com.overmighties.pubber.feature.dictionary;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.feature.dictionary.stateholders.DictionaryUiState;


public class DictionaryViewModel extends ViewModel {

    private final MutableLiveData<DictionaryUiState> UiState=
            new MutableLiveData<> (new DictionaryUiState());
    public LiveData<DictionaryUiState> UiState(){return UiState;}

    public static final ViewModelInitializer<DictionaryViewModel> initializer = new ViewModelInitializer<>(
            DictionaryViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new DictionaryViewModel(savedStateHandle);

            }
    );

    public DictionaryViewModel(SavedStateHandle savedStateHandle){}
}
