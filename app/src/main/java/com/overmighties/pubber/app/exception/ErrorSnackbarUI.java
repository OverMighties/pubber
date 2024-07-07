package com.overmighties.pubber.app.exception;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.UIText;

public class ErrorSnackbarUI {


    public static final String TAG="ErrorSnackbarUI";
    public static final int NONE_RES=-1;
    public enum ErrorTypes {
        FIREBASE_AUTH,
        GOOGLE_SIGN_IN,
        NO_INTERNET_CONNECTION,
        NO_NAVIGATION,
        UNKNOWN_ERROR
    }
    public static void showSnackbar(View view, ErrorTypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        int messageResId = authRes==null?getMessageResId(type):authRes.getResId();
        if(messageResId==NONE_RES)
            Snackbar.make(view,  logMes, Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(view,  view.getContext().getText(messageResId)+logMes, Snackbar.LENGTH_LONG).show();
    }

    @StringRes
    private static int getMessageResId(ErrorTypes type) {
        switch (type) {
            case NO_NAVIGATION:{
                return R.string.auth_error_message;
            }
            case NO_INTERNET_CONNECTION :{
                return R.string.no_internet_connection_message;
            }
            case FIREBASE_AUTH:{
                return R.string.auth_error_message;
            }
            default: Log.e(TAG,"Unknown SnackbarType: " + type);
        }
        return NONE_RES;
    }
}
