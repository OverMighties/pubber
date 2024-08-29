package com.overmighties.pubber.feature.map;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.core.location.LocationRepository;
import com.overmighties.pubber.core.model.LocationData;

import io.reactivex.rxjava3.disposables.Disposable;

public class UserTrackerViewModel extends PubberAppViewModel {
    public static final String TAG= "UserTrackerViewModel";

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
    public void startLocationUpdates(){
        Disposable d = locationRepository.getLocationFromPeriodicUpdates()
                .subscribe(value -> {
                    Log.i(TAG,"User location changed to:"+value.toString());
                    locationData.setValue(value);
                }, err-> Log.e(TAG,"Error in receving user location data"));
        disposables.add(d);
    }
}
