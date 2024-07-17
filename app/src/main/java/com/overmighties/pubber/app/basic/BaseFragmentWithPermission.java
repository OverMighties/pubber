package com.overmighties.pubber.app.basic;

import static com.overmighties.pubber.app.util.PermissionHandler.LOCATION_PERMISSIONS;
import static com.overmighties.pubber.app.util.PermissionHandler.hasLocationPermission;
import static com.overmighties.pubber.app.util.PermissionHandler.shouldShowRationaleLocation;


import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import com.overmighties.pubber.R;

public class BaseFragmentWithPermission extends Fragment {
    private Runnable onLocationAvailable;

    protected BaseFragmentWithPermission(@LayoutRes int id){
        super(id);
    }
    protected ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                LOCATION_PERMISSIONS[0], false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            if(onLocationAvailable!=null)
                                onLocationAvailable.run();
                        }else {
                            showRationaleLocationUI();
                        }
                    }
            );
    protected void actionOnLocationAvailable(Runnable action){
        onLocationAvailable=action;
        if (hasLocationPermission(requireContext()))
            action.run();
        else if (shouldShowRationaleLocation(requireActivity()))
            showRationaleLocationUI();
        else
            launchLocationPermissionRequest();
    }
    protected void launchLocationPermissionRequest(){
        locationPermissionRequest.launch(LOCATION_PERMISSIONS);
    }
    private void showRationaleLocationUI(){
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.location_alert_dialog_title)
                .setMessage(R.string.location_alert_dialog_message)
                .setPositiveButton(R.string.location_alert_dialog_positive, (dialog, which) -> {
                    launchLocationPermissionRequest();
                })
                .setNegativeButton(R.string.location_alert_dialog_negative, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }


}
