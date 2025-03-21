package com.overmighties.pubber.app.basic;

import static com.overmighties.pubber.app.util.PermissionHandler.LOCATION_PERMISSIONS;
import static com.overmighties.pubber.app.util.PermissionHandler.hasLocationPermission;
import static com.overmighties.pubber.app.util.PermissionHandler.shouldShowRationaleLocation;


import android.app.AlertDialog;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.overmighties.pubber.R;

public class BaseFragmentWithLocationPermission extends Fragment {
    private Runnable onLocationAvailable;
    public static final String TAG="BaseFragmentWithLocationPermission";

    protected ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                LOCATION_PERMISSIONS[0], false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            Log.i(TAG,"Location granted");
                            if(onLocationAvailable!=null)
                                onLocationAvailable.run();
                        }else {
                            Log.i(TAG,"Location not granted");
                            showRationaleLocationUI();
                        }
                    }
            );
    protected void actionOnLocationAvailable(@Nullable Runnable action){
        onLocationAvailable=action;
        if (hasLocationPermission(requireContext())) {
            Log.i(TAG,"User gave permission");
            if(action!=null)
                action.run();
        }else if (shouldShowRationaleLocation(requireActivity()))
            showRationaleLocationUI();
        else
            launchLocationPermissionRequest();
    }
    protected boolean checkLocationPermission(){
        return hasLocationPermission(requireContext());
    }

    protected void launchLocationPermissionRequest(){
        locationPermissionRequest.launch(LOCATION_PERMISSIONS);
    }
    private void showRationaleLocationUI(){
        Log.i(TAG,"Showing location rationale");
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.location_alert_dialog_title)
                .setMessage(R.string.location_alert_dialog_message)
                .setPositiveButton(R.string.location_alert_dialog_positive, (dialog, which) -> {
                    if (!hasLocationPermission(requireContext()))
                        launchLocationPermissionRequest();
                })
                .setNegativeButton(R.string.location_alert_dialog_negative, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }


}
