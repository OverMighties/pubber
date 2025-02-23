package com.overmighties.pubber.app.exception;


import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.UIText;

import org.jetbrains.annotations.NotNull;

public class ErrorSnackbarUI {


    public static final String TAG="ErrorSnackbarUI";
    public static final int NONE_RES=-1;
    public enum ErrorTypes {
        FIREBASE_AUTH,
        USER_ACCOUNT,
        GOOGLE_SIGN_IN,
        NO_INTERNET_CONNECTION,
        NO_NAVIGATION,
        UNKNOWN_ERROR
    }
    public static void showSnackbar(@NotNull View view,@NotNull ErrorTypes type, @Nullable UIText.ResourceString resMess,@Nullable String logMess) {
        String userInfoMess =getMessForErrorType(type,view.getContext());
        if(resMess!=null)
            userInfoMess+=(String) view.getContext().getText(resMess.getResId());
        if(logMess!=null)
            userInfoMess+=logMess;
        Snackbar.make(view,  userInfoMess, Snackbar.LENGTH_LONG).show();
    }

    private static String getMessForErrorType(ErrorTypes type, Context context) {
        switch (type) {
            case NO_NAVIGATION:{
                return (String) context.getText(R.string.no_navigation_message)+
                        context.getText(R.string.limited_func);
            }
            case USER_ACCOUNT:{
                return  (String) context.getText(R.string.user_account_error_message);
            }
            case NO_INTERNET_CONNECTION :{
                return  (String) context.getText(R.string.no_internet_connection_message)+
                        context.getText(R.string.limited_func);
            }
            case FIREBASE_AUTH:{
                return (String) context.getText(R.string.auth_error_message);
            }
            case UNKNOWN_ERROR: {
                return (String) context.getText(R.string.sth_went_wrong);
            }
            default: {
                Log.e(TAG, "Unknown SnackbarType: " + type);
                return (String) context.getText(R.string.sth_went_wrong);
            }
        }
    }
}
