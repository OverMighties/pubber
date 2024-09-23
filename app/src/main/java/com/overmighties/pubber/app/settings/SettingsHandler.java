package com.overmighties.pubber.app.settings;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.overmighties.pubber.R;

public class SettingsHandler {

    //Blocking default constructor
    private SettingsHandler(){
        throw new AssertionError();
    }
    public static void savePreference(Context context, String value, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getPreference(Context context, String default_state, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, default_state);
    }

    public static final class FirstTimeOpenHelper{
        private static final String KEY_TIME = "key_open";
        private static final String FILE_NAME = "open_info";
        public static final String FIRST_TIME = "first";
        public static final String NOT_FIRST_TIME = "second";

        public static void setSecond(Context context){
            SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(KEY_TIME, NOT_FIRST_TIME).apply();
        }

        public static String getTimeOpened(Context context){
            SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_TIME, FIRST_TIME);
        }
    }

    public static final class ThemeHelper {
        public static final String THEME_LIGHT = "Light";
        public static final String THEME_DARK = "Dark";

        public static void applyTheme(String theme) {
            switch (theme) {
                case THEME_LIGHT:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case THEME_DARK:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }

        public static void saveTheme(Context context, String theme) {
            savePreference(context, theme, context.getString(R.string.theme_key));
        }

        public static String getDefaultTheme(Context context){
            UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
            if (uiModeManager != null) {
                int mode = uiModeManager.getNightMode();
                return (mode == UiModeManager.MODE_NIGHT_YES)?THEME_DARK:THEME_LIGHT;
            }
            return THEME_DARK;
        }

        public static String getSavedTheme(Context context) {
            return getPreference(context, THEME_DARK, context.getString(R.string.theme_key));
        }
    }

    public static final class NotificationsHelper{
        public static String getNotificationState(Context context){
            return getPreference(context, "false", context.getString(R.string.notifications_key));
        }
    }





    public static  final class LanguageHelper{
        public static final String LANGUAGE_POLISH = "Polski";
        public static final String LANGUAGE_ENGLISH = "English";
        public static final String LANGUAGE_UKRAINIAN = "Український";

        public static void setLanguage(String language){

            LocaleListCompat appLocale;
            if (language.equals(LANGUAGE_POLISH)){
                appLocale = LocaleListCompat.forLanguageTags("pl-PL");
            }
            else{
                appLocale = LocaleListCompat.forLanguageTags("en-US");
            }
            AppCompatDelegate.setApplicationLocales(appLocale);

        }

        public static void saveLanguage(Context context, String language){
            savePreference(context, language, context.getString(R.string.language_key));
        }

        public static String getLanguage(Context context){
            return getPreference(context, LANGUAGE_POLISH, context.getString(R.string.language_key));
        }
    }
}
