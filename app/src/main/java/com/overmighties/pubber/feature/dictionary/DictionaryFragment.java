package com.overmighties.pubber.feature.dictionary;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.dictionary.stateholders.AlcoholCardViewUiState;
import com.overmighties.pubber.feature.search.PubListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        requireView().findViewById(R.id.dictionary_image_back).setOnClickListener(v1->{
            navController.popBackStack();
        });
        requireView().findViewById(R.id.dictionary_image_beers).setOnClickListener(v1->{
            prepareBeerData();
        });
        requireView().findViewById(R.id.dictionary_text_beers).setOnClickListener(v1->{
            prepareBeerData();
        });
    }

    private void prepareBeerData() {
        List<AlcoholCardViewUiState> list = new ArrayList<>();
        for(var drink: Objects.requireNonNull(pubListViewModel.get_originalDrinksData().getValue())){
            List<String> parametrs = new ArrayList<>();
            parametrs.add("4.2");//parametrs.add(drink.getBeer().getHopiness());
            parametrs.add(drink.getBeer().getMaltiness());
            parametrs.add(drink.getBeer().getAlcoholContent());
            list.add(new AlcoholCardViewUiState(
                    drink.getDrinkId(),
                    drink.getName(),
                    drink.getBeer().getShortDescription(),
                    drink.getBeer().getLongDescription(),
                    drink.getBeer().getPhotoUrl(),
                    parametrs
            ));
        }
        viewModel.UiState().getValue().setAlcoholDataList(list);
        navController.navigate(DictionaryFragmentDirections.actionDictionaryFragmentToDictionarySearchFragment());
    }
}
