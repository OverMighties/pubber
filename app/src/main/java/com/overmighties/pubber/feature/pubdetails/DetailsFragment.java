package com.overmighties.pubber.feature.pubdetails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.NavigationBar;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment
{
    private ViewPager2 viewPager;
    private DetailsViewModel viewModel;
    public DetailsFragment() {super(R.layout.details);}
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        //To ukrywa navigation bar
        NavigationBar.smoothHide(getActivity().findViewById(R.id.nav_view));
        viewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        viewPager=requireView().findViewById(R.id.viewPager);
        List<Integer> sliderItemIds=new ArrayList<>();
        sliderItemIds.add(R.drawable.zdjecie1);
        sliderItemIds.add(R.drawable.zdjecie3);
        sliderItemIds.add(R.drawable.zdjecie1);
        sliderItemIds.add(R.drawable.zdjecie3);
        invisibleDetails();

    }

    private void invisibleDetails()
    {
        requireView().findViewById(R.id.googleR).setVisibility(View.GONE);
        requireView().findViewById(R.id.fbR).setVisibility(View.GONE);
        requireView().findViewById(R.id.tripR).setVisibility(View.GONE);
    }

    public void visibleDetails(View v)
    {
        requireView().findViewById(R.id.googleR).setVisibility(View.VISIBLE);;
        requireView().findViewById(R.id.fbR).setVisibility(View.VISIBLE);
        requireView().findViewById(R.id.tripR).setVisibility(View.VISIBLE);
    }



}