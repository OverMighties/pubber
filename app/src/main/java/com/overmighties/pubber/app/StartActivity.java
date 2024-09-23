package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.SettingsBasicActivity;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.feature.firstopen.FirstOpenViewModel;

import java.util.Locale;


public class StartActivity extends SettingsBasicActivity {
    private NavController navController;
    private FirstOpenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (SettingsHandler.FirstTimeOpenHelper.getTimeOpened(this).equals(SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME)){
            goToMainActivity();
        }else {
            viewModel = new ViewModelProvider(this,
                    ViewModelProvider.Factory.from(FirstOpenViewModel.initializer)).get(FirstOpenViewModel.class);
            if (Locale.getDefault().getLanguage().equals("en")) {
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.English);
            } else if (Locale.getDefault().getLanguage().equals("uk")) {
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Ukrainian);
            } else {
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Polish);
            }
            if (SettingsHandler.ThemeHelper.getSavedTheme(this).equals(SettingsHandler.ThemeHelper.THEME_DARK)) {
                viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Dark);
            } else {
                viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Light);
            }
        }
    }
    public void goToMainActivity()
    {
        Intent i=new Intent(StartActivity.this, MainActivity.class);
        startActivity(i);
    }

}