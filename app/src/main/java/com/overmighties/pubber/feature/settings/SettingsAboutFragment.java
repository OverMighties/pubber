package com.overmighties.pubber.feature.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

import com.overmighties.pubber.R;

public class SettingsAboutFragment extends Fragment {

    public static final String TAG="SettingsAboutFragment";
    private NavController navController;


    public SettingsAboutFragment(){super(R.layout.fragment_settings_about);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

    }

}