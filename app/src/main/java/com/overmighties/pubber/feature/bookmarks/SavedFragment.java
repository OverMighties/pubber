package com.overmighties.pubber.feature.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.overmighties.pubber.util.SelectListener;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.data.model.PubUiState;
import com.overmighties.pubber.util.ListPubAdapter;

import java.util.ArrayList;

public class SavedFragment extends Fragment implements SelectListener {
    private ListPubAdapter adapter;
    private ArrayList<PubUiState> list=new ArrayList<>();
    private  RecyclerView save;

    private SavedViewModel viewModel;
    public SavedFragment(){super(R.layout.saved);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        list.clear();
        save=(RecyclerView) requireView().findViewById(R.id.savelist);
        viewModel= new ViewModelProvider(this).get(SavedViewModel.class);
        setup();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        list.clear();
        setup();
    }

    private void setup()
    {
        String saved=viewModel.getSavedList().getValue();


    }

    @Override
    public void onItemClicked(int position) {
        Navigation.findNavController(requireView()).navigate(SearcherFragmentDirections.searcherToDetail());
    }



}