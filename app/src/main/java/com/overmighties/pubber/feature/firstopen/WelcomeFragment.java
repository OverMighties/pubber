package com.overmighties.pubber.feature.firstopen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

import com.overmighties.pubber.R;

public class WelcomeFragment extends Fragment {

    private WelcomeViewModel viewModel;

    public WelcomeFragment(){super(R.layout.fragment_welcome);}
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.buttonNextL).setOnClickListener(v->{
            navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLanguageFragment());
        });
    }

}