package com.overmighties.pubber.feature.dictionary;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;

import java.util.ArrayList;
import java.util.List;

public class DictionarySearchFragment extends Fragment {

    public static final String TAG = "DictionaryFragment";

    public DictionarySearchFragment() {super(R.layout.fragment_dictionary_select);}

    private DictionaryViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DictionaryViewModel.initializer)).get(DictionaryViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState){
        List<AlcoholCardViewUiState> data = new ArrayList<>();
        data.add(new AlcoholCardViewUiState("Trzech Kumpli Lager Niemiecki",
                "mega goated",
                "Piwowarska klasyka na najlepszych składnikach zza Odry, czyli bardzo jasnych słodach jęczmiennych oraz trzech odmianach chmielu: Spalt Select, Polaris i Saphir. Po tym “dolniaku” spodziewać się można jasnego koloru, obowiązkowego klaru, słodkich aromatów słodu pilzneńskiego oraz ziołowo-ziemistych nut szlachetnych chmieli. Piwo ma także solidną, ale nieprzesadzoną gorycz.\n"
                ,"link"));
        AlcoholListAdapter adapter = new AlcoholListAdapter(data);
        ((RecyclerView)requireView().findViewById(R.id.dictionarySelect_recyclerView_search)).setAdapter(adapter);
    }
}
