package com.overmighties.pubber.feature.search.filterselect;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectDrinkCardViewUiState;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectListener;

import java.util.ArrayList;
import java.util.List;

public class FilterSelectFragment   extends Fragment implements FilterSelectListener {

    public static final String TAG = "FilterSelectFragment";

    public FilterSelectFragment() {super(R.layout.filter_select_fragment);}
    private FilterSelectViewModel viewModel;
    private PubListViewModel pubListViewModel;
    private DrinkListAdapter adapter;
    private final List<FilterSelectDrinkCardViewUiState> list = new ArrayList<>();
    private NavController navController;
    private String[] names;


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity())
                .get(FilterSelectViewModel.class);
        pubListViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer)).get(PubListViewModel.class);
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

        prepareData();
        setUpListeners();
    }

    private void prepareData(){
        if(viewModel.getDataType() == FilterSelectViewModel.listDataType.Breweries){
            names = requireContext().getResources().getStringArray(R.array.all_breweries);
        }
        else{
            names = requireContext().getResources().getStringArray(R.array.beer_styles);
        }
        for(int i=0; i<names.length; i++){
            list.add(new FilterSelectDrinkCardViewUiState(names[i]));
        }
        adapter = new DrinkListAdapter(list,this);
        ((RecyclerView)requireView().findViewById(R.id.filterSelect_recyclerView_search)).setAdapter(adapter);
    }

    private void setUpListeners(){
        EditText editText = requireView().findViewById(R.id.filterSelect_editText_search);

        requireView().findViewById(R.id.filterSelect_recyclerView_search).setOnClickListener(v->{
            unFocus(editText);
        });

        requireView().findViewById(R.id.filterSelect_fragment).setOnClickListener(v->{
            unFocus(editText);
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
                        ((TextInputLayout)requireView().findViewById(R.id.filterSelect_inputLayout_search)).setHint(null);

                }
                else{
                    if(editText.getText().toString().equals(""))
                        ((TextInputLayout)requireView().findViewById(R.id.filterSelect_inputLayout_search)).setHint(getString(R.string.searchview_hint));
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
        if(viewModel.getDataType() == FilterSelectViewModel.listDataType.Breweries){
            pubListViewModel.getFilterFragmentUiState().getValue().setBreweryTextview(names[position]);
        }
        else{
            pubListViewModel.getFilterFragmentUiState().getValue().setStyleTextview(names[position]);

        }
        navController.popBackStack();
    }

}
