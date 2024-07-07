package com.overmighties.pubber.feature.pubdetails.TabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;


public class TabFragmentRating extends Fragment {
    private DetailsViewModel viewModel;
    public TabFragmentRating() {super(R.layout.fragment_tab_rating);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();

        ((TextView)requireView().findViewById(R.id.RTvAvg)).setText(pubDetailsUiState.getRatings().getAverageRating().toString());


    }
}