package com.overmighties.pubber.app.exception;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.overmighties.pubber.R;
import com.overmighties.pubber.util.UIText;

public class ErrorUIHandler {
    public static final String TAG = "ErrorHandler";
    public static final int NONE_RES=-1;

    public enum ErrorTypes {
        FIREBASE_AUTH_BASIC_ERROR,
        FIREBASE_AUTH_EMAIL_ERROR,
        FIREBASE_AUTH_PASSWORD_ERROR,
        AUTH_NOT_SAME_PASSWORDS,
        BASIC_ERROR,
        NO_INTERNET_CONNECTION,
    }

    public static String getErrorMessage(Context context, ErrorUIHandler.ErrorTypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        int messageResId = authRes==null?getMessageResId(type):authRes.getResId();
        if(type == ErrorUIHandler.ErrorTypes.BASIC_ERROR
                || type == ErrorUIHandler.ErrorTypes.FIREBASE_AUTH_EMAIL_ERROR
                || type == ErrorUIHandler.ErrorTypes.FIREBASE_AUTH_PASSWORD_ERROR
                || type == ErrorUIHandler.ErrorTypes.FIREBASE_AUTH_BASIC_ERROR
                || type == ErrorUIHandler.ErrorTypes.AUTH_NOT_SAME_PASSWORDS)
            logMes += context.getText(R.string.try_again);
        if(messageResId!=NONE_RES)
            logMes=context.getText(messageResId)+logMes;
        return logMes;
    }

    @StringRes
    private static int getMessageResId(ErrorUIHandler.ErrorTypes type) {
        switch (type) {
            case BASIC_ERROR:{
                return R.string.auth_error_message;
            }
            case NO_INTERNET_CONNECTION :{
                return R.string.no_internet_connection_message;
            }
            default: Log.e(TAG,"Unknown ErrorType: " + type);
        }
        return NONE_RES;
    }
}
