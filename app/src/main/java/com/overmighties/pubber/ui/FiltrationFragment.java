package com.overmighties.pubber.ui;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.DRINKS_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.NUMBERSDAYS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_TEXTIDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXTIDS;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class FiltrationFragment extends Fragment {

    public static final String TAG="FiltrationFragment";
    public boolean moreBeers;
    private List<String> drinks=new ArrayList<>();;
    private List<String> breweries=new ArrayList<>();
    private boolean open;

<<<<<<< Updated upstream:app/src/main/java/com/overmighties/pubber/ui/FiltrationFragment.java
    public String price=FiltrationConstants.BASE_PRICE;
    private FiltrationData filtrationData;
=======
    public String price= FilterConstants.NONE_PRICE;
    private PubListViewModel viewModel;
    private PopupWindow popupWindow;
    private View popUpView;
>>>>>>> Stashed changes:app/src/main/java/com/overmighties/pubber/feature/search/FilterFragment.java

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
<<<<<<< Updated upstream:app/src/main/java/com/overmighties/pubber/ui/FiltrationFragment.java
        requireView().findViewById(R.id.wiecej).setOnClickListener(buttView->moreBeers(requireView()));
        listeners();


    }

    private void listeners()
=======
        arrowExpandersListeners();
        dropDownMenusListener();

    }

    private void dropDownMenusListener()
    {

        requireView().findViewById(R.id.Dayview).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Down Menus days in FilterFragment
                popUpView = LayoutInflater.from(getActivity()).inflate(R.layout.menu_pop_up_days, null);
                popupWindow = new PopupWindow(popUpView,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, true);
                //Listeners For popup days in filtrarion screen
                listenersmenudays(popupWindow, popUpView);
                //cheking which one is selected to highlight it
                whichOneCheckedDays(popUpView);
                popupWindow.showAsDropDown(requireView().findViewById(R.id.Dayview),0,0,Gravity.BOTTOM);
                requireView().findViewById(R.id.Dayview).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
                ((ImageView)requireView().findViewById(R.id.imageView7)).setImageResource(R.drawable.arrowblackup);
            }
        });
        requireView().findViewById(R.id.Timeview).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popUpView = LayoutInflater.from(getActivity()).inflate(R.layout.menu_pop_up_time, null);
                popupWindow = new PopupWindow(popUpView,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, true);
                //Listeners For popup time in filtrarion screen
                listenersmenutime(popupWindow, popUpView);
                //cheking which one is selected to highlight it
                whichOneCheckedTime(popUpView);
                popupWindow.showAsDropDown(requireView().findViewById(R.id.Timeview),0,0,Gravity.BOTTOM);
                ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.arrowblackup);
                ((View)requireView().findViewById(R.id.Timeview)).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
            }
        });
    }

    private void whichOneCheckedTime(View popUpView) {
        Integer i=0;
        for (var id : POP_UP_TIME_TEXTIDS) {
            if (((TextView) requireView().findViewById(R.id.textView18)).getText().toString().equals(((TextView) popUpView.findViewById(id)).getText().toString())) {
                popUpView.findViewById(POP_UP_TIME_IDS[i]).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#FFFFFF"));
                popUpView.findViewById(POP_UP_TIME_IDS[i]).getParent().requestChildFocus(popUpView.findViewById(POP_UP_TIME_IDS[i]), popUpView.findViewById(POP_UP_TIME_IDS[i]));
                break;
            }
            i++;
        }
    }

    private void listenersmenutime(PopupWindow popupWindow, View popUpView) {
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        for(int i=1;i<51;i++){numbers.add(i);}
        for (var id : POP_UP_TIME_IDS) {
            for(Integer n:numbers)
            {
                popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unchecktime(n, popUpView,id);
                    }
                });
                break;
            }
            numbers.remove(number);
            number=number+1;
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d("tak","tak");
                requireView().findViewById(R.id.Timeview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
                ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.arrowblack);
            }
        });

    }

    private void unchecktime(Integer n, View popUpView,Integer BackgroundId) {
        for (var id : POP_UP_TIME_IDS)
        {
            popUpView.findViewById(id).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        for (var id : POP_UP_TIME_TEXTIDS)
        {

            ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#000000"));
        }
        popUpView.findViewById(BackgroundId).setBackgroundColor(Color.parseColor("#693D2B"));
        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[n-1])).setTextColor(Color.parseColor("#FFFFFF"));
        String text=(((TextView)popUpView.findViewById(POP_UP_TIME_TEXTIDS[n-1])).getText().toString());
        ((TextView)(requireView().findViewById(R.id.textView18))).setText(text);
    }

    //method for highlight checked constrainlayout when pop_up starts
    private void whichOneCheckedDays(View popUpView) {
        Integer i=0;
        for(var id:POP_UP_DAYS_TEXTIDS){
            if(((TextView)requireView().findViewById(R.id.textView17)).getText().toString().equals(((TextView)popUpView.findViewById(id)).getText().toString())) {
                ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#FFFFFF"));
                popUpView.findViewById(POP_UP_DAYS_IDS[i]).setBackgroundColor(Color.parseColor("#693D2B"));
                if(i==0){popUpView.findViewById(R.id.jeden).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_up);}
                if(i==6){popUpView.findViewById(R.id.siedem).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_down);}
                break;
            }
            i++;
        }
    }

    private void listenersmenudays(PopupWindow popupWindow, View popUpView) {

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                requireView().findViewById(R.id.Dayview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
                ((ImageView)requireView().findViewById(R.id.imageView7)).setImageResource(R.drawable.arrowblack);
            }
        });
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        for(var n:NUMBERSDAYS){numbers.add(n);}
        for (var id : POP_UP_DAYS_IDS) {
            for(Integer n:numbers)
            {
                popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uncheckdays(n, popUpView);
                    }
                });
                break;
            }
            numbers.remove(number);
            number=number+1;
        }
    }
    //method for unchecking not checked constrainlayouts and checking the one clicked
    private void uncheckdays(Integer checked, View popUpView) {
        for(var id:POP_UP_DAYS_IDS) {
            popUpView.findViewById(id).setBackgroundColor(Color.parseColor("#FFFFFF"));
            if (id == POP_UP_DAYS_IDS[0]) {popUpView.findViewById(R.id.jeden).setBackgroundResource(R.drawable.menu_drop_out_list_shape_up);}
            if (id == POP_UP_DAYS_IDS[6]) {popUpView.findViewById(R.id.siedem).setBackgroundResource(R.drawable.menu_drop_out_list_shape_down);}
        }
        for(var id:POP_UP_DAYS_TEXTIDS) {
            ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#000000"));
        }
        ((TextView)(requireView().findViewById(R.id.textView17))).setText(((TextView)popUpView.findViewById(POP_UP_DAYS_TEXTIDS[checked-1])).getText().toString());
        ((TextView) popUpView.findViewById(POP_UP_DAYS_TEXTIDS[checked-1])).setTextColor(Color.parseColor("#FFFFFF"));
        popUpView.findViewById(POP_UP_DAYS_IDS[checked-1]).setBackgroundColor(Color.parseColor("#693D2B"));
        if(checked==1){popUpView.findViewById(R.id.jeden).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_up);}
        if(checked==7){popUpView.findViewById(R.id.siedem).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_down);}
    }

    private void arrowExpandersListeners()
