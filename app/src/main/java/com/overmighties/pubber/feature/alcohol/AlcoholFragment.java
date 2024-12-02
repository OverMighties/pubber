package com.overmighties.pubber.feature.alcohol;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;
import java.util.List;

public class AlcoholFragment  extends Fragment {

    public static final String TAG = "AlcoholFragment";

    public AlcoholViewModel viewModel;

    public AlcoholFragment() {super(R.layout.fragment_alcohol);}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AlcoholViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        viewModel.checkBackBroundTask();
        setUpData();
        setUpListeners();
    }

    private void setUpData() {
        ((TextView)requireView().findViewById(R.id.alcoholFragment_text_alcoholName)).setText(viewModel.getUiState().getValue().getName() + " ~ " +
                viewModel.getUiState().getValue().getAlcoholContent()+"%");
        Glide.with(requireContext())
                .load(viewModel.getUiState().getValue().getPhoto_path())
                .centerCrop()
                .into((ImageView) requireView().findViewById(R.id.alcoholFragment_image_alcohol));
        //hopiness
        ((TextView)requireView().findViewById(R.id.alcoholFragment_text_hopinessRating))
                .setText(viewModel.getUiState().getValue().getHopiness()+"/5");
        List<ImageView> list = new ArrayList<>();
        prepareList(list);
        new RatingToIVConverter().convert(list, 17,
                requireView().findViewById(R.id.alcoholFragment_cl_hopiness),
                Float.valueOf(viewModel.getUiState().getValue().getHopiness()), 10, 16, false);
        //maltiness
        ((TextView)requireView().findViewById(R.id.alcoholFragment_text_maltinessRating))
                .setText(viewModel.getUiState().getValue().getMaltiness()+"/5");
        prepareList(list);
        new RatingToIVConverter().convert(list, 17,
                requireView().findViewById(R.id.alcoholFragment_cl_maltiness),
                Float.valueOf(viewModel.getUiState().getValue().getMaltiness()), 10, 16, false);
        //desciption
        ((TextView)requireView().findViewById(R.id.alcoholFragment_text_longDesciption)).setText(viewModel.getUiState().getValue().getLong_des());
    }

    private void prepareList(List<ImageView> list){
        list.clear();
        for(int i=0; i<5; i++){
            list.add(new ImageView(requireContext()));
        }
    }

    private void setUpListeners(){
        requireView().findViewById(R.id.alcoholFragment_button_back).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        });
    }

}
