package com.overmighties.pubber.core.data;

import com.overmighties.pubber.core.location.LocationRepository;
import com.overmighties.pubber.core.location.UserLocationDataSource;
import com.overmighties.pubber.core.model.LocationData;

import io.reactivex.rxjava3.core.Flowable;

public class LocationRepositoryImpl implements LocationRepository {

    private final UserLocationDataSource userLocationDataSource;

    public LocationRepositoryImpl(UserLocationDataSource userLocationDataSource) {
        this.userLocationDataSource = userLocationDataSource;
    }

    @Override
    public Flowable<LocationData> getLastUserLocation() {
        userLocationDataSource.getLastLocation();
        return userLocationDataSource.getLocationFlowable();
    }
    @Override
    public Flowable<LocationData> getCurrentUserLocation() {
        userLocationDataSource.getCurrentLocation();
        return userLocationDataSource.getLocationFlowable();
    }

    @Override
    public Flowable<LocationData> getLocationFromPeriodicUpdates() {
        return userLocationDataSource.getLocationFlowable();
    }
}
