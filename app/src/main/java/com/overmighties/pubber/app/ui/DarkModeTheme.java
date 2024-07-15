package com.overmighties.pubber.app.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.overmighties.pubber.util.SettingsHandler;

public class DarkModeTheme extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
        SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(this));

    }


}