>>>>>>> Stashed changes:app/src/main/java/com/overmighties/pubber/feature/search/FilterFragment.java
    {
        requireView().findViewById(R.id.MoreBeers).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.IVrating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< Updated upstream:app/src/main/java/com/overmighties/pubber/ui/FiltrationFragment.java
                if(((Slider)requireView().findViewById(R.id.rangeRating)).isShown())
                {
                    ((Slider)requireView().findViewById(R.id.rangeRating)).setVisibility(View.GONE);
                }
                else
                {
                    ((Slider)requireView().findViewById(R.id.rangeRating)).setVisibility(View.VISIBLE);
=======

                if ((requireView().findViewById(R.id.RatingSlider)).isShown()) {
                    (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.GONE);
                    ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.arrowwhite);
                } else {
                    (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.VISIBLE);
                    ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.arrowwhiteup);
>>>>>>> Stashed changes:app/src/main/java/com/overmighties/pubber/feature/search/FilterFragment.java
                }
            }
        });

        requireView().findViewById(R.id.IVdistance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< Updated upstream:app/src/main/java/com/overmighties/pubber/ui/FiltrationFragment.java
                if(((Slider)requireView().findViewById(R.id.odleglosc)).isShown())
                {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.GONE);
                }
                else
                {
                    ((Slider)requireView().findViewById(R.id.odleglosc)).setVisibility(View.VISIBLE);
                }
            }
        });
        requireView().findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.cena)).isShown())
