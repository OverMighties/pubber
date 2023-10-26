package com.overmighties.pubber.feature.search;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.DRINKS_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.NUMBERSDAYS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_TEXTIDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXTIDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXTOdIDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXT_NEXT_DAYS;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.data.FilterConstants;


import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.util.PriceType;

import java.sql.Time;
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
    private View popUpView;
    private PopupWindow popupWindow;
    private int[] TimePopUpState=new int[]{1,0,0};


    public FilterFragment()
    {
        super(R.layout.filter);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        var nav_bar=getActivity().findViewById(R.id.nav_view);
        requireView().findViewById(R.id.buttonfiltr).setOnClickListener(v->{
            filtration(requireView());
            Navigation.findNavController(v).navigate(FilterFragmentDirections.actionFilterToSearcher());
        });

        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        NavigationBar.smoothHide(nav_bar);

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
        if(((TextView) (requireView().findViewById(R.id.textView18))).getText().toString().equals("Dowolna godzina"))
        {
            popUpView.findViewById(POP_UP_TIME_IDS[55]).setBackgroundColor(Color.parseColor("#693D2B"));
            ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[55])).setTextColor(Color.parseColor("#FFFFFF"));
        }
        else{
            ((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[TimePopUpState[1]])).performClick();
            ((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[TimePopUpState[2]-1])).performClick();
            ((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[TimePopUpState[2]-1])).getParent().requestChildFocus(((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[TimePopUpState[2]-1])),
                    ((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[TimePopUpState[2]-1])));
        }

    }

    private void listenersmenutime(PopupWindow popupWindow, View popUpView) {
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        for(int i=1;i<57;i++){numbers.add(i);}
        for (var id : POP_UP_TIME_IDS) {
            for(Integer n:numbers)
            {
                popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checktime(n, popUpView);
                        if(TimePopUpState[0]==0){
                            TimePopUpState[0]=1;
                        }else{
                            if(TimePopUpState[0]==2){
                                TimePopUpState[0]=1;}
                            else {TimePopUpState[0]=0;}

                        }


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
                requireView().findViewById(R.id.Timeview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
                ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.arrowblack);
            }
        });

    }

    private void checktime(Integer n, View popUpView) {
        if(TimePopUpState[0]==1) {
            if(n-1!=55) {
                unchecktime(popUpView);
                popUpView.findViewById(POP_UP_TIME_IDS[n - 1]).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[n - 1])).setTextColor(Color.parseColor("#FFFFFF"));
                setMargins(popUpView.findViewById(POP_UP_TIME_TEXTIDS[n - 1]), viewModel.dpToPx(4), viewModel.dpToPx(8), 0, 0);
                ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTOdIDS[n - 1])).setText("Od");
                popUpView.findViewById(POP_UP_TIME_TEXTOdIDS[n - 1]).setVisibility(View.VISIBLE);
                if (n >= 50) {
                    ((TextView) popUpView.findViewById(POP_UP_TIME_TEXT_NEXT_DAYS[n - 50])).setTextColor(Color.parseColor("#FFFFFF"));
                }

                String text = "Od "+(((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[n - 1])).getText().toString());
                ((TextView) (requireView().findViewById(R.id.textView18))).setText(text);

                TimePopUpState[1] = n - 1;
            }else{
                unchecktime(popUpView);
                popUpView.findViewById(POP_UP_TIME_IDS[55]).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[55])).setTextColor(Color.parseColor("#FFFFFF"));
                String text =(((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[55])).getText().toString());
                ((TextView) (requireView().findViewById(R.id.textView18))).setText(text);
                TimePopUpState[0]=2;
            }
        }else{
            if(n-1!=55){
                for(int h=TimePopUpState[1];h<n;h++){
                    if (h >= 49) {
                        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXT_NEXT_DAYS[h-49])).setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    if(h!=n-1){
                        popUpView.findViewById(POP_UP_TIME_IDS[h]).setBackgroundColor(Color.parseColor("#693D2B"));
                        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[h])).setTextColor(Color.parseColor("#FFFFFF"));
                    }else{
                        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTOdIDS[h])).setText("Do");
                        popUpView.findViewById(POP_UP_TIME_TEXTOdIDS[h]).setVisibility(View.VISIBLE);
                        setMargins(popUpView.findViewById(POP_UP_TIME_TEXTIDS[h]), viewModel.dpToPx(4), viewModel.dpToPx(8), 0, 0);
                        popUpView.findViewById(POP_UP_TIME_IDS[h]).setBackgroundColor(Color.parseColor("#693D2B"));
                        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[h])).setTextColor(Color.parseColor("#FFFFFF"));

                        ((TextView) (requireView().findViewById(R.id.textView18))).setText( ((TextView) (requireView().findViewById(R.id.textView18))).getText()+" Do "+
                                ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[h])).getText());
                        ((TextView) (requireView().findViewById(R.id.textView18))).setTextSize(14);
                    }

                }
                TimePopUpState[2]=n;
            }else{
                unchecktime(popUpView);
                popUpView.findViewById(POP_UP_TIME_IDS[55]).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[55])).setTextColor(Color.parseColor("#FFFFFF"));
                String text =(((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[55])).getText().toString());
                ((TextView) (requireView().findViewById(R.id.textView18))).setText(text);
                TimePopUpState[0]=2;
            }

        }


    }

    private void unchecktime(View popUpView) {
        for (var id : POP_UP_TIME_IDS) {
            popUpView.findViewById(id).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        for (var id : POP_UP_TIME_TEXTIDS) {
            setMargins(popUpView.findViewById(id), viewModel.dpToPx(8), viewModel.dpToPx(8), 0, 0);
            ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#000000"));
        }
        for (var id : POP_UP_TIME_TEXTOdIDS) {
            popUpView.findViewById(id).setVisibility(View.GONE);
        }
        for (var id : POP_UP_TIME_TEXT_NEXT_DAYS) {
            ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#000000"));
        }
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
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

    {
        requireView().findViewById(R.id.MoreBeers).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.IVrating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((requireView().findViewById(R.id.RatingSlider)).isShown()) {
                    (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.GONE);
                    ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.arrowwhite);
                } else {
                    (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.VISIBLE);
                    ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.arrowwhiteup);

                }
            }
        });

        requireView().findViewById(R.id.IVdistance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Slider)requireView().findViewById(R.id.DistanceSlider)).isShown())

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

        RangeSlider averageRatingSlider=view.findViewById(R.id.RatingSlider);
        Slider distanceSlider=view.findViewById(R.id.DistanceSlider);

        //jakiebrowary
        breweriesCheck(view);
        //drinki
        drinksCheck(view);
        //cena
        priceCheck(view);
        isOpen(view);
        FilterUiState filterUiState =new FilterUiState( averageRatingSlider.getValues().get(1),averageRatingSlider.getValues().get(0)
                ,distanceSlider.getValue(),open
                ,null, false, PriceType.getByIcon(price),beers,otherDrinks);

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