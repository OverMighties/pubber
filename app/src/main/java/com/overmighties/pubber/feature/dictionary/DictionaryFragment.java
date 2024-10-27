package com.overmighties.pubber.feature.dictionary;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.PubListViewModel;

public class DictionaryFragment extends Fragment {

    public static final String TAG = "DictionaryFragment";

    public DictionaryFragment() {super(R.layout.fragment_dictionary);}

    private DictionaryViewModel viewModel;
    private PubListViewModel pubListViewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DictionaryViewModel.initializer)).get(DictionaryViewModel.class);
        pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState){
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        requireView().findViewById(R.id.dictionary_image_beers).setOnClickListener(v1->{
            prepareBeerData();
        });
        requireView().findViewById(R.id.dictionary_text_beers).setOnClickListener(v1->{
            prepareBeerData();
        });
    }

    private void prepareBeerData() {

        navController.navigate(DictionaryFragmentDirections.actionDictionaryFragmentToDictionarySearchFragment());
    }
}
