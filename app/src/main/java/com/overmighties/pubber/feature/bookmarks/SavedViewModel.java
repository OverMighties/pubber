package com.overmighties.pubber.feature.bookmarks;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;


public class SavedViewModel extends ViewModel {

    private final MutableLiveData<SavedFragmentUiState> uiState=
            new MutableLiveData<> (new SavedFragmentUiState());
    public LiveData<SavedFragmentUiState> getUiState(){
        return uiState;
    }

    public static final ViewModelInitializer<SavedViewModel> initializer=new ViewModelInitializer<>(
            SavedViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new SavedViewModel(savedStateHandle);
            }
    );
    public SavedViewModel(SavedStateHandle savedStateHandle){}

}
