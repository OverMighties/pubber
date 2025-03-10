package com.overmighties.pubber.feature.search;

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
import android.widget.ScrollView;
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
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.stateholders.ParticularBeersCardViewUiState;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.ResourceUtil;

import java.util.ArrayList;
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

        var nav_bar=requireActivity().findViewById(R.id.main_bottomNavView);
        requireView().findViewById(R.id.filtration_button_filtr).setOnClickListener(v->{
            filtration(requireView());
            Navigation.findNavController(v).navigate(FilterFragmentDirections.actionFilterToSearcher());
        });

        pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);
        filterSelectViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(FilterSelectViewModel.initializer)).get(FilterSelectViewModel.class);
        NavigationBar.smoothHide(nav_bar, 200);
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        arrowExpandersListeners();
        dropDownMenusListener();
        setUpRangeSliders();
        prepareChips();
        if(pubListViewModel.getFilterUiState().getValue() != null)
            bringBackFilterSettings();
        requireView().findViewById(R.id.filtration_image_close).setOnClickListener(v -> navController.navigate(FilterFragmentDirections.actionFilterToSearcher()));
    }


    private void arrowExpandersListeners()
    {
        requireView().findViewById(R.id.filtration_text_moreBreweries).setOnClickListener(buttView->moreBeers(requireView()));
        requireView().findViewById(R.id.filtation_image_rating).setOnClickListener(v -> {
            if (pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(0)) {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(0, false);
                requireView().findViewById(R.id.filtration_rangeSlider_rating).setVisibility(View.GONE);
                ((ImageView) requireView().findViewById(R.id.filtation_image_rating)).setImageResource(R.drawable.ic_expand_more_primary);
            } else {
                requireView().findViewById(R.id.filtration_rangeSlider_rating).setVisibility(View.VISIBLE);
                ((ImageView) requireView().findViewById(R.id.filtation_image_rating)).setImageResource(R.drawable.ic_expand_less_primary);
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(0, true);
            }
        });

        requireView().findViewById(R.id.filtration_image_distance).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(1)) {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(1, false);
                requireView().findViewById(R.id.filtration_slider_distance).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_distance)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(1, true);
                requireView().findViewById(R.id.filtration_slider_distance).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_distance)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.filtration_image_price).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(2))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(2, false);
                requireView().findViewById(R.id.filtration_chipGroup_price).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_price)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(2, true);
                requireView().findViewById(R.id.filtration_chipGroup_price).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_price)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });

        requireView().findViewById(R.id.filtration_image_time).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(3))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(3, false);
                ((ImageView)requireView().findViewById(R.id.filtration_image_time)).setImageResource(R.drawable.ic_expand_more_primary);
                requireView().findViewById(R.id.filtration_chip_anytime).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_chip_customTime).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_chip_openNow).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.GONE);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(3, true);
                ((ImageView)requireView().findViewById(R.id.filtration_image_time)).setImageResource(R.drawable.ic_expand_less_primary);
                requireView().findViewById(R.id.filtration_chip_anytime).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_chip_openNow).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_chip_customTime).setVisibility(View.VISIBLE);
                if(((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).isChecked()){
                    requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.VISIBLE);
                }
            }
        });
        requireView().findViewById(R.id.filtration_chip_customTime).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.filtration_chip_openNow)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.filtration_chip_anytime)).setChecked(false);
                requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.VISIBLE);
            }
            else {((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).setChecked(true);}
        });
        requireView().findViewById(R.id.filtration_chip_openNow).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.filtration_chip_openNow)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.filtration_chip_anytime)).setChecked(false);
                requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.filtration_chip_openNow)).setChecked(true);}
        });
        requireView().findViewById(R.id.filtration_chip_anytime).setOnClickListener(v -> {
            if(((Chip)requireView().findViewById(R.id.filtration_chip_anytime)).isChecked())
            {
                ((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).setChecked(false);
                ((Chip)requireView().findViewById(R.id.filtration_chip_openNow)).setChecked(false);
                requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.GONE);
            }
            else {((Chip)requireView().findViewById(R.id.filtration_chip_anytime)).setChecked(true);}
        });

        requireView().findViewById(R.id.filtration_image_beer).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(4)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(4, false);
                for(var id: FILTER_FRAGMENT_BEER_WIDGETS_IDS){
                    requireView().findViewById(id).setVisibility(View.GONE);
                }
                if (requireView().findViewById(R.id.filtration_chipGroup_breweries).isShown())
                    requireView().findViewById(R.id.filtration_image_breweries).performClick();
                if (requireView().findViewById(R.id.filtration_chipGroup_styles).isShown())
                    requireView().findViewById(R.id.filtration_image_styles).performClick();
                if (requireView().findViewById(R.id.filtration_recyclerView_particularBeers).isShown())
                    requireView().findViewById(R.id.filtration_image_particularBeers).performClick();
                ((ImageView)requireView().findViewById(R.id.filtration_image_beer)).setImageResource(R.drawable.ic_expand_more_primary);
            }
            else {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(4, true);
                for(var id: FILTER_FRAGMENT_BEER_WIDGETS_IDS){
                    requireView().findViewById(id).setVisibility(View.VISIBLE);
                }
                ((ImageView)requireView().findViewById(R.id.filtration_image_beer)).setImageResource(R.drawable.ic_expand_less_primary);
            }
        });
        requireView().findViewById(R.id.filtration_image_breweries).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(5))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(5, false);
                requireView().findViewById(R.id.filtration_chipGroup_breweries).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_text_moreBreweries).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_breweries)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(5, true);
                requireView().findViewById(R.id.filtration_chipGroup_breweries).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_text_moreBreweries).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_breweries)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });
        requireView().findViewById(R.id.filtration_image_styles).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(6)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(6, false);
                requireView().findViewById(R.id.filtration_chipGroup_styles).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_styles)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else{
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(6, true);
                requireView().findViewById(R.id.filtration_chipGroup_styles).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_styles)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });
        requireView().findViewById(R.id.filtration_image_particularBeers).setOnClickListener(v->{
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(7)){
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(7, false);
                requireView().findViewById(R.id.filtration_cardView_chooseBrewery).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_cardView_chooseStyle).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_cardView_addParticularBeer).setVisibility(View.GONE);
                requireView().findViewById(R.id.filtration_recyclerView_particularBeers).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_particularBeers)).setImageResource(R.drawable.ic_expand_more_secondary);
            }
            else{
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(7, true);
                requireView().findViewById(R.id.filtration_cardView_chooseBrewery).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_cardView_chooseStyle).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_cardView_addParticularBeer).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.filtration_recyclerView_particularBeers).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_particularBeers)).setImageResource(R.drawable.ic_expand_less_secondary);
            }
        });

        requireView().findViewById(R.id.filtration_cardView_chooseBrewery).setOnClickListener(v->{
            pubListViewModel.getFilterFragmentUiState().getValue().setBreweryTextview(null);
            filterSelectViewModel.setDataType(FilterSelectViewModel.listDataType.Breweries);
            NavHostFragment.findNavController(this).navigate(FilterFragmentDirections.actionFilterFragmentToFilterSelectFragment());
        });
        requireView().findViewById(R.id.filtration_cardView_chooseStyle).setOnClickListener(v->{
            pubListViewModel.getFilterFragmentUiState().getValue().setStyleTextview(null);
            filterSelectViewModel.setDataType(FilterSelectViewModel.listDataType.Styles);
            NavHostFragment.findNavController(this).navigate(FilterFragmentDirections.actionFilterFragmentToFilterSelectFragment());
        });
        requireView().findViewById(R.id.filtration_cardView_addParticularBeer).setOnClickListener(v->{
            TextView brewery_tv = requireView().findViewById(R.id.filtration_text_chooseBrewery);
            TextView style_tv = requireView().findViewById(R.id.filtration_text_chooseStyle);
            if(brewery_tv.getText().toString() != getString(R.string.choose_brewery) && style_tv.getText().toString() != getString(R.string.choose_style)) {
                if (listParticularBeersAdapter != null) {
                    listParticularBeersAdapter.addNextBeer(new ParticularBeersCardViewUiState(brewery_tv.getText().toString(), style_tv.getText().toString()));
                } else {
                    List<ParticularBeersCardViewUiState> list = new ArrayList<>();
                    list.add(new ParticularBeersCardViewUiState(brewery_tv.getText().toString(), style_tv.getText().toString()));
                    listParticularBeersAdapter = new ListParticularBeersAdapter(list);
                    ((RecyclerView) requireView().findViewById(R.id.filtration_recyclerView_particularBeers)).setAdapter(listParticularBeersAdapter);
                }
                pubListViewModel.getFilterFragmentUiState().getValue().setBreweryTextview(null);
                pubListViewModel.getFilterFragmentUiState().getValue().setStyleTextview(null);
                brewery_tv.setText(getString(R.string.choose_brewery));
                style_tv.setText(getString(R.string.choose_style));
            }
        });
        requireView().findViewById(R.id.filtration_image_drinks).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(8))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(8, false);
                requireView().findViewById(R.id.filtration_chipGroup_drinks).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_drinks)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(8, true);
                requireView().findViewById(R.id.filtration_chipGroup_drinks).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_drinks)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });

        requireView().findViewById(R.id.filtration_image_tags).setOnClickListener(v -> {
            if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(9))
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(9, false);
                requireView().findViewById(R.id.filtration_chipGroup_tags).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_tags)).setImageResource(R.drawable.ic_expand_more_primary);

            }
            else
            {
                pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(9, true);
                requireView().findViewById(R.id.filtration_chipGroup_tags).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.filtration_image_tags)).setImageResource(R.drawable.ic_expand_less_primary);

            }
        });

        requireView().findViewById(R.id.filtration_button_clear).setOnClickListener(v -> {
            ((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_drinks)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_price)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_breweries)).clearCheck();
            ((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_tags)).clearCheck();
            requireView().findViewById(R.id.filtration_chip_anytime).performClick();
            ((RangeSlider)requireView().findViewById(R.id.filtration_rangeSlider_rating)).setValues(0f,10f);
            ((Slider)requireView().findViewById(R.id.filtration_slider_distance)).setValue(10f);
            pubListViewModel.filter(new FilterUiState());
        });


    }



    private void dropDownMenusListener()
    {

        requireView().findViewById(R.id.filtration_view_customDay).setOnClickListener(v -> {
            //Down Menus days in FilterFragment
            popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.menu_pop_up_days, null);
            popupWindow = new PopupWindow(popUpView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            //Listeners For popup days in filtrarion screen
            listenersMenuDays(popupWindow, popUpView);
            //cheking which one is selected to highlight it
            whichOneCheckedDays(popUpView);
            popupWindow.showAsDropDown(requireView().findViewById(R.id.filtration_view_customDay),0,0,Gravity.BOTTOM);
            requireView().findViewById(R.id.filtration_view_customDay).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
            ((ImageView)requireView().findViewById(R.id.filtration_image_customDay)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
        });
        requireView().findViewById(R.id.filtration_view_time).setOnClickListener(v -> {
            popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.menu_pop_up_time, null);
            popupWindow = new PopupWindow(popUpView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            //Listeners For popup time in filtrarion screen
            listenersmenutime();
            //cheking which one is selected to highlight it
            whichOneCheckedTime();
            setUpNextDayTV();
            popupWindow.showAsDropDown(requireView().findViewById(R.id.filtration_view_time),0,0,Gravity.BOTTOM);
            ((ImageView)requireView().findViewById(R.id.filtration_image_customTime)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
            requireView().findViewById(R.id.filtration_view_time).setBackgroundResource(R.drawable.menu_drop_out_list_highlight);
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
            requireView().findViewById(R.id.filtration_view_time).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
            ((ImageView)requireView().findViewById(R.id.filtration_image_customTime)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
            if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second != -1){
                String text = getString(R.string.from)+" "+
                        ((TextView)popUpView.findViewById(POP_UP_TIME_TEXT_IDS[pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first])).getText().toString()
                                + " " + getString(R.string.to) + " " + ((TextView)popUpView.findViewById(POP_UP_TIME_TEXT_IDS[pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second])).getText().toString();
                ((TextView)requireView().findViewById(R.id.filtration_text_customTime)).setText(text);
            } else if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first == -1 ){
                ((TextView)requireView().findViewById(R.id.filtration_text_customTime)).setText(getString(R.string.anytime));
            }
        });
    }

    private void checkTime(Integer backgroundId, Integer textViewId) {
        if(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second == -1){
            unCheckAll();
            popUpView.findViewById(backgroundId).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
            ((TextView)popUpView.findViewById(textViewId)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
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
                popUpView.findViewById(R.id.main_cl_container).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_up_highlight);
            }
        }
        else{
            for(int i=pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first;
                i<=pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second;i++){
                Pair<Integer, Integer> ids = POP_UP_TIME_PAIRS.get(i);
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
        popUpView.findViewById(POP_UP_TIME_PAIRS.get(0).first).setBackground(null);
        popUpView.findViewById(POP_UP_TIME_PAIRS.get(55).first).setBackground(null);
        popUpView.findViewById(R.id.main_cl_container).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_up);
        popUpView.findViewById(R.id.constraintLayout5).setBackgroundResource(R.drawable.menu_drop_out_list_shape_scrollview_down);
        if (fromTV != null) {
            ((ConstraintLayout) fromTV.getParent()).removeView(fromTV);
            fromTV = null;
        }
        if (toTV != null) {
            ((ConstraintLayout) toTV.getParent()).removeView(toTV);
            toTV = null;
        }
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
            constraintSet.applyTo(popUpView.findViewById(POP_UP_TIME_IDS[n]));
        }
    }

    private void whichOneCheckedTime(){
        if(((TextView)requireView().findViewById(R.id.filtration_text_customTime)).getText().toString().equals(getString(R.string.anytime))) {
            popUpView.findViewById(POP_UP_TIME_IDS[0]).performClick();
        }
        else {
            int secondIndexNumber = pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().second;
            pubListViewModel.getFilterFragmentUiState().getValue().setPopUpTimeIds(new Pair<>(pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first , -1));
            popUpView.findViewById(POP_UP_TIME_IDS[secondIndexNumber]).performClick();
            ScrollView scrollView = popUpView.findViewById(R.id.PopUpScrollView);
            scrollView.post(()->{
                scrollView.smoothScrollTo(0, popUpView.findViewById(POP_UP_TIME_IDS[pubListViewModel.getFilterFragmentUiState().getValue().getPopUpTimeIds().first]).getTop());
            });
        }
    }



    //method for highlight checked constrainlayout when pop_up starts
    private void whichOneCheckedDays(View popUpView) {
        int i=0;
        for(var id:POP_UP_DAYS_TEXT_IDS){
            if(((TextView)requireView().findViewById(R.id.filtration_text_customDay)).getText().toString().equals(((TextView)popUpView.findViewById(id)).getText().toString())) {
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
            requireView().findViewById(R.id.filtration_view_customDay).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
            ((ImageView)requireView().findViewById(R.id.filtration_image_customDay)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
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
        ((TextView)(requireView().findViewById(R.id.filtration_text_customDay))).setText(((TextView)popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checkedIndex])).getText().toString());
        ((TextView) popUpView.findViewById(POP_UP_DAYS_TEXT_IDS[checkedIndex])).setTextColor(getResources().getColor(R.color.on_surface_variant));
        popUpView.findViewById(checkedId).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight);
        if(checkedIndex==0){popUpView.findViewById(R.id.cl_today).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_up);}
        if(checkedIndex==7){popUpView.findViewById(R.id.cl_sun).setBackgroundResource(R.drawable.menu_drop_out_list_shape_highlight_down);}
    }


    private void setUpRangeSliders()
    {
        RangeSlider ratingslider = requireView().findViewById(R.id.filtration_rangeSlider_rating);
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

        Slider distanceslider = requireView().findViewById(R.id.filtration_slider_distance);
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
        ChipGroup breweriesGroup = requireView().findViewById(R.id.filtration_chipGroup_breweries);
        ChipGroup stylesGroup = requireView().findViewById(R.id.filtration_chipGroup_styles);
        ChipGroup drinksGroup = requireView().findViewById(R.id.filtration_chipGroup_drinks);
        ChipGroup tagsGroup = requireView().findViewById(R.id.filtration_chipGroup_tags);
        //breweries chips
        for(var brewery:requireContext().getResources().getStringArray(R.array.all_breweries)){
            Chip chip = new Chip(requireContext());
            chip.setText(brewery);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), brewery.replace(" ", "")));
            styleChip(chip);
            chip.setVisibility(View.GONE);
            breweriesGroup.addView(chip);
        }
        //make visible the most commonly known breweries
        for(var breweryId:requireContext().getResources().getStringArray(R.array.first_to_show_breweries)){
            try {
                int field = R.id.class.getField(breweryId.replace(" ", "")).getInt(0);
                requireView().findViewById(field).setVisibility(View.VISIBLE);
            }catch(NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "makeCommonBreweriesShown: Such View Field doesn't exit");
            }

        }
        //styles
        for(var style: requireContext().getResources().getStringArray(R.array.beer_styles)){
            Chip chip = new Chip(requireContext());
            chip.setText(style);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), style.replace(" ", "")));
            styleChip(chip);
            stylesGroup.addView(chip);
        }
        //drinks
        for(var cocktail:requireContext().getResources().getStringArray(R.array.cocktails)){
            Chip chip = new Chip(requireContext());
            chip.setText(cocktail);
            chip.setId(ResourceUtil.getResourceIdByName(requireContext(), cocktail.replace(" ", "")));
            styleChip(chip);
            drinksGroup.addView(chip);
        }
        //tags
        for(var tag:requireContext().getResources().getStringArray(R.array.pub_tags)){
            Chip chip = new Chip(requireContext());
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
                !pubListViewModel.getFilterUiState().getValue().getBottomAverageRating().equals(bottomAverageRating))
            bottomAverageRating = pubListViewModel.getFilterUiState().getValue().getBottomAverageRating();
        if(!pubListViewModel.getFilterUiState().getValue().getUpperAverageRating().equals(uppperverageRating))
            uppperverageRating = pubListViewModel.getFilterUiState().getValue().getUpperAverageRating();
        ((RangeSlider)requireView().findViewById(R.id.filtration_rangeSlider_rating)).setValues(bottomAverageRating*2, uppperverageRating*2);
        //distance
        if(pubListViewModel.getFilterUiState().getValue().getDistance() != null ||
                pubListViewModel.getFilterUiState().getValue().getDistance() != FilterUiState.NONE_DISTANCE)
            ((Slider)requireView().findViewById(R.id.filtration_slider_distance)).setValue(
                    pubListViewModel.getFilterUiState().getValue().getDistance()*2);
        //price
        if (pubListViewModel.getFilterUiState().getValue().getPriceType() == null) {
            pubListViewModel.getFilterUiState().getValue();
        }
        if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.SUPER_CHEAP)) {
            ((Chip) requireView().findViewById(R.id.filtration_chip_cheap)).setChecked(true);
        } else if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.CHEAP)) {
            ((Chip) requireView().findViewById(R.id.filtration_chip_average)).setChecked(true);
        } else if (pubListViewModel.getFilterUiState().getValue().getPriceType().equals(PriceType.AVERAGE)) {
            ((Chip) requireView().findViewById(R.id.filtration_chip_expensive)).setChecked(true);
        }
        //time
        if(pubListViewModel.getFilterUiState().getValue().getOpenNow()){
            requireView().findViewById(R.id.filtration_chip_openNow).performClick();
        } else if (pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours() != null) {
            requireView().findViewById(R.id.filtration_chip_customTime).performClick();
            requireView().findViewById(R.id.filtration_cl_customTime).setVisibility(View.GONE);
            if(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getWeekDay() != null){
                ((TextView)requireView().findViewById(R.id.filtration_text_customDay)).setText(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getWeekDay());
            }
            if(pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getFromTime() != null &&
                    pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getToTime() != null){
                ((TextView)requireView().findViewById(R.id.filtration_text_customTime)).setText(
                        getString(R.string.from)+" "+ pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getFromTime() +
                                " "+getString(R.string.to)+" "+pubListViewModel.getFilterUiState().getValue().getCustomOpeningHours().getToTime());

            }
        } else{
            requireView().findViewById(R.id.filtration_chip_anytime).performClick();
        }

        //breweries
        if(pubListViewModel.getFilterUiState().getValue().getBeers()!=null){
            List<String> beers = new ArrayList<>();
            for(int n = 0; n<((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_breweries)).getChildCount(); n++){
                beers.add(((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_breweries)).getChildAt(n)).getText().toString());
            }
            for(var beer:pubListViewModel.getFilterUiState().getValue().getBeers()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_breweries)).getChildAt(beers.indexOf(beer))).setChecked(true);
            }
        }
        //styles
        if(pubListViewModel.getFilterUiState().getValue().getStyles() != null){
            List<String> styles = new ArrayList<>();
            for(int n = 0; n<((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_styles)).getChildCount(); n++){
                styles.add(((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_styles)).getChildAt(n)).getText().toString());
            }
            for(var style:pubListViewModel.getFilterUiState().getValue().getStyles()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_styles)).getChildAt(styles.indexOf(style))).setChecked(true);
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
                ((RecyclerView) requireView().findViewById(R.id.filtration_recyclerView_particularBeers)).setAdapter(listParticularBeersAdapter);
            }
        }
        //drinks
        if(pubListViewModel.getFilterUiState().getValue().getOtherDrinks() != null){
            List<String> drinks = new ArrayList<>();
            for(int n = 0; n<((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_drinks)).getChildCount(); n++){
                drinks.add(((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_drinks)).getChildAt(n)).getText().toString());
            }
            for(var drink:pubListViewModel.getFilterUiState().getValue().getOtherDrinks()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_drinks)).getChildAt(drinks.indexOf(drink))).setChecked(true);
            }
        }
        //tags
        if(pubListViewModel.getFilterUiState().getValue().getTags() != null){
            List<String> tags = new ArrayList<>();
            for(int n = 0; n<((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_tags)).getChildCount(); n++){
                tags.add(((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_tags)).getChildAt(n)).getText().toString());
            }
            for(var tag:pubListViewModel.getFilterUiState().getValue().getTags()){
                ((Chip)((ChipGroup)requireView().findViewById(R.id.filtration_chipGroup_tags)).getChildAt(tags.indexOf(tag))).setChecked(true);
            }
        }
    }

    public void filtration(View view)
    {

        RangeSlider averageRatingSlider=view.findViewById(R.id.filtration_rangeSlider_rating);
        float upAvgRating = Math.round(averageRatingSlider.getValues().get(1));
        upAvgRating = upAvgRating/2;
        float bottomAvgRating = Math.round(averageRatingSlider.getValues().get(0));
        bottomAvgRating = bottomAvgRating/2;
        Slider distanceSlider=view.findViewById(R.id.filtration_slider_distance);
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
        open = ((Chip) v.findViewById(R.id.filtration_chip_openNow)).isChecked();
        customOpeningHours = new FilterUiState.CustomOpeningHours(null, null, null);
        if(((Chip)requireView().findViewById(R.id.filtration_chip_customTime)).isChecked()){
            String[] time = String.valueOf(((TextView)requireView().findViewById(R.id.filtration_text_customTime)).getText()).split(" ");
            if(time.length >= 4){
                customOpeningHours = new FilterUiState.CustomOpeningHours(
                        ((TextView)requireView().findViewById(R.id.filtration_text_customDay)).getText().toString(),
                        time[1],
                        time[3]
                );
            }
            if((((TextView)requireView().findViewById(R.id.filtration_text_customTime)).getText()).toString().equals(getString(R.string.anytime))){
                customOpeningHours = new FilterUiState.CustomOpeningHours(
                        ((TextView)requireView().findViewById(R.id.filtration_text_customDay)).getText().toString(),
                        null,
                        null
                );
            }
        }
    }

    private void priceCheck(View v){
        if(((Chip) v.findViewById(R.id.filtration_chip_cheap)).isChecked())
            price= FilterConstants.CHEAP_PRICE;
        if(((Chip) v.findViewById(R.id.filtration_chip_average)).isChecked())
            price= FilterConstants.AVERAGE_PRICE;
        if(((Chip) v.findViewById(R.id.filtration_chip_expensive)).isChecked())
            price= FilterConstants.EXPENSIVE_PRICE;
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
        RecyclerView particularBeerList = requireView().findViewById(R.id.filtration_recyclerView_particularBeers);
        for (int i = 0; i < particularBeerList.getChildCount(); i++) {
            View childView = particularBeerList.getChildAt(i);
            String brewery_name = ((TextView)childView.findViewById(R.id.filtrationPB_text_brewery)).getText().toString();
            String style = ((TextView)childView.findViewById(R.id.filtrationPB_text_style)).getText().toString();
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
            ((TextView)v.findViewById(R.id.filtration_text_moreBreweries)).setText("Mniej");
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
            ((TextView)v.findViewById(R.id.filtration_text_moreBreweries)).setText("Więcej");
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
            ((RecyclerView) requireView().findViewById(R.id.filtration_recyclerView_particularBeers)).setAdapter(listParticularBeersAdapter);
        if(pubListViewModel.getFilterFragmentUiState().getValue().getBreweryTextview() != null)
            ((TextView)requireView().findViewById(R.id.filtration_text_chooseBrewery)).setText(pubListViewModel.getFilterFragmentUiState().getValue().getBreweryTextview());
        if(pubListViewModel.getFilterFragmentUiState().getValue().getStyleTextview() != null)
            ((TextView)requireView().findViewById(R.id.filtration_text_chooseStyle)).setText(pubListViewModel.getFilterFragmentUiState().getValue().getStyleTextview());
    }

    private void checkVisibility(){
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(0)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(0, false);
            requireView().findViewById(R.id.filtation_image_rating).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(1)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(1, false);
            requireView().findViewById(R.id.filtration_image_distance).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(2)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(2, false);
            requireView().findViewById(R.id.filtration_image_price).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(3)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(3, false);
            requireView().findViewById(R.id.filtration_image_time).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(4)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(4, false);
            requireView().findViewById(R.id.filtration_image_beer).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(5)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(5, false);
            requireView().findViewById(R.id.filtration_image_breweries).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(6)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(6, false);
            requireView().findViewById(R.id.filtration_image_styles).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(7)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(7, false);
            requireView().findViewById(R.id.filtration_image_particularBeers).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(8)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(8, false);
            requireView().findViewById(R.id.filtration_image_drinks).performClick();
        }
        if(pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().get(9)) {
            pubListViewModel.getFilterFragmentUiState().getValue().getExpandVisibilyStateList().set(9, false);
            requireView().findViewById(R.id.filtration_image_tags).performClick();
        }
    }


}