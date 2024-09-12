package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import static com.overmighties.pubber.app.Constants.DETAILS_ALCOHOL_EDIT_BEERS_WIDGETS_IDS;
import static com.overmighties.pubber.app.Constants.DETAILS_ALCOHOL_EDIT_DRINKS_WIDGETS_IDS;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.DrinkStyle;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.ChangeAlcoholCardViewUiState;
import com.overmighties.pubber.feature.search.ListParticularBeersAdapter;
import com.overmighties.pubber.feature.search.filterselect.FilterSelectViewModel;
import com.overmighties.pubber.feature.search.stateholders.ParticularBeersCardViewUiState;
import com.overmighties.pubber.util.DimensionsConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetailsEditAlcoholsFragment extends Fragment {

    public static final String TAG = "DetailsEditFragment";

    public DetailsEditAlcoholsFragment() {super(R.layout.details_edit_alcohols);}

    private DetailsViewModel detailsViewModel;
    private DetailsEditAlcoholsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsEditAlcoholsViewModel.initializer)).get(DetailsEditAlcoholsViewModel.class);
        detailsViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        //prepare visibility state list
        viewModel.getUiState().getValue().getVisibilityState().add(false);
        viewModel.getUiState().getValue().getVisibilityState().add(false);
        //prepare adapter list
        for (int i = 0; i < 4; i++) {
            viewModel.getAdapterList().add(null);
        }

    }


    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        setUpListeners();
    }

    private void setUpListeners(){

        requireView().findViewById(R.id.CloseButtonEdit).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsEditAlcoholsFragmentDirections.actionDetailsEditFragmentToDetailsFragment());
        });

        requireView().findViewById(R.id.imageView4).setOnClickListener(v->{
            Integer state = 0;
            Integer drawableId = 0;
            if(viewModel.getUiState().getValue().getVisibilityState().get(0)){
                drawableId = R.drawable.ic_expand_more_secondary;
                state = View.GONE;
                viewModel.getUiState().getValue().getVisibilityState().set(0, false);
            }
            else{
                drawableId = R.drawable.ic_expand_less_secondary;
                state = View.VISIBLE;
                viewModel.getUiState().getValue().getVisibilityState().set(0, true);
            }
            ((ImageView)requireView().findViewById(R.id.imageView4)).setImageDrawable(requireContext().getDrawable(drawableId));
            for(var id:  DETAILS_ALCOHOL_EDIT_BEERS_WIDGETS_IDS){
                requireView().findViewById(id).setVisibility(state);
            }
        });

        requireView().findViewById(R.id.buttonAddBeer).setOnClickListener(v->{
            viewModel.setDataType(DetailsEditAlcoholsViewModel.listDataType.Breweries);
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsEditAlcoholsFragmentDirections.actionDetailsEditFragmentToDetailsEditAlcoholsSelectFragment());
        });
        requireView().findViewById(R.id.buttonRemoveBeer).setOnClickListener(v-> {
            showDialogBoxBeer();
        });

        requireView().findViewById(R.id.imageView10).setOnClickListener(v->{
            Integer state = 0;
            Integer drawableId = 0;
            if(viewModel.getUiState().getValue().getVisibilityState().get(1)){
                drawableId = R.drawable.ic_expand_more_secondary;
                state = View.GONE;
                viewModel.getUiState().getValue().getVisibilityState().set(1, false);
            }
            else{
                drawableId = R.drawable.ic_expand_less_secondary;
                state = View.VISIBLE;
                viewModel.getUiState().getValue().getVisibilityState().set(1, true);
            }
            ((ImageView)requireView().findViewById(R.id.imageView10)).setImageDrawable(requireContext().getDrawable(drawableId));
            for(var id:  DETAILS_ALCOHOL_EDIT_DRINKS_WIDGETS_IDS){
                requireView().findViewById(id).setVisibility(state);
            }
        });

        requireView().findViewById(R.id.buttonAddDrink).setOnClickListener(v->{
            viewModel.setDataType(DetailsEditAlcoholsViewModel.listDataType.Drinks);
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsEditAlcoholsFragmentDirections.actionDetailsEditFragmentToDetailsEditAlcoholsSelectFragment());
        });

        requireView().findViewById(R.id.buttonRemoveDrink).setOnClickListener(v->{
            showDialogBoxDrinks();
        });
    }

    private void showDialogBoxBeer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        FlexboxLayout dialogView = (FlexboxLayout) inflater.inflate(R.layout.layout_alcohol_chips_container, null);

        if (detailsViewModel.getUiState().getValue().getDrinks() != null) {
            for (var alcohol : detailsViewModel.getUiState().getValue().getDrinks()) {
                if(alcohol.getType() == "Beer")
                {
                    View alcoholChipView = inflater.inflate(R.layout.alcohol_view_chip, dialogView, false);

                    Chip chip = alcoholChipView.findViewById(R.id.chipAlcohol);
                    chip.setText(alcohol.getName());
                    chip.setPadding(16, 0, 16, 0);
                    chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
                    chip.setChipBackgroundColorResource(R.color.chip_remove_background_color);
                    chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_remove_text_color));
                    chip.setTextSize(14);
                    chip.setChipStrokeColorResource(R.color.outline);
                    chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0.7F));
                    chip.setCheckedIconVisible(true);
                    chip.setCheckedIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.error)));
                    chip.setCheckedIcon(requireContext().getDrawable(R.drawable.ic_close_on_surface));
                    chip.setCheckable(true);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    chip.setLayoutParams(params);

                    if(alcohol.getDrinkStyles() != null && !alcohol.getDrinkStyles().isEmpty()){
                        String styles="";
                        for(var style: alcohol.getDrinkStyles()){
                            if(alcohol.getDrinkStyles().get(alcohol.getDrinkStyles().size()-1).getName().equals(style.getName())) {
                                styles = styles + style.getName();
                            }
                            else{
                                styles = styles + style.getName() + ", ";
                            }
                        }
                        ((TextView)alcoholChipView.findViewById(R.id.textViewAlcoholStyles)).setText(styles);
                    }
                    else{
                        alcoholChipView.findViewById(R.id.textViewAlcoholStyles).setVisibility(View.GONE);
                    }
                    if(!viewModel.getUiState().getValue().getRemoveBeerListName().isEmpty() && viewModel.getUiState().getValue().getRemoveBeerListName().stream().map(Drink::getName).collect(Collectors.toList()).contains(alcohol.getName()))
                        chip.setChecked(true);


                    dialogView.addView(alcoholChipView);
                }

            }
        }
        builder.setTitle(getString(R.string.select_beers_remove));

        builder.setNeutralButton(getString(R.string.cancel), ((dialog, which) -> {}));
        builder.setNegativeButton(getString(R.string.choose), ((dialog, which) -> {
            if(dialogView.getChildCount() != 0){
                for(int i=0; i<dialogView.getChildCount(); i++){
                    View alcoholChipView = dialogView.getChildAt(i);
                    List<DrinkStyle> drinkStyleList = new ArrayList<>();
                    drinkStyleList.add(new DrinkStyle(((TextView) alcoholChipView.findViewById(R.id.textViewAlcoholStyles)).getText().toString()));
                    Drink checkDrink = new Drink(
                            ((Chip) alcoholChipView.findViewById(R.id.chipAlcohol)).getText().toString(),
                            "Beer",
                            null,
                            drinkStyleList);
                    if(((Chip)alcoholChipView.findViewById(R.id.chipAlcohol)).isChecked()) {
                        if(!viewModel.getUiState().getValue().getRemoveBeerListName().contains(checkDrink))
                            viewModel.getUiState().getValue().getRemoveBeerListName().add(checkDrink);
                    }
                    else{
                        if(viewModel.getUiState().getValue().getRemoveBeerListName().contains(checkDrink))
                            viewModel.getUiState().getValue().getRemoveBeerListName().remove(checkDrink);
                    }

                }
                if(!viewModel.getUiState().getValue().getRemoveBeerListName().isEmpty()){
                    List<ChangeAlcoholCardViewUiState> list = new ArrayList<>();
                    for(var drink: viewModel.getUiState().getValue().getRemoveBeerListName()){
                        String styles = null;
                        if(drink.getDrinkStyles() != null && !drink.getDrinkStyles().isEmpty()){
                            styles="";
                            for(var style: drink.getDrinkStyles()){
                                if(drink.getDrinkStyles().get(drink.getDrinkStyles().size()-1).getName().equals(style.getName())) {
                                    styles = styles + style.getName();
                                }
                                else{
                                    styles = styles + style.getName() + ", ";
                                }
                            }
                        }
                        list.add(new ChangeAlcoholCardViewUiState(drink.getName(), styles));
                    }
                    viewModel.getAdapterList().set(1, new ChangeAlcoholListAdapter(list, true, false, viewModel));
                    ((RecyclerView) requireView().findViewById(R.id.removeBeerRecyclerView)).setAdapter(viewModel.getAdapterList().get(1));
                }
                else {
                    ((RecyclerView) requireView().findViewById(R.id.removeBeerRecyclerView)).setAdapter(null);
                }
            }
        }));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialogBoxDrinks(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        FlexboxLayout dialogView = (FlexboxLayout) inflater.inflate(R.layout.layout_alcohol_chips_container, null);

        if (detailsViewModel.getUiState().getValue().getDrinks() != null) {
            for (var alcohol : detailsViewModel.getUiState().getValue().getDrinks()) {
                if(!alcohol.getType().equals("Beer"))
                {
                    View alcoholChipView = inflater.inflate(R.layout.alcohol_view_chip, dialogView, false);

                    Chip chip = alcoholChipView.findViewById(R.id.chipAlcohol);
                    chip.setText(alcohol.getName());
                    chip.setPadding(16, 0, 16, 0);
                    chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
                    chip.setChipBackgroundColorResource(R.color.chip_remove_background_color);
                    chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_remove_text_color));
                    chip.setTextSize(14);
                    chip.setChipStrokeColorResource(R.color.outline);
                    chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0.7F));
                    chip.setCheckedIconVisible(true);
                    chip.setCheckedIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.error)));
                    chip.setCheckedIcon(requireContext().getDrawable(R.drawable.ic_close_on_surface));
                    chip.setCheckable(true);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    chip.setLayoutParams(params);
                    alcoholChipView.findViewById(R.id.textViewAlcoholStyles).setVisibility(View.GONE);

                    if(!viewModel.getUiState().getValue().getRemoveDrinkListName().isEmpty() && viewModel.getUiState().getValue().getRemoveDrinkListName().stream().map(Drink::getName).collect(Collectors.toList()).contains(alcohol.getName()))
                        chip.setChecked(true);


                    dialogView.addView(alcoholChipView);
                }

            }
        }
        builder.setTitle(getString(R.string.select_drinks_remove));

        builder.setNeutralButton(getString(R.string.cancel), ((dialog, which) -> {}));
        builder.setNegativeButton(getString(R.string.choose), ((dialog, which) -> {
            if(dialogView.getChildCount() != 0){
                for(int i=0; i<dialogView.getChildCount(); i++){
                    View alcoholChipView = dialogView.getChildAt(i);
                    Drink checkDrink = new Drink(
                            ((Chip) alcoholChipView.findViewById(R.id.chipAlcohol)).getText().toString(),
                            "Drink",
                            null,
                            null);
                    if(((Chip)alcoholChipView.findViewById(R.id.chipAlcohol)).isChecked()) {
                        if(!viewModel.getUiState().getValue().getRemoveDrinkListName().contains(checkDrink))
                            viewModel.getUiState().getValue().getRemoveDrinkListName().add(checkDrink);
                    }
                    else{
                        if(viewModel.getUiState().getValue().getRemoveDrinkListName().contains(checkDrink))
                            viewModel.getUiState().getValue().getRemoveDrinkListName().remove(checkDrink);
                    }

                }
                if(!viewModel.getUiState().getValue().getRemoveDrinkListName().isEmpty()){
                    List<ChangeAlcoholCardViewUiState> list = new ArrayList<>();
                    for(var drink: viewModel.getUiState().getValue().getRemoveDrinkListName()){
                        list.add(new ChangeAlcoholCardViewUiState(drink.getName(), null));
                    }
                    viewModel.getAdapterList().set(3, new ChangeAlcoholListAdapter(list, true, true, viewModel));
                    ((RecyclerView) requireView().findViewById(R.id.removeDrinkRecyclerView)).setAdapter(viewModel.getAdapterList().get(3));
                }
                else {
                    ((RecyclerView) requireView().findViewById(R.id.removeDrinkRecyclerView)).setAdapter(null);
                }
            }
        }));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(viewModel.getAdapterList().get(0) != null)
            ((RecyclerView) requireView().findViewById(R.id.addBeerRecyclerView)).setAdapter(viewModel.getAdapterList().get(0));

        if(viewModel.getAdapterList().get(1) != null)
            ((RecyclerView) requireView().findViewById(R.id.removeBeerRecyclerView)).setAdapter(viewModel.getAdapterList().get(1));

        if(viewModel.getAdapterList().get(2) != null)
            ((RecyclerView) requireView().findViewById(R.id.addDrinkRecyclerView)).setAdapter(viewModel.getAdapterList().get(2));

        if(viewModel.getAdapterList().get(3) != null)
            ((RecyclerView) requireView().findViewById(R.id.removeDrinkRecyclerView)).setAdapter(viewModel.getAdapterList().get(3));

        if(viewModel.getChoosenBrewery() != null){
            if(viewModel.getAdapterList().get(0) != null){
                viewModel.getAdapterList().get(0).addNextAlcohol(new ChangeAlcoholCardViewUiState(viewModel.getChoosenBrewery(), viewModel.getChoosenStyle()));
            }
            else{
                List<ChangeAlcoholCardViewUiState> list = new ArrayList<>();
                list.add(new ChangeAlcoholCardViewUiState(viewModel.getChoosenBrewery(), viewModel.getChoosenStyle()));
                viewModel.getAdapterList().set(0, new ChangeAlcoholListAdapter(list, false, false, viewModel));
                ((RecyclerView) requireView().findViewById(R.id.addBeerRecyclerView)).setAdapter(viewModel.getAdapterList().get(0));
            }
            viewModel.setChoosenBrewery(null);
            viewModel.setChoosenStyle(null);
        }

        if(viewModel.getChoosenDrink() != null){
            if(viewModel.getAdapterList().get(2) != null){
                viewModel.getAdapterList().get(2).addNextAlcohol(new ChangeAlcoholCardViewUiState(viewModel.getChoosenDrink(), null));
            }
            else{
                List<ChangeAlcoholCardViewUiState> list = new ArrayList<>();
                list.add(new ChangeAlcoholCardViewUiState(viewModel.getChoosenDrink(), null));
                viewModel.getAdapterList().set(2, new ChangeAlcoholListAdapter(list, false, true, viewModel));
                ((RecyclerView) requireView().findViewById(R.id.addDrinkRecyclerView)).setAdapter(viewModel.getAdapterList().get(2));
            }
            viewModel.setChoosenDrink(null);
        }

        Integer state = 0;
        Integer drawableId = 0;

        if(!viewModel.getUiState().getValue().getVisibilityState().get(0)){
            drawableId = R.drawable.ic_expand_more_secondary;
            state = View.GONE;
            viewModel.getUiState().getValue().getVisibilityState().set(0, false);
        }
        else{
            drawableId = R.drawable.ic_expand_less_secondary;
            state = View.VISIBLE;
            viewModel.getUiState().getValue().getVisibilityState().set(0, true);
        }

        ((ImageView)requireView().findViewById(R.id.imageView4)).setImageDrawable(requireContext().getDrawable(drawableId));
        for(var id:  DETAILS_ALCOHOL_EDIT_BEERS_WIDGETS_IDS){
            requireView().findViewById(id).setVisibility(state);
        }

        if(!viewModel.getUiState().getValue().getVisibilityState().get(1)){
            drawableId = R.drawable.ic_expand_more_secondary;
            state = View.GONE;
            viewModel.getUiState().getValue().getVisibilityState().set(1, false);
        }
        else{
            drawableId = R.drawable.ic_expand_less_secondary;
            state = View.VISIBLE;
            viewModel.getUiState().getValue().getVisibilityState().set(1, true);
        }

        ((ImageView)requireView().findViewById(R.id.imageView10)).setImageDrawable(requireContext().getDrawable(drawableId));
        for(var id:  DETAILS_ALCOHOL_EDIT_DRINKS_WIDGETS_IDS){
            requireView().findViewById(id).setVisibility(state);
        }



    }

}
