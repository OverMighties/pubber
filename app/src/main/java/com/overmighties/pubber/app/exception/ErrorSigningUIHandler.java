package com.overmighties.pubber.app.exception;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.overmighties.pubber.R;
import com.overmighties.pubber.util.UIText;

public class ErrorSigningUIHandler {
    public static final String TAG = "ErrorHandler";
    public static final int NONE_RES=-1;


    public static String getErrorMessage(Context context, ErrorSigningUITypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        int messageResId = authRes==null?getMessageResId(type):authRes.getResId();
        if(type == ErrorSigningUITypes.UNKNOWN_ERROR
                || type == ErrorSigningUITypes.FIREBASE_AUTH_EMAIL_ERROR
                || type == ErrorSigningUITypes.FIREBASE_AUTH_PASSWORD_ERROR
                || type == ErrorSigningUITypes.FIREBASE_AUTH_BASIC_ERROR
                || type == ErrorSigningUITypes.AUTH_NOT_SAME_PASSWORDS)
            logMes += context.getText(R.string.try_again);
        if(messageResId!=NONE_RES)
            logMes=context.getText(messageResId)+logMes;
        return logMes;
    }

    @StringRes
    private static int getMessageResId(ErrorSigningUITypes type) {
        switch (type) {
            case FIREBASE_AUTH_BASIC_ERROR:{
                return R.string.auth_error_message;
            }
            case UNKNOWN_ERROR:{
                return R.string.sth_went_wrong;
            }
            default: {
                Log.e(TAG,"Unknown ErrorType: " + type);
            }
        }
        return NONE_RES;
    }
}
