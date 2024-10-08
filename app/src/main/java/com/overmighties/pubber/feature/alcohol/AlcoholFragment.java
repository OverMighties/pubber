package com.overmighties.pubber.feature.alcohol;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;

public class AlcoholFragment  extends Fragment {

    public static final String TAG = "AlcoholFragment";

    public AlcoholViewModel viewModel;

    public AlcoholFragment() {super(R.layout.fragment_alcohol);}


    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AlcoholViewModel.class);
    }

}
