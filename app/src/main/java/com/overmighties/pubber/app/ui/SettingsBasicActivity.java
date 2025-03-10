package com.overmighties.pubber.app.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.overmighties.pubber.app.settings.SettingsHandler;

public class SettingsBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SettingsHandler.FirstTimeOpenHelper.getTimeOpened(this).equals(SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME)){
            SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
            SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(this));
        }
    }




}
