package com.overmighties.pubber.core.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;
import com.overmighties.pubber.core.model.LocationData;

import java.util.function.Consumer;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import lombok.Getter;

@SuppressLint("MissingPermission")
public final class UserLocationDataSource implements UserLocationApi{
    private static final long  LOCATION_REQUEST_INTERVAL = 30000L;
    public static final String TAG = "UserLocationDataSource";
    private final Subject<LocationData> locationSubject = BehaviorSubject.create();
    @Getter
    private Flowable<LocationData> locationFlowable;
    private final LocationRequest locationRequestRealTime=  new LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY,LOCATION_REQUEST_INTERVAL)
            .build();

    private final FusedLocationProviderClient fusedLocationClient;
    private final CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
    public UserLocationDataSource(Context context){
        this.fusedLocationClient= LocationServices.getFusedLocationProviderClient(context);
        locationFlowable=locationSubject.toFlowable(BackpressureStrategy.LATEST)
                .doOnSubscribe((loc)->startLocationUpdates())
                .doOnCancel(this::stopLocationUpdates);
    }

    public void getCurrentLocation(){
        fusedLocationClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,cancellationTokenSource.getToken())
                .addOnSuccessListener((loc)-> locationSubject.onNext(mapToData(loc)));
    }
    public void getCurrentLocation(@NonNull Consumer<LocationData> updateUI) {
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,cancellationTokenSource.getToken())
                .addOnSuccessListener( loc -> {
                    if (loc != null) {
                        locationSubject.onNext(mapToData(loc));
                        Log.i(TAG, "getCurrentLocation: Location was received");
                        updateUI.accept(mapToData(loc));
                    } else {
                        Log.i(TAG, "getCurrentLocation: Location wasn't received");
                        updateUI.accept(null);
                    }
            });
    }

    public void getLastLocation(){
        fusedLocationClient.getLastLocation().addOnSuccessListener(
                (loc)-> locationSubject.onNext(mapToData(loc)));
    }

    private LocationCallback locationCallback(){
        return new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult.getLastLocation()!=null){
                    locationSubject.onNext(mapToData(locationResult.getLastLocation()));
                }
            }

        };
    }
    public void startLocationUpdates(){
        fusedLocationClient.requestLocationUpdates(locationRequestRealTime,locationCallback(), Looper.getMainLooper());
    }
    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback());
    }
    private LocationData mapToData(Location location){
        return new LocationData(location.getTime(),location.getLongitude(),location.getLatitude());
    }

    //    private CancellationToken getCancellationToken(){
//        return new CancellationToken() {
//            @NonNull
//            @Override
//            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
//                Log.e(TAG, "onCanceledRequested: location doesn't received");
//                return null;
//            }
//
//            @Override
//            public boolean isCancellationRequested() {
//                return false;
//            }
//        };
//    }

}
