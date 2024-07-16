package com.overmighties.pubber.feature.search;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.DAY_OF_WEEK;
import static com.overmighties.pubber.app.Constants.DRINKS_VIEW_ID_LIST;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_TEXT_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXT_IDS;


import android.os.Bundle;
import android.util.Log;
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
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.feature.search.data.FilterConstants;


import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DimensionsConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FilterFragment extends Fragment {

    public static final String TAG="FilterFragment";
    public boolean moreBeers;
    private final List<String> otherDrinks =new ArrayList<>();
    private final List<String> beers =new ArrayList<>();
    private boolean open;
    public String price= FilterConstants.NONE_PRICE;
    private PubListViewModel pubListViewModel;
    private View popUpView;
    private PopupWindow popupWindow;

    //timePopUpState[0] = 1 none is checked = 0 from is visible
    //timePopUpState[1] = n n determines id of from TextView
    //timePopUpState[2] = n n determines id of to TextView
    private final int[] timePopUpState =new int[]{1,0,0};
    private NavController navController;
    private TextView fromTV;
    private TextView toTV;

    public FilterFragment()
    {
        super(R.layout.filter);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        var nav_bar=requireActivity().findViewById(R.id.bottom_nav_view);
        requireView().findViewById(R.id.buttonfiltr).setOnClickListener(v->{
            filtration(requireView());
            Navigation.findNavController(v).navigate(FilterFragmentDirections.actionFilterToSearcher());
        });

        pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);
        NavigationBar.smoothHide(nav_bar);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        setUpChips();
        arrowExpandersListeners();
        dropDownMenusListener();
        setUpRangeSliders();
        requireView().findViewById(R.id.close_filter).setOnClickListener(v -> navController.navigate(FilterFragmentDirections.actionFilterToSearcher()));
    }

    private void setUpChips(){

    }


    private void arrowExpandersListeners()
    {
        requireView().findViewById(R.id.MoreBeers).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.IVrating).setOnClickListener(v -> {
            if ((requireView().findViewById(R.id.RatingSlider)).isShown()) {
                (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.GONE);
                ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.ic_expand_more_primary);
            } else {
                (requireView().findViewById(R.id.RatingSlider)).setVisibility(View.VISIBLE);
                ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });

        requireView().findViewById(R.id.IVdistance).setOnClickListener(v -> {
            if(requireView().findViewById(R.id.DistanceSlider).isShown()) {
                requireView().findViewById(R.id.DistanceSlider).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else {
                requireView().findViewById(R.id.DistanceSlider).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.IVprice).setOnClickListener(v -> {
            if(requireView().findViewById(R.id.PriceChG).isShown())
            {
                requireView().findViewById(R.id.PriceChG).setVisibility(View.GONE);

                ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                requireView().findViewById(R.id.PriceChG).setVisibility(View.VISIBLE);

                ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });

        requireView().findViewById(R.id.IVtime).setOnClickListener(v -> {
            if(requireView().findViewById(R.id.Chip_anytime).isShown())
            {
                ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.ic_expand_more_primary);
                requireView().findViewById(R.id.Chip_anytime).setVisibility(View.GONE);
                requireView().findViewById(R.id.Chip_custom).setVisibility(View.GONE);
                requireView().findViewById(R.id.Chip_open_now).setVisibility(View.GONE);
                requireView().findViewById(R.id.Layout_custom).setVisibility(View.GONE);

            }
            else
            {
                ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.ic_expand_less_primary);
                requireView().findViewById(R.id.Chip_anytime).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.Chip_custom).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.Chip_open_now).setVisibility(View.VISIBLE);
            }
        });
        requireView().findViewById(R.id.Chip_custom).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_custom)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(false);
                ((ConstraintLayout)requireView().findViewById(R.id.Layout_custom)).setVisibility(View.VISIBLE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(true);}
        });
        requireView().findViewById(R.id.Chip_open_now).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_open_now)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(false);
                ((ConstraintLayout)requireView().findViewById(R.id.Layout_custom)).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(true);}
        });
        requireView().findViewById(R.id.Chip_anytime).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_anytime)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(false);
                ((ConstraintLayout)requireView().findViewById(R.id.Layout_custom)).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(true);}
        });

        requireView().findViewById(R.id.IVbeer).setOnClickListener(v -> {
            if(((ChipGroup)requireView().findViewById(R.id.BeerListChG)).isShown())
            {
                ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).setVisibility(View.GONE);
                ((TextView)requireView().findViewById(R.id.MoreBeers)).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else
            {
                ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).setVisibility(View.VISIBLE);
                ((TextView)requireView().findViewById(R.id.MoreBeers)).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.IVdrinks).setOnClickListener(v -> {
            if(((ChipGroup)requireView().findViewById(R.id.DrinksChG)).isShown())
            {
                ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });
        requireView().findViewById(R.id.buttonreset).setOnClickListener(v -> {
            ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.PriceChG)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).clearCheck();
            requireView().findViewById(R.id.Chip_anytime).performClick();
            ((RangeSlider)requireView().findViewById(R.id.RatingSlider)).setValues(0f,5f);
            ((Slider)requireView().findViewById(R.id.DistanceSlider)).setValue(5f);
        });
    }



    private void dropDownMenusListener()
    {

        requireView().findViewById(R.id.Dayview).setOnClickListener(v -> {
            //Down Menus days in FilterFragment
            popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.menu_pop_up_days, null);
            popupWindow = new PopupWindow(popUpView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            //Listeners For popup days in filtrarion screen
            listenersMenuDays(popupWindow, popUpView);
            //cheking which one is selected to highlight it
            whichOneCheckedDays(popUpView);
            popupWindow.showAsDropDown(requireView().findViewById(R.id.Dayview),0,0,Gravity.BOTTOM);
            requireView().findViewById(R.id.Dayview).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
            ((ImageView)requireView().findViewById(R.id.imageView7)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
        });
        requireView().findViewById(R.id.Timeview).setOnClickListener(v -> {
            popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.menu_pop_up_time, null);
            popupWindow = new PopupWindow(popUpView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            //Listeners For popup time in filtrarion screen
            listenersmenutime();
            //cheking which one is selected to highlight it
            whichOneCheckedTime();
            setUpNextDayTV();
            popupWindow.showAsDropDown(requireView().findViewById(R.id.Timeview),0,0,Gravity.BOTTOM);
            ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
            ((View)requireView().findViewById(R.id.Timeview)).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
        });
    }

    private void whichOneCheckedTime() {
        if(((TextView) (requireView().findViewById(R.id.textView18))).getText().toString().equals(getString(R.string.anytime)))
        {
            popUpView.findViewById(POP_UP_TIME_IDS[55]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
        }
        else{
            popUpView.findViewById(POP_UP_TIME_IDS[timePopUpState[1]]).performClick();
            popUpView.findViewById(POP_UP_TIME_IDS[timePopUpState[2]-1]).performClick();
            popUpView.findViewById(POP_UP_TIME_IDS[timePopUpState[2]-1]).getParent().requestChildFocus(popUpView.findViewById(POP_UP_TIME_IDS[timePopUpState[2]-1]),
                    popUpView.findViewById(POP_UP_TIME_IDS[timePopUpState[2]-1]));
        }

    }


    private void checkTime(Integer n) {
        if (n - 1 == 55) {
            uncheckTime(popUpView, true);
            popUpView.findViewById(POP_UP_TIME_IDS[55]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
            String text = (((TextView) popUpView.findViewById(POP_UP_TIME_TEXT_IDS[55])).getText().toString());
            ((TextView) (requireView().findViewById(R.id.textView18))).setText(text);
        }
        else {
            if (timePopUpState[1] != 0 && timePopUpState[2] != 0){
                uncheckTime(popUpView, true);
            }
            if (timePopUpState[0] == 1) {
                uncheckTime(popUpView, false);
                popUpView.findViewById(POP_UP_TIME_IDS[n - 1]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                fromTV = new TextView(requireContext());
                fromTV.setText(getString(R.string.from));
                fromTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
                fromTV.setTextSize(16);
                fromTV.setId(View.generateViewId());
                ((ConstraintLayout) popUpView.findViewById(POP_UP_TIME_IDS[n - 1])).addView(fromTV);
                setMargins(popUpView.findViewById(POP_UP_TIME_TEXT_IDS[n - 1]), true, popUpView.findViewById(POP_UP_TIME_IDS[n - 1]));
                String text = getString(R.string.from) + " " +(((TextView) popUpView.findViewById(POP_UP_TIME_TEXT_IDS[n - 1])).getText().toString());
                ((TextView) (requireView().findViewById(R.id.textView18))).setText(text);
                timePopUpState[0] = 0;
                timePopUpState[1] = n - 1;

            }
            else {
                for (int h = timePopUpState[1]; h < n; h++) {
                    if (h != n - 1) {
                        popUpView.findViewById(POP_UP_TIME_IDS[h]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                    }
                    else {
                        toTV = new TextView(requireContext());
                        toTV.setText(getString(R.string.to));
                        toTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
                        toTV.setTextSize(16);
                        toTV.setId(View.generateViewId());
                        ((ConstraintLayout) popUpView.findViewById(POP_UP_TIME_IDS[h])).addView(toTV);
                        setMargins(popUpView.findViewById(POP_UP_TIME_TEXT_IDS[h]), true, popUpView.findViewById(POP_UP_TIME_IDS[h]));
                        popUpView.findViewById(POP_UP_TIME_IDS[h]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                        ((TextView) (requireView().findViewById(R.id.textView18))).setText(((TextView) (requireView().findViewById(R.id.textView18))).getText() + " "
                                + " " +getString(R.string.to) + " " + ((TextView) popUpView.findViewById(POP_UP_TIME_TEXT_IDS[h])).getText());
                        ((TextView) (requireView().findViewById(R.id.textView18))).setTextSize(14);
                    }

                }
                if (timePopUpState[1] >= n){
                    uncheckTime(popUpView, true);
                }
                else{
                    timePopUpState[2] = n;
                }
            }
        }
    }
    private void listenersmenutime() {
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        //CO TO JEST???!!! nie uzwaj takich nie wiadomo skad liczb i stringow
        for(int i=1;i<57;i++)
            numbers.add(i);
        for (var id : POP_UP_TIME_IDS) {
            for(Integer n:numbers) {
                popUpView.findViewById(id).setOnClickListener(v -> {
                    checkTime(n);
                });
                break;
            }
            numbers.remove(number);
            number=number+1;
        }

        popupWindow.setOnDismissListener(() -> {
            requireView().findViewById(R.id.Timeview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
            ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
        });

    }

    private void uncheckTime(View popUpView, boolean uncheck_all) {
        for (var id : POP_UP_TIME_IDS) {
            popUpView.findViewById(id).setBackgroundResource(R.drawable.menu_drop_down_days_shape);
        }
        int n = 0;
        for (var id : POP_UP_TIME_TEXT_IDS) {
            setMargins(popUpView.findViewById(id), false, popUpView.findViewById(POP_UP_TIME_IDS[n]));
            n += 1;
        }
        if (uncheck_all){
            if (fromTV != null)
                ((ConstraintLayout)fromTV.getParent()).removeView(fromTV);
                fromTV = null;
            if (toTV != null)
                ((ConstraintLayout)toTV.getParent()).removeView(toTV);
                toTV = null;
            timePopUpState[1] = 0;
            timePopUpState[2] = 0;
            timePopUpState[0] = 1;
        }
    }

    public void setMargins (TextView Time, boolean checked, ConstraintLayout constraintLayout) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if (checked){
            TextView TV = new TextView(requireContext());
            if (toTV == null){TV = fromTV;}
            else{TV = toTV;}
            constraintSet.connect(TV.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.connect(TV.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(TV.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) DimensionsConverter.pxFromDp(requireContext(), 8));
            constraintSet.applyTo(constraintLayout);
            constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(Time.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.connect(Time.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(Time.getId(), ConstraintSet.START, TV.getId(), ConstraintSet.END, (int) DimensionsConverter.pxFromDp(requireContext(), 8));
            constraintSet.applyTo(constraintLayout);
        }
        else{
            constraintSet.connect(Time.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.connect(Time.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(Time.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) DimensionsConverter.pxFromDp(requireContext(), 8));
            constraintSet.applyTo(constraintLayout);
        }

    }

    //method for highlight checked constrainlayout when pop_up starts
    private void whichOneCheckedDays(View popUpView) {
        int i=0;
        for(var id:POP_UP_DAYS_TEXT_IDS){
            if(((TextView)requireView().findViewById(R.id.textView17)).getText().toString().equals(((TextView)popUpView.findViewById(id)).getText().toString())) {
                ((TextView) popUpView.findViewById(id)).setTextColor(getResources().getColor(R.color.on_surface_variant));
                popUpView.findViewById(POP_UP_DAYS_IDS[i]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                if(i==0){popUpView.findViewById(R.id.cl_mon).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_up);}
                if(i==6){popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_down);}
                break;
            }
            i++;
        }
    }

    private void listenersMenuDays(PopupWindow popupWindow, View popUpView) {

        popupWindow.setOnDismissListener(() -> {
            requireView().findViewById(R.id.Dayview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
            ((ImageView)requireView().findViewById(R.id.imageView7)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
        });
        Integer number=1;
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(DAY_OF_WEEK));
        for (var id : POP_UP_DAYS_IDS) {
            for(Integer n:numbers)
            {
                popUpView.findViewById(id).setOnClickListener(v -> uncheckDays(n, popUpView));
                break;
            }
            numbers.remove(number);
            number+=1;
        }
    }
    //Method for unchecking not checked constraintlayouts and checking the one clicked
    private void uncheckDays(Integer checked, View popUpView) {
        for(var id:POP_UP_DAYS_IDS) {
            popUpView.findViewById(id).setBackgroundResource(R.drawable.menu_drop_down_days_shape);
            if (id == POP_UP_DAYS_IDS[0]) {popUpView.findViewById(R.id.cl_mon).setBackgroundResource(R.drawable.menu_drop_out_list_shape_up);}
            if (id == POP_UP_DAYS_IDS[6]) {popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_down);}
        }
        for(var id:POP_UP_DAYS_TEXT_IDS) {
            ((TextView) popUpView.findViewById(id)).setTextColor(getResources().getColor(R.color.on_surface_variant));
        }
        ((TextView)(requireView().findViewById(R.id.textView17))).setText(((TextView)popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checked-1])).getText().toString());
        ((TextView) popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checked-1])).setTextColor(getResources().getColor(R.color.on_surface_variant));
        popUpView.findViewById(POP_UP_DAYS_IDS[checked-1]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
        if(checked==1){popUpView.findViewById(R.id.cl_mon).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_up);}
        if(checked==7){popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_down);}
    }


    private void setUpRangeSliders()
    {
        RangeSlider ratingslider = requireView().findViewById(R.id.RatingSlider);
        ratingslider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                //It is just an example
                float n = Math.round(value);
                value = n/2;
                return String.format(Locale.US, "%.1f", value);
            }
        });

        Slider distanceslider = requireView().findViewById(R.id.DistanceSlider);
        distanceslider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                //It is just an example
                float n = Math.round(value);
                value = n/2;
                return String.format(Locale.US, "%.1f", value) + " km";
            }
        });
    }

    private void setUpNextDayTV() {
        for(int n = 49; n < 55; n++){
            TextView nextday = new TextView(requireContext());
            nextday.setText(getString(R.string.next_day));
            nextday.setTextSize(10);
            nextday.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
            nextday.setId(View.generateViewId());
            ((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[n])).addView(nextday);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[n]));
            constraintSet.connect(nextday.getId(), ConstraintSet.START, POP_UP_TIME_TEXT_IDS[n], ConstraintSet.END, (int) DimensionsConverter.pxFromDp(requireContext(), 4));
            constraintSet.connect(nextday.getId(), ConstraintSet.TOP, POP_UP_TIME_IDS[n], ConstraintSet.TOP);
            constraintSet.connect(nextday.getId(), ConstraintSet.BOTTOM, POP_UP_TIME_IDS[n], ConstraintSet.BOTTOM);
            constraintSet.applyTo((ConstraintLayout)popUpView.findViewById(POP_UP_TIME_IDS[n]));
        }
    }

    public void filtration(View view)
    {

        RangeSlider averageRatingSlider=view.findViewById(R.id.RatingSlider);
        float upAvgRating = Math.round(averageRatingSlider.getValues().get(1));
        upAvgRating = upAvgRating/2;
        float bottomAvgRating = Math.round(averageRatingSlider.getValues().get(0));
        bottomAvgRating = bottomAvgRating/2;
        Slider distanceSlider=view.findViewById(R.id.DistanceSlider);
        float distance = Math.round(distanceSlider.getValue());
        distance = distance/2;


        //jakiebrowary
        breweriesCheck(view);
        //drinki
        drinksCheck(view);
        //cena
        priceCheck(view);
        isOpen(view);
        FilterUiState filterUiState =new FilterUiState( upAvgRating, bottomAvgRating
                ,distance,open
                ,null, false, PriceType.getByIcon(price),beers,otherDrinks);

        pubListViewModel.filter(filterUiState);
    }


    public void isOpen(View v) {
        open = ((Chip) v.findViewById(R.id.Chip_open_now)).isChecked();
    }

    private void priceCheck(View v){
        if(((Chip) v.findViewById(R.id.malo)).isChecked())
            price= FilterConstants.MALO;
        if(((Chip) v.findViewById(R.id.srednio)).isChecked())
            price= FilterConstants.SREDNIO;
        if(((Chip) v.findViewById(R.id.duzo)).isChecked())
            price= FilterConstants.DUZO;
    }

    private void  drinksCheck(View v){
        for(var sid:DRINKS_VIEW_ID_LIST) {
            try {
                int field = R.id.class.getField(sid).getInt(0);
                if (((Chip) v.findViewById(field)).isChecked())
                    otherDrinks.add(String.valueOf(((Chip) v.findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }


    private void breweriesCheck(View v)  {
        for(var sid:BREWERIES_VIEW_ID_LIST) {
            try{
                int field = R.id.class.getField(sid).getInt(0);
                if(((Chip)v.findViewById(field)).isChecked())
                    beers.add(String.valueOf(((Chip)v.findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }
    public void moreBeers(View v)  {
        if (!moreBeers) {
            ((TextView)v.findViewById(R.id.MoreBeers)).setText("Mniej");
            try{
                for(var sid:BREWERIES_VIEW_ID_LIST) {
                    int field = R.id.class.getField(sid).getInt(0);
                    v.findViewById(field).setVisibility(View.VISIBLE);
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
            moreBeers = true;
        }
        else {
            ((TextView)v.findViewById(R.id.MoreBeers)).setText("Więcej");
            try {
                for (var sid : BREWERIES_VIEW_ID_LIST) {
                    int field = R.id.class.getField(sid).getInt(0);
                    v.findViewById(field).setVisibility(View.GONE);
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
            moreBeers = false;
        }
    }


}