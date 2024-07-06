package com.overmighties.pubber.app.exception;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.SnackbarUI;
import com.overmighties.pubber.util.UIText;

public class ErrorHandler {
    public static final String TAG = "ErrorHandler";
    public static final int NONE_RES=-1;

    public enum ErrorTypes {
        FIREBASE_AUTH_ERROR,
        BASIC_AUTH_ERROR,
        NO_INTERNET_CONNECTION,
        MARKED_AS_FAVORITE,
        COMMENT_ADDED
    }

    public static String getErrorMessage(Context context, ErrorHandler.ErrorTypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        int messageResId = authRes==null?getMessageResId(type):authRes.getResId();
        if(type == ErrorHandler.ErrorTypes.BASIC_AUTH_ERROR || type == ErrorHandler.ErrorTypes.FIREBASE_AUTH_ERROR)
            logMes += context.getText(R.string.try_again);
        if(messageResId==NONE_RES)
            return logMes;
        else
            return logMes;
    }

    @StringRes
    private static int getMessageResId(ErrorHandler.ErrorTypes type) {
        switch (type) {
            case BASIC_AUTH_ERROR:{
                return R.string.auth_error_message;
            }
            case NO_INTERNET_CONNECTION :{
                return R.string.no_internet_connection_message;
            }
            case MARKED_AS_FAVORITE :{
                return R.string.marked_as_favorite_message;
            }
            case COMMENT_ADDED :{
                return R.string.comment_added_message;
            }
            default: Log.e(TAG,"Unknown ErrorType: " + type);
        }
        return NONE_RES;
    }
}
