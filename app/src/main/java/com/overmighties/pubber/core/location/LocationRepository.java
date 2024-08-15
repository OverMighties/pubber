package com.overmighties.pubber.core.location;

import com.overmighties.pubber.core.model.LocationData;

import io.reactivex.rxjava3.core.Flowable;

public interface LocationRepository {
    Flowable<LocationData> getLastUserLocation();
    Flowable<LocationData> getCurrentUserLocation();
}
