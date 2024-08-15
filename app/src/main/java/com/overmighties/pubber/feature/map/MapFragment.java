package com.overmighties.pubber.feature.map;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.basic.BaseFragmentWithPermission;
import com.overmighties.pubber.core.model.LocationData;

public class MapFragment extends BaseFragmentWithPermission implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    protected UserTrackerViewModel userTrackerViewModel;
    protected GoogleMap googleMap;
    protected SupportMapFragment mapFragment;
    public MapFragment(){
        super(R.layout.fragment_map);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userTrackerViewModel = new ViewModelProvider(this).get(UserTrackerViewModel.class);
//        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.MapFragment);
//        mapFragment.getMapAsync(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null, false);
        userTrackerViewModel.getLocationData().observe(getViewLifecycleOwner(), this::onUserLocationChanged);
        // userTrackerViewModel.getLocationData().observe(getViewLifecycleOwner(), this::onUserLocationChanged);
        mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.MapFragment));
        mapFragment.getMapAsync(this);


        return view;
    }

    private void onUserLocationChanged(@NonNull LocationData locationData){
        LatLng location=new LatLng(locationData.getLatitude(),locationData.getLongitude());
        if(googleMap!=null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("You are there"));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap=googleMap;
        if(!checkLocationPermission())
            launchLocationPermissionRequest();

    }
}