=======
                if(((Slider)requireView().findViewById(R.id.DistanceSlider)).isShown())
>>>>>>> Stashed changes:app/src/main/java/com/overmighties/pubber/feature/search/FilterFragment.java
                {
                    ((Slider)requireView().findViewById(R.id.DistanceSlider)).setVisibility(View.GONE);

                    ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.arrowwhite);


                }
                else
                {
                    ((Slider)requireView().findViewById(R.id.DistanceSlider)).setVisibility(View.VISIBLE);

                    ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.arrowwhiteup);

                }
            }
        });
        requireView().findViewById(R.id.IVprice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.PriceChG)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.PriceChG)).setVisibility(View.GONE);

                    ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.arrowwhite);

                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.PriceChG)).setVisibility(View.VISIBLE);

                    ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.arrowwhiteup);
                }
            }
        });

        requireView().findViewById(R.id.IVtime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Chip)requireView().findViewById(R.id.dowolne)).isShown())
                {
                    ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.arrowwhite);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setVisibility(View.GONE);
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setVisibility(View.GONE);
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setVisibility(View.GONE);
                    ((ConstraintLayout)requireView().findViewById(R.id.NiestandardoweLayout)).setVisibility(View.GONE);

                }
                else
                {
                    ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.arrowwhiteup);
                    ((Chip)requireView().findViewById(R.id.dowolne)).setVisibility(View.VISIBLE);
                    ((Chip)requireView().findViewById(R.id.niestandardowe)).setVisibility(View.VISIBLE);
                    ((Chip)requireView().findViewById(R.id.czyotwarte)).setVisibility(View.VISIBLE);
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
                    ((ConstraintLayout)requireView().findViewById(R.id.NiestandardoweLayout)).setVisibility(View.VISIBLE);
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
                    ((ConstraintLayout)requireView().findViewById(R.id.NiestandardoweLayout)).setVisibility(View.GONE);
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
                    ((ConstraintLayout)requireView().findViewById(R.id.NiestandardoweLayout)).setVisibility(View.GONE);
                }
                else {((Chip)requireView().findViewById(R.id.dowolne)).setChecked(true);}
            }
        });

        requireView().findViewById(R.id.IVbeer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.BeerListChG)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).setVisibility(View.GONE);
                    ((TextView)requireView().findViewById(R.id.MoreBeers)).setVisibility(View.GONE);
                    ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.arrowwhite);
                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).setVisibility(View.VISIBLE);
                    ((TextView)requireView().findViewById(R.id.MoreBeers)).setVisibility(View.VISIBLE);
                    ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.arrowwhiteup);
                }
            }
        });
        requireView().findViewById(R.id.IVdrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ChipGroup)requireView().findViewById(R.id.DrinksChG)).isShown())
                {
                    ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).setVisibility(View.GONE);
                    ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.arrowwhite);

                }
                else
                {
                    ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).setVisibility(View.VISIBLE);
                    ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.arrowwhiteup);

                }
            }
        });
        requireView().findViewById(R.id.buttonreset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).clearCheck();
                ((ChipGroup)requireView().findViewById(R.id.PriceChG)).clearCheck();
                ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).clearCheck();
                ((Chip)requireView().findViewById(R.id.dowolne)).performClick();
                ((RangeSlider)requireView().findViewById(R.id.RatingSlider)).setValues(0f,5f);
                ((Slider)requireView().findViewById(R.id.DistanceSlider)).setValue(5f);
            }
        });
    }

    public void filtration(View view)
    {
<<<<<<< Updated upstream:app/src/main/java/com/overmighties/pubber/ui/FiltrationFragment.java
        RangeSlider r=view.findViewById(R.id.rangeRating);
        Slider s=view.findViewById(R.id.odleglosc);
=======
        RangeSlider averageRatingSlider=view.findViewById(R.id.RatingSlider);
        Slider distanceSlider=view.findViewById(R.id.DistanceSlider);
>>>>>>> Stashed changes:app/src/main/java/com/overmighties/pubber/feature/search/FilterFragment.java
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


}