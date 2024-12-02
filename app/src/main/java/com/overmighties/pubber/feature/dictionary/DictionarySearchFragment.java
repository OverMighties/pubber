package com.overmighties.pubber.feature.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.AlcoholAlertDialog.AlcoholAlertDialog;
import com.overmighties.pubber.app.designsystem.AlcoholAlertDialog.AlcoholAlertDialogUiState;
import com.overmighties.pubber.feature.alcohol.AlcoholViewModel;
import com.overmighties.pubber.feature.dictionary.stateholders.AlcoholCardViewUiState;
import com.overmighties.pubber.feature.dictionary.util.AlcoholSelectListener;
import com.overmighties.pubber.feature.search.PubListViewModel;

import java.util.ArrayList;
import java.util.List;

public class DictionarySearchFragment extends Fragment implements AlcoholSelectListener {

    public static final String TAG = "DictionaryFragment";

    public DictionarySearchFragment() {super(R.layout.fragment_dictionary_select);}

    private DictionaryViewModel viewModel;

    private AlcoholViewModel alcoholViewModel;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity())
                .get(DictionaryViewModel.class);
        alcoholViewModel = new ViewModelProvider(requireActivity())
                .get(AlcoholViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState){
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        requireView().findViewById(R.id.dictionarySelect_image_back).setOnClickListener(v1->{
            navController.popBackStack();
        });
        AlcoholListAdapter adapter = new AlcoholListAdapter(
                viewModel.UiState().getValue().getAlcoholDataList(),
                this);
        ((RecyclerView)requireView().findViewById(R.id.dictionarySelect_recyclerView_search)).setAdapter(adapter);
    }
    @Override
    public void onItemClicked(int position) {
        AlcoholCardViewUiState data = viewModel.UiState().getValue().getAlcoholDataList().get(position);
        List<Float> list = new ArrayList<>();
        for(var par:data.getParametrs()) {
            try {
                list.add(Float.valueOf(par));
            } catch (Exception exception) {
                Log.i(TAG, "Can't transform pub's parametr into Float");
            }
        }
        alcoholViewModel.getUiState().getValue().setDrink_id(data.getDrink_id());
        alcoholViewModel.setUiStateDataBackground();
        AlcoholAlertDialog.show(
                requireContext(),
                new AlcoholAlertDialogUiState(
                        data.getDrink_id(),
                        data.getName(),
                        data.getShort_des(), data.getLong_des(),
                        data.getPhoto_path(), list),
                navController,
                DictionarySearchFragmentDirections.actionSeartcherFragmentToAlcoholFragment().getActionId());
    }
}
