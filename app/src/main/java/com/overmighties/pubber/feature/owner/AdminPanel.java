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
import com.overmighties.pubber.feature.auth.SplashViewModel;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.feature.search.filterselect.DrinkListAdapter;
import com.overmighties.pubber.feature.search.filterselect.FilterSelectViewModel;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectDrinkCardViewUiState;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends Fragment {

    public static final String TAG = "AdminFragment";

    public AdminPanel() {super(R.layout.fragment_owner_admin);}
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
        navController = Navigation.findNavController(requireActivity(), R.id.main_navHostFragment_container);


    }
}
