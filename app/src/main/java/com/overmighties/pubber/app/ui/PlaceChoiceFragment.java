package com.overmighties.pubber.app.ui;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.PLACE_CHOICE_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.PubListViewModel;

public class PlaceChoiceFragment extends Fragment {

    private PubListViewModel pubListViewModel;
    private NavController navController;
    public PlaceChoiceFragment() {
        super(R.layout.place_choice);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        pubListViewModel = new ViewModelProvider(requireActivity()).get(PubListViewModel.class);
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.city1).setOnClickListener(v->{
            pubListViewModel.setCityConstraint(  ((Button) requireView().findViewById(R.id.city1)).getText().toString());
            navController.navigate(getNavDirections(PLACE_CHOICE_FRAGMENT,SEARCHER_FRAGMENT));
        });
        requireView().findViewById(R.id.city2).setOnClickListener(v->{
            pubListViewModel.setCityConstraint(  ((Button) requireView().findViewById(R.id.city2)).getText().toString());
            navController.navigate(getNavDirections(PLACE_CHOICE_FRAGMENT,SEARCHER_FRAGMENT));
        });
        requireView().findViewById(R.id.city3).setOnClickListener(v->{
            pubListViewModel.setCityConstraint(  ((Button) requireView().findViewById(R.id.city3)).getText().toString());
            navController.navigate(getNavDirections(PLACE_CHOICE_FRAGMENT,SEARCHER_FRAGMENT));
        });

        if ((requireActivity().getIntent().hasExtra("openSettings") && requireActivity().getIntent().getBooleanExtra("openSettings", false))){
            if(requireActivity().getIntent().hasExtra("city"))
                pubListViewModel.setCityConstraint(requireActivity().getIntent().getStringExtra("city"));
                navController.navigate(getNavDirections(PLACE_CHOICE_FRAGMENT,SEARCHER_FRAGMENT));
        }
    }
}