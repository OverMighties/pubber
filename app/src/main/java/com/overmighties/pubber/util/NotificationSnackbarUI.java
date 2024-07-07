package com.overmighties.pubber.util;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.overmighties.pubber.R;

public class NotificationSnackbarUI {


    public static final String TAG="NotificationSnackbarUI";
    public static final int NONE_RES=-1;
    public enum SnackbarTypes {
        MARKED_AS_FAVORITE,
        COMMENT_ADDED
    }
    public static void showSnackbar(View view, SnackbarTypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        int messageResId = authRes==null?getMessageResId(type):authRes.getResId();
        if(messageResId==NONE_RES)
            Snackbar.make(view,  logMes, Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(view,  view.getContext().getText(messageResId)+logMes, Snackbar.LENGTH_LONG).show();
    }

    @StringRes
    private static int getMessageResId(SnackbarTypes type) {
        switch (type) {
            case MARKED_AS_FAVORITE :{
                return R.string.marked_as_favorite_message;
            }
            case COMMENT_ADDED :{
                return R.string.comment_added_message;
            }
            default: Log.e(TAG,"Unknown SnackbarType: " + type);
        }
        return NONE_RES;
    }
}
