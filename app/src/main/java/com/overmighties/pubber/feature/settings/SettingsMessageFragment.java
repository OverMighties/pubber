package com.overmighties.pubber.feature.settings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;

import com.overmighties.pubber.R;

public class SettingsMessageFragment extends Fragment {

    public static final String TAG="SettingsMessageFragment";
    private SettingsMessageViewModel viewModel;

    private NavController navController;

    public SettingsMessageFragment(){super(R.layout.fragment_settings_message);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        Log.i(TAG,"on create");
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(SettingsMessageViewModel.initializer))
                .get(SettingsMessageViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

    }

}