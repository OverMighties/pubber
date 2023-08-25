package com.overmighties.pubber.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.Interface.SelectListener;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.data.PubData;
import com.overmighties.pubber.test_data.TestData;
import com.overmighties.pubber.util.ListPubAdapter;

import java.util.ArrayList;

public class SavedFragment extends Fragment implements SelectListener {
    public SavedFragment(){super(R.layout.saved);}

    private ListPubAdapter adapter;
    private ArrayList<PubData> list=new ArrayList<>();
    private String saved;
    private  RecyclerView save;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        list.clear();
        save=(RecyclerView) requireView().findViewById(R.id.savelist);
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
        saved=AppContainer.getInstance().getPubSearchingContainer().getSavedList().getValue();
        int helper=0;
        Log.d("tak",saved);
        if(saved!=null)
        {
            while(helper<saved.length()-1)
            {
                list.add(TestData.getPubDataList().get(Integer.parseInt(saved.substring(helper,helper+1))-1));
                helper++;
            }

            adapter = new ListPubAdapter(list, this);
            save.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClicked(int position) {
        Navigation.findNavController(requireView()).navigate(SearcherFragmentDirections.searcherToDetail());
        AppContainer.getInstance().getPubSearchingContainer().getPosition().setValue(position);
    }



}