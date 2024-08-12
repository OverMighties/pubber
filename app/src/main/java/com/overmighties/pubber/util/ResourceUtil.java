package com.overmighties.pubber.util;

import android.content.Context;
import android.util.Log;

public class ResourceUtil {
    private static final String TAG = "ResourceUtil";

    public static int getResourceIdByName(Context context, String resName) {
        try {
            return context.getResources().getIdentifier(resName, "id", context.getPackageName());
        } catch (Exception e) {
            Log.e(TAG, "Resource not found: " + resName, e);
            return 0;
        }
    }
}
