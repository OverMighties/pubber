package com.overmighties.pubber.feature.pubdetails.TabFragments;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import java.util.ArrayList;


import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;
import com.overmighties.pubber.R;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentsUiState;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;




public class TabFragmentAlcohol extends Fragment {

    private DetailsViewModel viewModel;
    private TabFragmentsViewModel fragmentsViewModel;
    private ArrayList<DrinkDto> drinksDataSet1 = new ArrayList<>();
    public TabFragmentAlcohol() {super(R.layout.fragment_tab_alcohol);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();
        fragmentsViewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(TabFragmentsViewModel.initializer)).get(TabFragmentsViewModel.class);
        TabFragmentsUiState tabFragmentsUiState= TabFragmentsViewModel.getTabFragmentsUiState().getValue();

        loadData(tabFragmentsUiState);
    }



    private void loadData(TabFragmentsUiState tabFragmentsUiState){
        for (Integer id:tabFragmentsUiState.getIdsOfBeerChips()){
            requireView().findViewById(id).setVisibility(View.VISIBLE);
        }
        for(Integer id:tabFragmentsUiState.getIdsOfDrinkChips()){
            requireView().findViewById(id).setVisibility(View.VISIBLE);
        }
    }
}