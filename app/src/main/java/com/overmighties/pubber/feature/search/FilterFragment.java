package com.overmighties.pubber.feature.search;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.DRINKS_VIEW_ID_LIST;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.NavigationBar;
import com.overmighties.pubber.data.FilterConstants;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.util.PriceType;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends Fragment {

    public static final String TAG="FilterFragment";
    public boolean moreBeers;
    private List<String> otherDrinks =new ArrayList<>();;
    private List<String> beers =new ArrayList<>();
    private boolean open;

    public String price= FilterConstants.NONE_PRICE;
    private PubListViewModel viewModel;


    public FilterFragment()
    {
        super(R.layout.filter);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        var nav_bar=getActivity().findViewById(R.id.nav_view);
        requireView().findViewById(R.id.buttonfiltr).setOnClickListener(v->{
            filtration(requireView());

            NavigationBar.smoothPopUp(nav_bar);
            Navigation.findNavController(v).navigate(FilterFragmentDirections.filtrationToSearcher());
        });

        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);

        NavigationBar.smoothHide(nav_bar);
        requireView().findViewById(R.id.wiecej).setOnClickListener(buttView->moreBeers(requireView()));
        arrowExpandersListeners();


    }

    private void arrowExpandersListeners()
    {
        requireView().findViewById(R.id.wiecej).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Slider)requireView().findViewById(R.id.rangeRating)).isShown()) {
                    ((Slider)requireView().findViewById(R.id.rangeRating)).setVisibility(View.GONE);
                } else {
                    ((Slider)requireView().findViewById(R.id.rangeRating)).setVisibility(View.VISIBLE);
                }
            }
        });
        requireView().findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Slider)requireView().findViewById(R.id.odleglosc)).isShown()) {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.GONE);
                }
                else {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.VISIBLE);
                }
            }
        });
        requireView().findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.cena)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.cena)).setVisibility(View.GONE);
                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.cena)).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void filtration(View view)
    {
        RangeSlider averageRatingSlider=view.findViewById(R.id.rangeRating);
        Slider distanceSlider=view.findViewById(R.id.odleglosc);
        //jakiebrowary
        breweriesCheck(view);
        //drinki
        drinksCheck(view);
        //cena
        priceCheck(view);
        isOpen(view);
        FilterUiState filterUiState =new FilterUiState( averageRatingSlider.getValues().get(1),averageRatingSlider.getValues().get(0)
                ,distanceSlider.getValue(),open
                ,null, false,PriceType.getByIcon(price),beers,otherDrinks);

        viewModel.filter(filterUiState);

    }


    public void isOpen(View v) {
       open= ((Chip) v.findViewById(R.id.czyotwarte)).isChecked();
    }

    private void priceCheck(View v){
        if(((Chip) v.findViewById(R.id.malo)).isChecked()) {
            price= FilterConstants.MALO;
        }
        if(((Chip) v.findViewById(R.id.srednio)).isChecked()) {
            price= FilterConstants.SREDNIO;
        }
        if(((Chip) v.findViewById(R.id.duzo)).isChecked()) {
            price= FilterConstants.DUZO;
        }

    }

    private void  drinksCheck(View v){
        for(var sid:DRINKS_VIEW_ID_LIST)
        {
            try {
                int field = R.id.class.getField(sid).getInt(0);
                if (((Chip) v.findViewById(field)).isChecked()) {
                    otherDrinks.add(String.valueOf(((Chip) v.findViewById(field)).getText()));
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }


    private void breweriesCheck(View v)  {
        for(var sid:BREWERIES_VIEW_ID_LIST)
        {
            try{
                int field = R.id.class.getField(sid).getInt(0);
                if(((Chip)v.findViewById(field)).isChecked())
                {
                    beers.add(String.valueOf(((Chip)v.findViewById(field)).getText()));
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }
    public void moreBeers(View v)  {
        if (!moreBeers)
        {
            try{
                for(var sid:BREWERIES_VIEW_ID_LIST)
                {
                    int field = R.id.class.getField(sid).getInt(0);
                   v.findViewById(field).setVisibility(View.VISIBLE);
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
            moreBeers =true;
        }
        else
        {
            try {
                for (var sid : BREWERIES_VIEW_ID_LIST) {
                    int field = R.id.class.getField(sid).getInt(0);
                    v.findViewById(field).setVisibility(View.GONE);
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
            moreBeers =false;
        }
    }


}