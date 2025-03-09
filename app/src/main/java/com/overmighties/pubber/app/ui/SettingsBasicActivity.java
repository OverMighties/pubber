package com.overmighties.pubber.app.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import com.overmighties.pubber.app.settings.SettingsHandler;

import java.util.Calendar;
import java.util.TimeZone;

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
