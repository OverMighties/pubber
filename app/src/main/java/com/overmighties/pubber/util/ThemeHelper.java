package com.overmighties.pubber.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {
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
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(KEY_THEME, theme).apply();
    }

    public static int getSavedTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_THEME, THEME_LIGHT);
    }
}
