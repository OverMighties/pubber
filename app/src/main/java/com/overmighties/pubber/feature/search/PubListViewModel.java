package com.overmighties.pubber.feature.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.data.FilterData;
import com.overmighties.pubber.data.model.PubUiState;
import com.overmighties.pubber.util.SortPubsBy;

import java.util.List;

import lombok.Getter;

public class PubListViewModel extends ViewModel {

    private final PubsRepository pubsRepository;

    private final MutableLiveData<SortPubsBy> sortType=new MutableLiveData<>();

    private final MutableLiveData<List<PubUiState>> originalList=new MutableLiveData<>();

    private final MutableLiveData<List<PubUiState>> sortedList=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<FilterData> filterData=new MutableLiveData<>();

    public static final ViewModelInitializer<PubListViewModel> initializer = new ViewModelInitializer<>(
            PubListViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new PubListViewModel(app.appContainer.pubsRepository,  savedStateHandle);
            }
    );

    public PubListViewModel(PubsRepository pubsRepository, SavedStateHandle savedStateHandle) {
    this.pubsRepository=pubsRepository;
    }

    public void getPubs()
    {

    }
}
