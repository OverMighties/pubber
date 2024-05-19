package com.overmighties.pubber.feature.bookmarks;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.ListPubAdapter;

public class SavedFragment extends Fragment  {
    private ListPubAdapter adapter;
    private  RecyclerView save;

    private SavedViewModel viewModel;
    public SavedFragment(){super(R.layout.saved);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {

        save=(RecyclerView) requireView().findViewById(R.id.savelist);
        viewModel= new ViewModelProvider(this).get(SavedViewModel.class);
        setup();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        setup();
    }

    private void setup()
    {
        String saved=viewModel.getSavedList().getValue();
    }




}