package com.overmighties.pubber.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.LoadActivity;
import com.overmighties.pubber.feature.search.PubListViewModel;

public class PlaceChoiceFragment extends Fragment {

    private PlaceChoiceViewModel viewModel;
    public PlaceChoiceFragment() {
        super(R.layout.place_choice);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(PlaceChoiceViewModel.class);
        ((Button) requireView().findViewById(R.id.city1)).setOnClickListener(v->{
            viewModel.setCity(  ((Button) requireView().findViewById(R.id.city1)).getText().toString());
        });
        ((Button) requireView().findViewById(R.id.city2)).setOnClickListener(v->{
            viewModel.setCity(  ((Button) requireView().findViewById(R.id.city2)).getText().toString());
        });
        ((Button) requireView().findViewById(R.id.city3)).setOnClickListener(v->{
            viewModel.setCity(  ((Button) requireView().findViewById(R.id.city3)).getText().toString());
        });
    }
}