package com.overmighties.pubber.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;

public class ResourceUtil {
    private static final String TAG = "ResourceUtil";
    //Blocking default constructor
    private ResourceUtil(){
        throw new AssertionError();
    }
    public static int getResourceIdByName(Context context, String resName) {
        try {
            return context.getResources().getIdentifier(resName, "id", context.getPackageName());
        } catch (Exception e) {
            Log.e(TAG, "Resource not found: " + resName, e);
            return 0;
        }
    }

    public static String getResStringLanguage(Context context,int id, String lang){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale originalLocale = configuration.getLocales().get(0);
        Locale newLocale = new Locale(lang);
        Locale.setDefault(newLocale);
        Configuration newConfig = new Configuration(configuration);
        newConfig.setLocale(newLocale);
        Context localizedContext = context.createConfigurationContext(newConfig);
        String localizedString = localizedContext.getString(id);

        Locale.setDefault(originalLocale);

        return localizedString;
    }

}
