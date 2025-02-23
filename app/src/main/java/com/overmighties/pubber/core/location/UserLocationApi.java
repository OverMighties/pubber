package com.overmighties.pubber.core.location;

import androidx.annotation.NonNull;

import com.overmighties.pubber.core.model.LocationData;

import java.util.function.Consumer;

public interface UserLocationApi {
    void getCurrentLocation();
    void getCurrentLocation(@NonNull Consumer<LocationData> updateUI);
    void getLastLocation();
    void startLocationUpdates();
    void stopLocationUpdates();
}
