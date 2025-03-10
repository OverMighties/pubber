package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.feature.open.FirstOpenViewModel;


public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private NavController navController;
    private FirstOpenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SettingsHandler.FirstTimeOpenHelper.getTimeOpened(this).equals(SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME)){
            if(SettingsHandler.ThemeHelper.getAppliedTheme(this).equals(SettingsHandler.ThemeHelper.getSavedTheme(this))) {
                SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(this));
            } else {
                SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getAppliedTheme(this));
                SettingsHandler.ThemeHelper.saveTheme(this, SettingsHandler.ThemeHelper.getAppliedTheme(this));
            }
        } else {
            SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(this));
        }
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_start);

    }
    public void goToMainActivity()
    {
        Intent i=new Intent(StartActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}