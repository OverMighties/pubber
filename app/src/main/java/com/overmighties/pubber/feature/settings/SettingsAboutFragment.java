package com.overmighties.pubber.feature.settings;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.NavigationBar;

public class SettingsAboutFragment extends Fragment {

    public static final String TAG="SettingsAboutFragment";
    private SettingsAboutFragment viewModel;
    private NavController navController;


    public SettingsAboutFragment(){super(R.layout.fragment_settings_about);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        Log.i(TAG,"on create");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);

        if (getActivity().findViewById(R.id.bottom_nav_view) != null)
            NavigationBar.smoothHide(getActivity().findViewById(R.id.bottom_nav_view));

        requireView().findViewById(R.id.IV_goback_a).setOnClickListener(v->{
            navController.popBackStack();
        });

    }

}