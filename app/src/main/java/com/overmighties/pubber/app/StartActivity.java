package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.SettingsBasicActivity;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.feature.open.FirstOpenViewModel;

import java.util.Locale;


public class StartActivity extends SettingsBasicActivity {
    private NavController navController;
    private FirstOpenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void goToMainActivity()
    {
        Intent i=new Intent(StartActivity.this, MainActivity.class);
        startActivity(i);
    }
}