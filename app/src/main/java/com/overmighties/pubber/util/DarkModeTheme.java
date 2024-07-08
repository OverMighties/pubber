package com.overmighties.pubber.util;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Set;

public class DarkModeTheme extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
        SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(this));

        super.onCreate(savedInstanceState);

    }


}
