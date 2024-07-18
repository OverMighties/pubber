package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.SettingsBasicActivity;
import com.overmighties.pubber.util.SettingsHandler;

import java.util.Objects;


public class StartActivity extends SettingsBasicActivity {
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (SettingsHandler.FirstTimeOpenHelper.getTimeOpened(this) == SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME){
            goToMainActivity();
        }
    }
    public void goToMainActivity()
    {
        Intent i=new Intent(StartActivity.this, MainActivity.class);
        startActivity(i);
    }

}