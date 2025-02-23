package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.adapters.ChangeAlcoholListAdapter;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsEditAlcoholsUiState;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsEditTagsUiState;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DetailsEditViewModel extends ViewModel {

    private final MutableLiveData<DetailsEditAlcoholsUiState> alcoholUiState=
            new MutableLiveData<> (new DetailsEditAlcoholsUiState());
    public LiveData<DetailsEditAlcoholsUiState> getAlcoholUiState(){return alcoholUiState;}

    private final MutableLiveData<DetailsEditTagsUiState> tagsUiState=
            new MutableLiveData<> (new DetailsEditTagsUiState());
    public LiveData<DetailsEditTagsUiState> getTagsUiState(){return tagsUiState;}

    @Getter
    @Setter
    private DetailsEditViewModel.listDataType dataType;
    @Getter
    @Setter
    private String choosenBrewery = null;
    @Getter
    @Setter
    private String choosenStyle = null;
    @Getter
    @Setter
    private String choosenDrink = null;

    // [0] beer add recyclerview
    // [1] beer remove recyclerview
    // [2] cocktail add recyclerview
    // [3] cocktail remove recyclerview
    @Getter
    @Setter
    List<ChangeAlcoholListAdapter> adapterList = new ArrayList<>(4);

    public enum listDataType{
        Breweries,
        Styles,
        Drinks
    }

    public static final ViewModelInitializer<DetailsEditViewModel> initializer = new ViewModelInitializer<>(
            DetailsEditViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new DetailsEditViewModel(savedStateHandle);

            }
    );

    public DetailsEditViewModel(SavedStateHandle savedStateHandle){}
}
