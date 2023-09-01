package com.overmighties.pubber.ui;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.DRINKS_VIEW_ID_LIST;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.NavigationBar;
import com.overmighties.pubber.data.FiltrationConstants;
import com.overmighties.pubber.data.FiltrationData;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FiltrationFragment extends Fragment  {

    public static final String TAG="FiltrationFragment";
    public boolean moreBeers;
    private List<String> drinks=new ArrayList<>();;
    private List<String> breweries=new ArrayList<>();
    private boolean open;

    public String price=FiltrationConstants.BASE_PRICE;
    private FiltrationData filtrationData;


    public FiltrationFragment()
    {
        super(R.layout.filtration);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        var nav_bar=getActivity().findViewById(R.id.nav_view);
        requireView().findViewById(R.id.buttonfiltr).setOnClickListener(v->{
            filtration(requireView());
            NavigationBar.smoothPopUp(nav_bar);
            Navigation.findNavController(v).navigate(FiltrationFragmentDirections.filtrationToSearcher());
        });
        NavigationBar.smoothHide(nav_bar);
        requireView().findViewById(R.id.wiecej).setOnClickListener(buttView->moreBeers(requireView()));

        //setting listeners unfolding diffrent filtration settings
        listeners();
        //setting up dropdown menus with days and time
        dropdownmenus();

    }


    private void dropdownmenus()
    {

        requireView().findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().setValue("days");
                    requireView().findViewById(R.id.view).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
                    ((ImageView)requireView().findViewById(R.id.imageView7)).setImageResource(R.drawable.arrowblackup);
            }
        });
        requireView().findViewById(R.id.view2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().setValue("time");
                requireView().findViewById(R.id.view2).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
                ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.arrowblackup);
            }
        });
    }




    private void listeners()
    {
        requireView().findViewById(R.id.wiecej).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((requireView().findViewById(R.id.rangeRating)).isShown()) {
                    (requireView().findViewById(R.id.rangeRating)).setVisibility(View.GONE);
                    ((ImageView) requireView().findViewById(R.id.imageView)).setImageResource(R.drawable.arrowwhite);
                } else {
                    (requireView().findViewById(R.id.rangeRating)).setVisibility(View.VISIBLE);
                    ((ImageView) requireView().findViewById(R.id.imageView)).setImageResource(R.drawable.arrowwhiteup);
                }
            }
        });

        requireView().findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Slider)requireView().findViewById(R.id.odleglosc)).isShown())
                {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.GONE);

                    ((ImageView)requireView().findViewById(R.id.imageView2)).setImageResource(R.drawable.arrowwhite);


                }
                else
                {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.VISIBLE);

                    ((ImageView)requireView().findViewById(R.id.imageView2)).setImageResource(R.drawable.arrowwhiteup);

                }
            }
        });
        requireView().findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.cena)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.cena)).setVisibility(View.GONE);

                    ((ImageView)requireView().findViewById(R.id.imageView3)).setImageResource(R.drawable.arrowwhite);

                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.cena)).setVisibility(View.VISIBLE);

                    ((ImageView)requireView().findViewById(R.id.imageView3)).setImageResource(R.drawable.arrowwhiteup);
                }
            }
        });

        requireView().findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Chip)requireView().findViewById(R.id.dowolne)).isShown())
                {
                    ((ImageView)requireView().findViewById(R.id.imageView4)).setImageResource(R.drawable.arrowwhite);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setVisibility(View.GONE);
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setVisibility(View.GONE);
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setVisibility(View.GONE);
                    ((ConstraintLayout)requireView().findViewById(R.id.constraintLayout6)).setVisibility(View.GONE);
                    int top = dpToPx(4);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)  ((CardView)requireView().findViewById(R.id.cardView6)).getLayoutParams();
                    params.topMargin=top;
                }
                else
                {
                    ((ImageView)requireView().findViewById(R.id.imageView4)).setImageResource(R.drawable.arrowwhiteup);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setVisibility(View.VISIBLE);
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setVisibility(View.VISIBLE);
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setVisibility(View.VISIBLE);
                    int top = dpToPx(8);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)  ((CardView)requireView().findViewById(R.id.cardView6)).getLayoutParams();
                    params.topMargin=top;
                }
            }
        });
        requireView().findViewById(R.id.niestandardowe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Chip)requireView().findViewById(R.id.niestandardowe)).isChecked())
                {
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setChecked(false);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setChecked(false);
                    ((ConstraintLayout)requireView().findViewById(R.id.constraintLayout6)).setVisibility(View.VISIBLE);
                }
                else {((Chip)requireView().findViewById(R.id.niestandardowe)).setChecked(true);}
            }
        });
        requireView().findViewById(R.id.czyotwarte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Chip)requireView().findViewById(R.id.czyotwarte)).isChecked())
                {
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setChecked(false);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setChecked(false);
                    ((ConstraintLayout)requireView().findViewById(R.id.constraintLayout6)).setVisibility(View.GONE);
                }
                else {((Chip)requireView().findViewById(R.id.czyotwarte)).setChecked(true);}
            }
        });
        requireView().findViewById(R.id.dowolne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Chip)requireView().findViewById(R.id.dowolne)).isChecked())
                {
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setChecked(false);
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setChecked(false);
                    ((ConstraintLayout)requireView().findViewById(R.id.constraintLayout6)).setVisibility(View.GONE);
                }
                else {((Chip)requireView().findViewById(R.id.dowolne)).setChecked(true);}
            }
        });

        requireView().findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.BrowaryLista)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.BrowaryLista)).setVisibility(View.GONE);
                    ((TextView)requireView().findViewById(R.id.wiecej)).setVisibility(View.GONE);
                    ((ImageView)requireView().findViewById(R.id.imageView5)).setImageResource(R.drawable.arrowwhite);
                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.BrowaryLista)).setVisibility(View.VISIBLE);
                    ((TextView)requireView().findViewById(R.id.wiecej)).setVisibility(View.VISIBLE);
                    ((ImageView)requireView().findViewById(R.id.imageView5)).setImageResource(R.drawable.arrowwhiteup);
                }
            }
        });
        requireView().findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.Drinki)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.Drinki)).setVisibility(View.GONE);
                    ((ImageView)requireView().findViewById(R.id.imageView6)).setImageResource(R.drawable.arrowwhite);

                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.Drinki)).setVisibility(View.VISIBLE);
                    ((ImageView)requireView().findViewById(R.id.imageView6)).setImageResource(R.drawable.arrowwhiteup);

                }
            }
        });
        requireView().findViewById(R.id.buttonreset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChipGroup)requireView().findViewById(R.id.Drinki)).clearCheck();
                ((ChipGroup)requireView().findViewById(R.id.cena)).clearCheck();
                ((ChipGroup)requireView().findViewById(R.id.BrowaryLista)).clearCheck();
                ((Chip)requireView().findViewById(R.id.dowolne)).performClick();
                ((RangeSlider)requireView().findViewById(R.id.rangeRating)).setValues(0f,5f);
                ((Slider)requireView().findViewById(R.id.odleglosc)).setValue(5f);
            }
        });
    }





    public void filtration(View view)
    {
        RangeSlider r=view.findViewById(R.id.rangeRating);
        Slider s=view.findViewById(R.id.odleglosc);
        //jakiebrowary
        breweriesCheck(view);
        //drinki
        drinksCheck(view);
        //cena
        priceCheck(view);
        isOpen(view);
        filtrationData=new FiltrationData.Builder()
                .distance(s.getValue())
                .bottomRating( r.getValues().get(0))
                .upperRating( r.getValues().get(1))
                .isOpen(open)
                .price(price)
                .breweries(breweries)
                .drinks(drinks)
                .build();
       // Log.d(TAG, "filtration: bottom rating"+r.getValues().get(0)+ ", upper "+r.getValues().get(1));
        AppContainer
                .getInstance()
                .getPubSearchingContainer()
                .getFiltrationOfPubs()
                .setValue(filtrationData);
    }


    public void isOpen(View v) {
       open= ((Chip) v.findViewById(R.id.czyotwarte)).isChecked();
    }

    private void priceCheck(View v){
        if(((Chip) v.findViewById(R.id.malo)).isChecked()) {
            price= FiltrationConstants.MALO;
        }
        if(((Chip) v.findViewById(R.id.srednio)).isChecked()) {
            price= FiltrationConstants.SREDNIO;
        }
        if(((Chip) v.findViewById(R.id.duzo)).isChecked()) {
            price= FiltrationConstants.DUZO;
        }

    }

    private void  drinksCheck(View v){
        for(var sid:DRINKS_VIEW_ID_LIST)
        {
            try {
                int field = R.id.class.getField(sid).getInt(0);
                if (((Chip) v.findViewById(field)).isChecked()) {
                    drinks.add(String.valueOf(((Chip) v.findViewById(field)).getText()));
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
                    breweries.add(String.valueOf(((Chip)v.findViewById(field)).getText()));
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

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}