package com.overmighties.pubber.feature.owner;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.overmighties.pubber.R;

public class InformationFragment extends Fragment {

    public static final String TAG = "InformationFragment";

    public InformationFragment() {super(R.layout.fragment_owner_information);}
    private OwnerViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(OwnerViewModel.initializer))
                .get(OwnerViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

        requireActivity().requireViewById(R.id.main_topAppBarLayout_back).setVisibility(View.VISIBLE);
        ((MaterialToolbar)requireActivity().requireViewById(R.id.main_topAppBarView_back)).setTitle(null);
    }
}
