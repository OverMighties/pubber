package com.overmighties.pubber.feature.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class SettingsHandler {
    //Blocking default constructor
    private SettingsHandler(){
        throw new AssertionError();
    }
    public static void savePreference(Context context, int theme, String prefs_name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, theme).apply();
    }

    public static int getPreference(Context context, int default_state, String prefs_name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, default_state);
    }

    public static final class FirstTimeOpenHelper{
        private static final String PREFS_NAME = "open_prefs";
        private static final String KEY_TIME = "key_open";
        public static final int FIRST_TIME = 0;
        public static final int NOT_FIRST_TIME = 1;

        public static void setSecond(Context context){
            savePreference(context, NOT_FIRST_TIME, PREFS_NAME, KEY_TIME);
        }

        public static int getTimeOpened(Context context){
            return getPreference(context, FIRST_TIME, PREFS_NAME, KEY_TIME);
        }
    }

    public static final class ThemeHelper {
        private static final String PREFS_NAME = "theme_prefs";
        private static final String KEY_THEME = "key_theme";
        public static final int THEME_LIGHT = 0;
        public static final int THEME_DARK = 1;

        public static void applyTheme(int theme) {
            switch (theme) {
                case THEME_LIGHT:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case THEME_DARK:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }

        public static void saveTheme(Context context, int theme) {
            savePreference(context, theme, PREFS_NAME, KEY_THEME);
        }

        public static int getSavedTheme(Context context) {
            return getPreference(context, THEME_DARK, PREFS_NAME, KEY_THEME);
        }
    }

    public static final class NotificationsHelper{
        private static final String PREFS_NAME = "notification_prefs";
        private static final String KEY_NOTIFICATION = "key_notification";
        public static final int NOTIFICATIONS_ON = 0;
        public static final int NOTIFICATIONS_OFF = 1;

        public static void saveNotificationState(Context context, int state){
            savePreference(context, state, PREFS_NAME, KEY_NOTIFICATION);
        }

        public static int getNotificationState(Context context){
            return getPreference(context, NOTIFICATIONS_ON, PREFS_NAME, KEY_NOTIFICATION);
        }
    }

    public static  final class LanguageHelper{
        private static final String PREFS_NAME = "language_prefs";
        private static final String KEY_NOTIFICATION = "key_language";
        public static final int LANGUAGE_POLISH = 0;
        public static final int LANGUAGE_ENGLISH = 1;

        public static void setLanguage(int language){

            LocaleListCompat appLocale;
            if (language == 0){
                appLocale = LocaleListCompat.forLanguageTags("pl-PL");
            }
            else{
                appLocale = LocaleListCompat.forLanguageTags("en-US");
            }
            AppCompatDelegate.setApplicationLocales(appLocale);

        }

        public static void saveLanguage(Context context, int language){
            savePreference(context, language, PREFS_NAME, KEY_NOTIFICATION);
        }

        public static int getLanguage(Context context){
            return getPreference(context, LANGUAGE_POLISH, PREFS_NAME, KEY_NOTIFICATION);
        }
    }
}
