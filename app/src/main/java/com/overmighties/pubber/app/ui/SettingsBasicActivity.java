package com.overmighties.pubber.app.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import com.overmighties.pubber.feature.settings.SettingsHandler;

import java.util.Calendar;
import java.util.TimeZone;

public class SettingsBasicActivity extends AppCompatActivity {

    private static double WarsawLatitude = 52.2297;
    private static double WarsawLongitude = 21.0122;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SettingsHandler.FirstTimeOpenHelper.getTimeOpened(this) == SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME){
            SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
            SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(this));
        }
        else{
            Pair<Integer, Integer> sunsetTime = getSunsetTime(TimeZone.getTimeZone("Europe/Warsaw"));
            Calendar time_now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
            if(sunsetTime.first < time_now.get(Calendar.HOUR_OF_DAY)){
                SettingsHandler.ThemeHelper.saveTheme(this, SettingsHandler.ThemeHelper.THEME_DARK);

            }
            else if(sunsetTime.first == time_now.get(Calendar.HOUR_OF_DAY)){
                if (sunsetTime.second <= time_now.get(Calendar.MINUTE)){
                    SettingsHandler.ThemeHelper.saveTheme(this, SettingsHandler.ThemeHelper.THEME_DARK);
                }
                else{
                    SettingsHandler.ThemeHelper.saveTheme(this, SettingsHandler.ThemeHelper.THEME_LIGHT);

                }
            }
            else{
                SettingsHandler.ThemeHelper.saveTheme(this, SettingsHandler.ThemeHelper.THEME_LIGHT);
            }
            SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
        }

    }

    private Pair<Integer, Integer> getSunsetTime(TimeZone timeZone) {
        Location location = new Location(String.valueOf(WarsawLatitude), String.valueOf(WarsawLongitude));

        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, timeZone);

        Calendar sunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());

        return new Pair<> (sunset.get(Calendar.HOUR_OF_DAY), sunset.get(Calendar.MINUTE));
    }


}
