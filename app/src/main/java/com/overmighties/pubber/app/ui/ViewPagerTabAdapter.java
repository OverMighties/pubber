package com.overmighties.pubber.app.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentAlcohol;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentInformations;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentMenu;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentOverView;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentPhotos;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentRating;

public class ViewPagerTabAdapter extends FragmentStateAdapter {
    public ViewPagerTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new TabFragmentOverView();
            case 1: return new TabFragmentRating();
            case 2: return new TabFragmentAlcohol();
            case 3: return new TabFragmentMenu();
            case 4: return new TabFragmentPhotos();
            case 5: return new TabFragmentInformations();
            default: return new TabFragmentOverView();

        }
    }



    @Override
    public int getItemCount() {
        return 6;
    }
}
