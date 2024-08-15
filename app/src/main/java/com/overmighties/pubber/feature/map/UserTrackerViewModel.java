package com.overmighties.pubber.feature.map;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.location.LocationRepository;
import com.overmighties.pubber.core.model.LocationData;
import com.overmighties.pubber.feature.search.PubListViewModel;

public class UserTrackerViewModel extends ViewModel {

    private final MutableLiveData<LocationData> locationData=new MutableLiveData<>(LocationData.BASE_VAL);
    private final LocationRepository locationRepository;

    @NonNull
    public LiveData<LocationData> getLocationData(){
        return locationData;
    }
    public static final ViewModelInitializer<UserTrackerViewModel> initializer = new ViewModelInitializer<>(
            UserTrackerViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new UserTrackerViewModel(app.appContainer.getLocationRepository(),  savedStateHandle);
            }
    );
    public UserTrackerViewModel(LocationRepository locationRepository, SavedStateHandle savedStateHandle) {
        this.locationRepository=locationRepository;
    }

}
