package com.overmighties.pubber.feature.alcohol;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;

public class AlcoholViewModel extends ViewModel {


    private final MutableLiveData<AlcoholUiState> uiState=
            new MutableLiveData<> (new AlcoholUiState());
    public LiveData<AlcoholUiState> getUiState(){
        return uiState;
    }


    public static final ViewModelInitializer<AlcoholViewModel> initializer=new ViewModelInitializer<>(
            AlcoholViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new AlcoholViewModel(savedStateHandle);
            }
    );

    public AlcoholViewModel(SavedStateHandle savedStateHandle){}
}
