package com.overmighties.pubber.feature.search.filterselect;

import static com.overmighties.pubber.app.Constants.BREWERIES_VIEW_ID_LIST;

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
    private List<FilterSelectDrinkCardViewUiState> list = new ArrayList<>();
    private NavController navController;
    private String[] names;


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity())
                .get(FilterSelectViewModel.class);
        pubListViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer)).get(PubListViewModel.class);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);

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
        ((RecyclerView)requireView().findViewById(R.id.search_list)).setAdapter(adapter);
    }

    private void setUpListeners(){
        EditText editText = ((EditText)requireView().findViewById(R.id.edit_text_search));

        requireView().findViewById(R.id.search_list).setOnClickListener(v->{
            unFocus(editText);
        });

        requireView().findViewById(R.id.filter_select_fragment).setOnClickListener(v->{
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
                        ((TextInputLayout)requireView().findViewById(R.id.text_input_layout_search)).setHint(null);

                }
                else{
                    if(editText.getText().toString().equals(""))
                        ((TextInputLayout)requireView().findViewById(R.id.text_input_layout_search)).setHint(getString(R.string.searchview_hint));
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
            pubListViewModel.setBrewery_textview(names[position]);
        }
        else{
            pubListViewModel.setStyle_textview(names[position]);

        }
        navController.popBackStack();
    }

}
