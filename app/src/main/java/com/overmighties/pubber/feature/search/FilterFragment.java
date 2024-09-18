package com.overmighties.pubber.feature.search;

import static com.overmighties.pubber.app.Constants.DAY_OF_WEEK;
import static com.overmighties.pubber.app.Constants.FILTER_FRAGMENT_BEER_WIDGETS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_TEXT_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_PAIRS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXT_IDS;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.core.util.Pair;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.designsystem.NavigationBar;
import com.overmighties.pubber.feature.search.data.FilterConstants;


import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.overmighties.pubber.feature.search.filterselect.FilterSelectViewModel;
import com.overmighties.pubber.feature.search.stateholders.FilterFragmentUiState;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.stateholders.ParticularBeersCardViewUiState;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.ResourceUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FilterFragment extends Fragment {

    public static final String TAG="FilterFragment";
    public boolean moreBeers;
    private final List<String> otherDrinks =new ArrayList<>();
    private final List<String> beers =new ArrayList<>();
    private final List<String> styles =new ArrayList<>();
    private final List<Pair<String, String>> particularBeers =new ArrayList<>();
    private final List<String> tags = new ArrayList<>();
    private boolean open;
    private FilterUiState.CustomOpeningHours customOpeningHours = new FilterUiState.CustomOpeningHours(null, null, null);
    public String price= FilterConstants.NONE_PRICE;
    private PubListViewModel pubListViewModel;
    private FilterSelectViewModel filterSelectViewModel;
    private View popUpView;
    private PopupWindow popupWindow;
    private ListParticularBeersAdapter listParticularBeersAdapter = null;
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
        filterSelectViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(FilterSelectViewModel.initializer)).get(FilterSelectViewModel.class);
        NavigationBar.smoothHide(nav_bar, 200);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        arrowExpandersListeners();
        dropDownMenusListener();
        setUpRangeSliders();
        prepareChips();
        if(pubListViewModel.getFilterUiState().getValue() != null)
            bringBackFilterSettings();
        requireView().findViewById(R.id.close_filter).setOnClickListener(v -> navController.navigate(FilterFragmentDirections.actionFilterToSearcher()));
    }


    private void arrowExpandersListeners()
    {
        requireView().findViewById(R.id.MoreBeers).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.IVrating).setOnClickListener(v -> {
            if (pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(0)) {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(0, false);
                requireView().findViewById(R.id.RatingSlider).setVisibility(View.GONE);
                ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.ic_expand_more_primary);
            } else {
                requireView().findViewById(R.id.RatingSlider).setVisibility(View.VISIBLE);
                ((ImageView) requireView().findViewById(R.id.IVrating)).setImageResource(R.drawable.ic_expand_less_primary);
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(0, true);
            }
        });

        requireView().findViewById(R.id.IVdistance).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(1)) {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(1, false);
                requireView().findViewById(R.id.DistanceSlider).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(1, true);
                requireView().findViewById(R.id.DistanceSlider).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVdistance)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.IVprice).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(2))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(2, false);
                requireView().findViewById(R.id.PriceChG).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(2, true);
                requireView().findViewById(R.id.PriceChG).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVprice)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });

        requireView().findViewById(R.id.IVtime).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(3))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(3, false);
                ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.ic_expand_more_primary);
                requireView().findViewById(R.id.Chip_anytime).setVisibility(View.GONE);
                requireView().findViewById(R.id.Chip_custom).setVisibility(View.GONE);
                requireView().findViewById(R.id.Chip_open_now).setVisibility(View.GONE);
                requireView().findViewById(R.id.Layout_custom).setVisibility(View.GONE);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(3, true);
                ((ImageView)requireView().findViewById(R.id.IVtime)).setImageResource(R.drawable.ic_expand_less_primary);
                requireView().findViewById(R.id.Chip_anytime).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.Chip_open_now).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.Chip_custom).setVisibility(View.VISIBLE);
                if(((Chip)requireView().findViewById(R.id.Chip_custom)).isChecked()){
                    ((ConstraintLayout)requireView().findViewById(R.id.Layout_custom)).setVisibility(View.VISIBLE);
                }
            }
        });
        requireView().findViewById(R.id.Chip_custom).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_custom)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(false);
                requireView().findViewById(R.id.Layout_custom).setVisibility(View.VISIBLE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(true);}
        });
        requireView().findViewById(R.id.Chip_open_now).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_open_now)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(false);
                requireView().findViewById(R.id.Layout_custom).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(true);}
        });
        requireView().findViewById(R.id.Chip_anytime).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.Chip_anytime)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.Chip_custom)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.Chip_open_now)).setChecked(false);
                requireView().findViewById(R.id.Layout_custom).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.Chip_anytime)).setChecked(true);}
        });

        requireView().findViewById(R.id.IVbeer).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(4)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(4, false);
                for(var id: FILTER_FRAGMENT_BEER_WIDGETS_IDS){
                    requireView().findViewById(id).setVisibility(View.GONE);
                }
                if (requireView().findViewById(R.id.BeerListChG).isShown())
                    requireView().findViewById(R.id.IVBreweries).performClick();
                if (requireView().findViewById(R.id.StylesListChG).isShown())
                    requireView().findViewById(R.id.IVStyles).performClick();
                if (requireView().findViewById(R.id.ParticularBeersList).isShown())
                    requireView().findViewById(R.id.IVParticularBeers).performClick();
                ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(4, true);
                for(var id: FILTER_FRAGMENT_BEER_WIDGETS_IDS){
                    requireView().findViewById(id).setVisibility(View.VISIBLE);
                }
                ((ImageView)requireView().findViewById(R.id.IVbeer)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.IVBreweries).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(5))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(5, false);
                requireView().findViewById(R.id.BeerListChG).setVisibility(View.GONE);
                requireView().findViewById(R.id.MoreBeers).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVBreweries)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(5, true);
                requireView().findViewById(R.id.BeerListChG).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.MoreBeers).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVBreweries)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });
        requireView().findViewById(R.id.IVStyles).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(6)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(6, false);
                requireView().findViewById(R.id.StylesListChG).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVStyles)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else{
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(6, true);
                requireView().findViewById(R.id.StylesListChG).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVStyles)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });
        requireView().findViewById(R.id.IVParticularBeers).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(7)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(7, false);
                requireView().findViewById(R.id.choose_brewery_cardview).setVisibility(View.GONE);
                requireView().findViewById(R.id.choose_style_cardview).setVisibility(View.GONE);
                requireView().findViewById(R.id.add_cardview).setVisibility(View.GONE);
                requireView().findViewById(R.id.ParticularBeersList).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVParticularBeers)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else{
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(7, true);
                requireView().findViewById(R.id.choose_brewery_cardview).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.choose_style_cardview).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.add_cardview).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.ParticularBeersList).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVParticularBeers)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });

        requireView().findViewById(R.id.choose_brewery_cardview).setOnClickListener(v->{
            pubListViewModel.setBrewery_textview(null);
            filterSelectViewModel.setDataType(FilterSelectViewModel.listDataType.Breweries);
            NavHostFragment.findNavController(this).navigate(FilterFragmentDirections.actionFilterFragmentToFilterSelectFragment());
        });
        requireView().findViewById(R.id.choose_style_cardview).setOnClickListener(v->{
            pubListViewModel.setStyle_textview(null);
            filterSelectViewModel.setDataType(FilterSelectViewModel.listDataType.Styles);
            NavHostFragment.findNavController(this).navigate(FilterFragmentDirections.actionFilterFragmentToFilterSelectFragment());
        });
        requireView().findViewById(R.id.add_cardview).setOnClickListener(v->{
            TextView brewery_tv = requireView().findViewById(R.id.choose_brewery_textview);
            TextView style_tv = requireView().findViewById(R.id.choose_style_textview);
            if(brewery_tv.getText().toString() != getString(R.string.choose_brewery) && style_tv.getText().toString() != getString(R.string.choose_style)) {
                if (listParticularBeersAdapter != null) {
                    listParticularBeersAdapter.addNextBeer(new ParticularBeersCardViewUiState(brewery_tv.getText().toString(), style_tv.getText().toString()));
                } else {
                    List<ParticularBeersCardViewUiState> list = new ArrayList<>();
                    list.add(new ParticularBeersCardViewUiState(brewery_tv.getText().toString(), style_tv.getText().toString()));
                    listParticularBeersAdapter = new ListParticularBeersAdapter(list);
                    ((RecyclerView) requireView().findViewById(R.id.ParticularBeersList)).setAdapter(listParticularBeersAdapter);
                }
                pubListViewModel.setStyle_textview(null);
                pubListViewModel.setBrewery_textview(null);
                brewery_tv.setText(getString(R.string.choose_brewery));
                style_tv.setText(getString(R.string.choose_style));
            }
        });
        requireView().findViewById(R.id.IVdrinks).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(8))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(8, false);
                requireView().findViewById(R.id.DrinksChG).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(8, true);
                requireView().findViewById(R.id.DrinksChG).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVdrinks)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });

        requireView().findViewById(R.id.IVTags).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(9))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(9, false);
                requireView().findViewById(R.id.TagsChG).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.IVTags)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(9, true);
                requireView().findViewById(R.id.TagsChG).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.IVTags)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });

        requireView().findViewById(R.id.buttonreset).setOnClickListener(v -> {
            ((ChipGroup)requireView().findViewById(R.id.DrinksChG)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.PriceChG)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.BeerListChG)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.TagsChG)).clearCheck();
            requireView().findViewById(R.id.Chip_anytime).performClick();
            ((RangeSlider)requireView().findViewById(R.id.RatingSlider)).setValues(0f,10f);
            ((Slider)requireView().findViewById(R.id.DistanceSlider)).setValue(10f);
            pubListViewModel.filter(new FilterUiState());
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
            //whichOneCheckedTime();
            setUpNextDayTV();
            popupWindow.showAsDropDown(requireView().findViewById(R.id.Timeview),0,0,Gravity.BOTTOM);
            ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
            ((View)requireView().findViewById(R.id.Timeview)).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
        });
    }

    private void listenersmenutime(){
        int n=0;
        for(var ids: POP_UP_TIME_PAIRS){
            int index = n;
            popUpView.findViewById(ids.first).setOnClickListener(v->{
                Pair<Integer, Integer> pairIndex = pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds();
                if(index == 0){
                    pubListViewModel.getFilterFragmentUiState().getValue().setPopUpTimeIds(new Pair<>(-1, -1));
                } else if (pairIndex.first == -1) {
                    pubListViewModel.getFilterFragmentUiState().getValue().setPopUpTimeIds(new Pair<>(index, -1));
                } else if (pairIndex.second == -1 && index>pairIndex.first){
                    pubListViewModel.getFilterFragmentUiState().getValue().setPopUpTimeIds(new Pair<>(pairIndex.first , index));
                } else {
                    pubListViewModel.getFilterFragmentUiState().getValue().setPopUpTimeIds( new Pair<>(index, -1));
                }
                checkTime(ids.first, ids.second);
                if(index==55)
                    popUpView.findViewById(R.id.constraintLayout5).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_down_highlight);

            });
            n+=1;
        }
        popupWindow.setOnDismissListener(()->{
            requireView().findViewById(R.id.Timeview).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
            ((ImageView)requireView().findViewById(R.id.imageView8)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
            if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second != -1){
                String text = getString(R.string.from)+" "+
                        ((TextView)popUpView.findViewById(POP_UP_TIME_TEXT_IDS[pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first])).getText().toString()
                                + " " + getString(R.string.to) + " " + ((TextView)popUpView.findViewById(POP_UP_TIME_TEXT_IDS[pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second])).getText().toString();
                ((TextView)requireView().findViewById(R.id.textView18)).setText(text);
            } else if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first == -1 ){
                ((TextView)requireView().findViewById(R.id.textView18)).setText(getString(R.string.anytime));
            }
        });
    }

    private void checkTime(Integer backgroundId, Integer textViewId) {
        if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second == -1){
            unCheckAll();
            popUpView.findViewById(backgroundId).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
            ((TextView)popUpView.findViewById(textViewId)).setTextColor(R.color.on_surface);
            if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first != -1) {
                fromTV = new TextView(requireContext());
                fromTV.setText(getString(R.string.from));
                fromTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
                fromTV.setTextSize(16);
                fromTV.setId(View.generateViewId());
                ((ConstraintLayout) popUpView.findViewById(backgroundId)).addView(fromTV);
                setMargins(popUpView.findViewById(textViewId), popUpView.findViewById(backgroundId), true);
            }
            else{
                popUpView.findViewById(R.id.constraintLayout2).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_up_highlight);
            }
        }
        else{
            for(int i=pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first;
                i<=pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second;i++){
                Pair<Integer, Integer> ids = POP_UP_TIME_PAIRS[i];
                popUpView.findViewById(ids.first).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                ((TextView)popUpView.findViewById(ids.second)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
            }
            toTV = new TextView(requireContext());
            toTV.setText(getString(R.string.to));
            toTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
            toTV.setTextSize(16);
            toTV.setId(View.generateViewId());
            ((ConstraintLayout) popUpView.findViewById(backgroundId)).addView(toTV);
            setMargins(popUpView.findViewById(textViewId),  popUpView.findViewById(backgroundId), true);
        }
    }

    private void unCheckAll() {
        for(var ids: POP_UP_TIME_PAIRS) {
             popUpView.findViewById(ids.first).setBackgroundResource(R.drawable.menu_drop_down_days_shape);
             ((TextView)popUpView.findViewById(ids.second)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
             setMargins(popUpView.findViewById(ids.second),  popUpView.findViewById(ids.first), false);
        }
        popUpView.findViewById(POP_UP_TIME_PAIRS[0].first).setBackground(null);
        popUpView.findViewById(POP_UP_TIME_PAIRS[55].first).setBackground(null);
        popUpView.findViewById(R.id.constraintLayout2).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_up);
        popUpView.findViewById(R.id.constraintLayout5).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_down);
        if (fromTV != null)
            ((ConstraintLayout)fromTV.getParent()).removeView(fromTV);
            fromTV = null;
        if (toTV != null)
            ((ConstraintLayout)toTV.getParent()).removeView(toTV);
            toTV = null;
    }

    public void setMargins (TextView Time, ConstraintLayout constraintLayout, boolean checked) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if (checked){
            TextView TV = new TextView(requireContext());
            if (toTV == null){
                TV = fromTV;
            }
            else{
                TV = toTV;
            }
            constraintSet.connect(TV.getId(), ConstraintSet.TOP, Time.getId(), ConstraintSet.TOP);
            constraintSet.connect(TV.getId(), ConstraintSet.BOTTOM, Time.getId(), ConstraintSet.BOTTOM);
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

    private void setUpNextDayTV() {
        for(int n = 50; n <= 55; n++){
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



    //method for highlight checked constrainlayout when pop_up starts
    private void whichOneCheckedDays(View popUpView) {
        int i=0;
        for(var id:POP_UP_DAYS_TEXT_IDS){
            if(((TextView)requireView().findViewById(R.id.textView17)).getText().toString().equals(((TextView)popUpView.findViewById(id)).getText().toString())) {
                ((TextView) popUpView.findViewById(id)).setTextColor(getResources().getColor(R.color.on_surface_variant));
                popUpView.findViewById(POP_UP_DAYS_IDS[i]).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
                if(i==0){popUpView.findViewById(R.id.cl_today).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_up);}
                if(i==7){popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_down);}
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

        for (var id : POP_UP_DAYS_IDS) {
            popUpView.findViewById(id).setOnClickListener(v -> uncheckDays(id, popUpView));
        }
    }
    //Method for unchecking not checked constraintlayouts and checking the one clicked
    private void uncheckDays(Integer checkedId, View popUpView) {
        Integer n = 0;
        Integer checkedIndex = 0;
        for(var id:POP_UP_DAYS_IDS) {
            //finding index of checked view
            if(id == checkedId){checkedIndex = n;}
            n+=1;
            popUpView.findViewById(id).setBackgroundResource(R.drawable.menu_drop_down_days_shape);
            if (id == POP_UP_DAYS_IDS[0]) {popUpView.findViewById(R.id.cl_today).setBackgroundResource(R.drawable.menu_drop_out_list_shape_up);}
            if (id == POP_UP_DAYS_IDS[7]) {popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_down);}
        }
        for(var id:POP_UP_DAYS_TEXT_IDS) {
            ((TextView) popUpView.findViewById(id)).setTextColor(getResources().getColor(R.color.on_surface_variant));
        }
        ((TextView)(requireView().findViewById(R.id.textView17))).setText(((TextView)popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checkedIndex])).getText().toString());
        ((TextView) popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checkedIndex])).setTextColor(getResources().getColor(R.color.on_surface_variant));
        popUpView.findViewById(checkedId).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
        if(checkedIndex==0){popUpView.findViewById(R.id.cl_today).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_up);}
        if(checkedIndex==7){popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_down);}
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
    public void prepareChips(){
        ChipGroup breweriesGroup = requireView().findViewById(R.id.BeerListChG);
        ChipGroup stylesGroup = requireView().findViewById(R.id.StylesListChG);
        ChipGroup drinksGroup = requireView().findViewById(R.id.DrinksChG);
        ChipGroup tagsGroup = requireView().findViewById(R.id.TagsChG);
        //breweries chips
        for(var brewery:getContext().getResources().getStringArray(R.array.all_breweries)){
            Chip chip = new Chip(getContext());
            chip.setText(brewery);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), brewery.replace(" ", "")));
            styleChip(chip);
            chip.setVisibility(View.GONE);
            breweriesGroup.addView(chip);
        }
        //make visible the most commonly known breweries
        for(var breweryId:getContext().getResources().getStringArray(R.array.first_to_show_breweries)){
            try {
                int field = R.id.class.getField(breweryId.replace(" ", "")).getInt(0);
                ((Chip)requireView().findViewById(field)).setVisibility(View.VISIBLE);
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "makeCommonBreweriesShown: Such View Field doesn't exit");
            }

        }
        //styles
        for(var style: getContext().getResources().getStringArray(R.array.beer_styles)){
            Chip chip = new Chip(getContext());
            chip.setText(style);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), style.replace(" ", "")));
            styleChip(chip);
            stylesGroup.addView(chip);
        }
        //drinks
        for(var cocktail:getContext().getResources().getStringArray(R.array.cocktails)){
            Chip chip = new Chip(getContext());
            chip.setText(cocktail);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), cocktail.replace(" ", "")));
            styleChip(chip);
            drinksGroup.addView(chip);
        }
        //tags
        for(var tag:getContext().getResources().getStringArray(R.array.pub_tags)){
            Chip chip = new Chip(getContext());
            chip.setText(tag);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), tag.replace(" ", "")));
            styleChip(chip);
            tagsGroup.addView(chip);
        }
    }

    private void styleChip(Chip chip){
        chip.setPadding(16, 0, 16, 0);
        chip.setChipCornerRadius(DimensionsConverter.pxFromDp(getContext(), 8));
        chip.setChipBackgroundColorResource(R.color.chip_filter_background_color);
        chip.setTextColor(getContext().getResources().getColor(R.color.on_surface_variant));
        chip.setTextSize(14);
        chip.setCheckable(true);
        chip.setCheckedIconVisible(true);
        chip.setCheckedIcon(getContext().getDrawable(R.drawable.ic_chip_checked_on_surface));
        chip.setChipStrokeColorResource(R.color.outline);
        chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(getContext(), 0.7F));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        chip.setLayoutParams(params);
    }

    private void bringBackFilterSettings(){
        //rating
        Float bottomAverageRating = FilterConstants.BASE_BOTTOM_RATING;
        Float uppperverageRating = FilterConstants.BASE_UPPER_RATING;
        if(pubListViewModel.getFilterUiState().getValue().getBottomAverageRating() != null ||
                pubListViewModel.getFilterUiState().getValue().getBottomAverageRating() != bottomAverageRating)
            bottomAverageRating = pubListViewModel.getFilterUiState().getValue().getBottomAverageRating();
        if(pubListViewModel.getFilterUiState().getValue().getUpperAverageRating() != uppperverageRating)
            uppperverageRating = pubListViewModel.getFilterUiState().getValue().getUpperAverageRating();
        ((RangeSlider)requireView().findViewById(R.id.RatingSlider)).setValues(bottomAverageRating*2, uppperverageRating*2);
        //distance
        if(pubListViewModel.getFilterUiState().getValue().getDistance() != null ||
                pubListViewModel.getFilterUiState().getValue().getDistance() != FilterUiState.NONE_DISTANCE)
            ((Slider)requireView().findViewById(R.id.DistanceSlider)).setValue(
                    pubListViewModel.getFilterUiState().getValue().getDistance()*2);
        //price
        if(pubListViewModel.getFilterUiState().getValue().getPriceType()!=null ||
                !pubListViewModel.getFilterUiState().getValue().getPriceType().equals(FilterConstants.NONE_PRICE)) {
            if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.SUPER_CHEAP)) {
                ((Chip) requireView().findViewById(R.id.malo)).setChecked(true);
            } else if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.CHEAP)) {
                ((Chip) requireView().findViewById(R.id.srednio)).setChecked(true);
            } else if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.AVERAGE)) {
                ((Chip) requireView().findViewById(R.id.duzo)).setChecked(true);
            }
        }
        //time
        if(pubListViewModel.getFilterUiState().getValue().getOpenNow()){
            requireView().findViewById(R.id.Chip_open_now).performClick();
        } else if (pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours() != null) {
            requireView().findViewById(R.id.Chip_custom).performClick();
            requireView().findViewById(R.id.Layout_custom).setVisibility(View.GONE);
            if(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getWeekDay() != null){
                ((TextView)requireView().findViewById(R.id.textView17)).setText(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getWeekDay());
            }
            if(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getFromTime() != null &&
                    pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getToTime() != null){
                ((TextView)requireView().findViewById(R.id.textView18)).setText(
                        getString(R.string.from)+" "+ pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getFromTime() +
                                " "+getString(R.string.to)+" "+pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getToTime());
            }
        } else{
            requireView().findViewById(R.id.Chip_anytime).performClick();
        }

        //breweries
        if(pubListViewModel.getFilterUiState().getValue().getBeers()!=null){
            List<String> beers = new ArrayList<>();
            for(int n=0; n<((ChipGroup)requireView().findViewById(R.id.BeerListChG)).getChildCount(); n++){
                beers.add(((Chip)((ChipGroup)requireView().findViewById(R.id.BeerListChG)).getChildAt(n)).getText().toString());
            }
            for(var beer:pubListViewModel.getFilterUiState().getValue().getBeers()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.BeerListChG)).getChildAt(beers.indexOf(beer))).setChecked(true);
            }
        }
        //styles
        if(pubListViewModel.getFilterUiState().getValue().getStyles() != null){
            List<String> styles = new ArrayList<>();
            for(int n=0; n<((ChipGroup)requireView().findViewById(R.id.StylesListChG)).getChildCount(); n++){
                styles.add(((Chip)((ChipGroup)requireView().findViewById(R.id.StylesListChG)).getChildAt(n)).getText().toString());
            }
            for(var style:pubListViewModel.getFilterUiState().getValue().getStyles()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.StylesListChG)).getChildAt(styles.indexOf(style))).setChecked(true);
            }
        }
        //particular beers
        if(pubListViewModel.getFilterUiState().getValue().getParticularBeers() != null){
            List<ParticularBeersCardViewUiState> particularBeers = new ArrayList<>();
            for(var particularBeer:pubListViewModel.getFilterUiState().getValue().getParticularBeers()){
                particularBeers.add(new ParticularBeersCardViewUiState(particularBeer.first, particularBeer.second));
            }
            if(listParticularBeersAdapter == null){
                listParticularBeersAdapter = new ListParticularBeersAdapter(particularBeers);
                ((RecyclerView) requireView().findViewById(R.id.ParticularBeersList)).setAdapter(listParticularBeersAdapter);
            }
        }
        //drinks
        if(pubListViewModel.getFilterUiState().getValue().getOtherDrinks() != null){
            List<String> drinks = new ArrayList<>();
            for(int n=0; n<((ChipGroup)requireView().findViewById(R.id.DrinksChG)).getChildCount(); n++){
                drinks.add(((Chip)((ChipGroup)requireView().findViewById(R.id.DrinksChG)).getChildAt(n)).getText().toString());
            }
            for(var drink:pubListViewModel.getFilterUiState().getValue().getOtherDrinks()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.DrinksChG)).getChildAt(drinks.indexOf(drink))).setChecked(true);
            }
        }
        //tags
        if(pubListViewModel.getFilterUiState().getValue().getTags() != null){
            List<String> tags = new ArrayList<>();
            for(int n=0; n<((ChipGroup)requireView().findViewById(R.id.TagsChG)).getChildCount(); n++){
                tags.add(((Chip)((ChipGroup)requireView().findViewById(R.id.TagsChG)).getChildAt(n)).getText().toString());
            }
            for(var tag:pubListViewModel.getFilterUiState().getValue().getTags()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.TagsChG)).getChildAt(tags.indexOf(tag))).setChecked(true);
            }
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

        beerCheck(view);
        drinksCheck(view);
        tagsCheck();
        priceCheck(view);
        timeCheck(view);
        FilterUiState filterUiState =new FilterUiState(
                upAvgRating, bottomAvgRating,
                distance,open, customOpeningHours,
                PriceType.getByIcon(price),beers,
                styles, particularBeers, otherDrinks, tags);

        pubListViewModel.filter(filterUiState);
    }


    public void timeCheck(View v) {
        open = ((Chip) v.findViewById(R.id.Chip_open_now)).isChecked();
        customOpeningHours = new FilterUiState.CustomOpeningHours(null, null, null);
        if(((Chip)requireView().findViewById(R.id.Chip_custom)).isChecked()){
            String[] time = String.valueOf(((TextView)requireView().findViewById(R.id.textView18)).getText()).toString().split(" ");
            if(time.length >= 4){
                customOpeningHours = new FilterUiState.CustomOpeningHours(
                        ((TextView)requireView().findViewById(R.id.textView17)).getText().toString(),
                        time[1],
                        time[3]
                );
            }
            if((((TextView)requireView().findViewById(R.id.textView18)).getText()).toString().equals(getString(R.string.anytime))){
                customOpeningHours = new FilterUiState.CustomOpeningHours(
                        ((TextView)requireView().findViewById(R.id.textView17)).getText().toString(),
                        null,
                        null
                );
            }
        }
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
        for(var sid:requireContext().getResources().getStringArray(R.array.cocktails)) {
            try {
                int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
                if (((Chip) v.findViewById(field)).isChecked())
                    otherDrinks.add(String.valueOf(((Chip) v.findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }


    private void beerCheck(View v)  {
        for(var sid:requireContext().getResources().getStringArray(R.array.all_breweries)) {
            try{
                int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
                if(((Chip)v.findViewById(field)).isChecked())
                    beers.add(String.valueOf(((Chip)v.findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
        for(var sid:requireContext().getResources().getStringArray(R.array.beer_styles)){
            try{
                int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
                if(((Chip)v.findViewById(field)).isChecked())
                    styles.add(String.valueOf(((Chip)v.findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
        RecyclerView particularBeerList = requireView().findViewById(R.id.ParticularBeersList);
        for (int i = 0; i < particularBeerList.getChildCount(); i++) {
            View childView = particularBeerList.getChildAt(i);
            String brewery_name = ((TextView)childView.findViewById(R.id.brewery_textview)).getText().toString();
            String style = ((TextView)childView.findViewById(R.id.style_textview)).getText().toString();
            particularBeers.add(new Pair<>(brewery_name, style));
        }
    }

    private void tagsCheck(){
        for(var sid:requireContext().getResources().getStringArray(R.array.pub_tags)){
            try{
                int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
                if(((Chip)requireView().findViewById(field)).isChecked())
                    tags.add(String.valueOf(((Chip)requireView().findViewById(field)).getText()));
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
        }
    }

    public void moreBeers(View v)  {
        if (!moreBeers) {
            ((TextView)v.findViewById(R.id.MoreBeers)).setText("Mniej");
            try{
                for(var sid: requireContext().getResources().getStringArray(R.array.second_to_show_breweries)) {
                    int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
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
                for (var sid : requireContext().getResources().getStringArray(R.array.second_to_show_breweries)) {
                    int field = R.id.class.getField(sid.replace(" ", "")).getInt(0);
                    v.findViewById(field).setVisibility(View.GONE);
                }
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "drinksCheck: Such View Field doesn't exit");
            }
            moreBeers = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pubListViewModel.getFilterFragmentUiState().getValue() != null)
            checkVisibility();
        if(listParticularBeersAdapter != null)
            ((RecyclerView) requireView().findViewById(R.id.ParticularBeersList)).setAdapter(listParticularBeersAdapter);
        if(pubListViewModel.getBrewery_textview() != null)
            ((TextView)requireView().findViewById(R.id.choose_brewery_textview)).setText(pubListViewModel.getBrewery_textview());
        if(pubListViewModel.getStyle_textview() != null)
            ((TextView)requireView().findViewById(R.id.choose_style_textview)).setText(pubListViewModel.getStyle_textview());
    }

    private void checkVisibility(){
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(0))
            requireView().findViewById(R.id.IVrating).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(1))
            requireView().findViewById(R.id.IVdistance).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(2))
            requireView().findViewById(R.id.IVprice).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(3))
            requireView().findViewById(R.id.IVtime).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(4))
            requireView().findViewById(R.id.IVbeer).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(5))
            requireView().findViewById(R.id.IVBreweries).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(6))
            requireView().findViewById(R.id.IVStyles).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(7))
            requireView().findViewById(R.id.IVParticularBeers).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(8))
            requireView().findViewById(R.id.IVdrinks).performClick();
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(9))
            requireView().findViewById(R.id.IVTags).performClick();
    }


}