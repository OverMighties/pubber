package com.overmighties.pubber.app.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.overmighties.pubber.util.ThemeHelper;

public class DarkModeTheme extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeHelper.applyTheme(ThemeHelper.getSavedTheme(this));
        super.onCreate(savedInstanceState);

    }


}
