package com.overmighties.pubber.app.util;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
public class PermissionHandler {
    public static final String[] LOCATION_PERMISSIONS ={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };// Example request code
    //Blocking default constructor
    private PermissionHandler(){
        throw new AssertionError();
    }
    public static boolean shouldShowRationaleLocation(@NonNull Activity activity){
        return ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    public static boolean hasLocationPermission(@NonNull Context context) {
        return hasPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) && hasPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private static boolean hasPermission(@NonNull Context context,@NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
