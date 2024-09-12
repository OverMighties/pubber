package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsDrinkCardViewUiState;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsSelectListener;
import com.overmighties.pubber.feature.search.filterselect.DrinkListAdapter;
import com.overmighties.pubber.feature.search.filterselect.FilterSelectViewModel;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectDrinkCardViewUiState;

import java.util.ArrayList;
import java.util.List;

public class DetailsEditAlcoholsSelectFragment extends Fragment implements DetailsSelectListener {

    public static final String TAG = "DetailsEditAlcoholsSelectFragment";

    public DetailsEditAlcoholsSelectFragment() {super(R.layout.details_edit_alcohols_select);}

    private DetailsEditAlcoholsViewModel viewModel;

    private DetailsDrinkListAdapter adapter;
    private List<DetailsDrinkCardViewUiState> list = new ArrayList<>();
    private String[] names;

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsEditAlcoholsViewModel.initializer)).get(DetailsEditAlcoholsViewModel.class);

        prepareData();
        setUpListeners();
    }

    private void prepareData(){
        if(viewModel.getDataType() == DetailsEditAlcoholsViewModel.listDataType.Breweries){
            names = requireContext().getResources().getStringArray(R.array.all_breweries);
        }
        else if(viewModel.getDataType() == DetailsEditAlcoholsViewModel.listDataType.Styles){
            names = requireContext().getResources().getStringArray(R.array.beer_styles_unkown);
        }
        else{
            names = requireContext().getResources().getStringArray(R.array.cocktails);
        }
        for(int i=0; i<names.length; i++){
            list.add(new DetailsDrinkCardViewUiState(names[i]));
        }
        adapter = new DetailsDrinkListAdapter(list,this);
        ((RecyclerView)requireView().findViewById(R.id.details_search_list)).setAdapter(adapter);
    }

    private void setUpListeners(){
        EditText editText = ((EditText)requireView().findViewById(R.id.detials_edit_text_search));

        requireView().findViewById(R.id.details_search_list).setOnClickListener(v->{
            unFocus(editText);
        });

        requireView().findViewById(R.id.detailsEditAlcoholsSelectFragment).setOnClickListener(v->{
            unFocus(editText);
        });

        requireView().findViewById(R.id.CloseButtonSelect).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(editText.getText().toString().equals(""))
                        ((TextInputLayout)requireView().findViewById(R.id.details_text_input_layout_search)).setHint(null);

                }
                else{
                    if(editText.getText().toString().equals(""))
                        ((TextInputLayout)requireView().findViewById(R.id.details_text_input_layout_search)).setHint(getString(R.string.searchview_hint));
                }
            }
        });
    }

    private void unFocus(EditText editText){
        if (!editText.isActivated()){
            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        if(viewModel.getDataType() == DetailsEditAlcoholsViewModel.listDataType.Breweries){
            viewModel.setChoosenBrewery(names[position]);
            viewModel.setDataType(DetailsEditAlcoholsViewModel.listDataType.Styles);
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsEditAlcoholsSelectFragmentDirections.actionDetailsEditAlcoholsSelectFragmentSelf());
        } else if (viewModel.getDataType() == DetailsEditAlcoholsViewModel.listDataType.Styles) {
            viewModel.setChoosenStyle(names[position]);
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsEditAlcoholsSelectFragmentDirections.actionDetailsEditAlcoholsSelectFragmentToDetailsEditFragment());
        }
        else{
            viewModel.setChoosenDrink(names[position]);
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        }
    }

}
