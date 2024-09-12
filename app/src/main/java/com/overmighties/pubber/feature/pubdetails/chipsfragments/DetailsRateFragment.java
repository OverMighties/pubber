package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.overmighties.pubber.R;

public class DetailsRateFragment extends Fragment {

    public static final String TAG = "DetailsRateFragment";

    public DetailsRateFragment() {super(R.layout.details_rate);}


    //TODO make details_rate.xml ratebar better as soon as new graphics will be made
    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        requireView().findViewById(R.id.CloseButtonEdit).setOnClickListener(v1->{
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        });


        requireView().findViewById(R.id.buttonPublish).setOnClickListener(v1->{
            Float rating = ((RatingBar)requireView().findViewById(R.id.ratingBar)).getRating();
            String comment = ((EditText)requireView().findViewById(R.id.edit_field_rating)).getText().toString();
            if(rating!=0) {
                //TODO make comment be published
                NavHostFragment.findNavController(getParentFragment()).popBackStack();
            }
            else{
                Toast.makeText(requireContext(), getString(R.string.rating_error), Toast.LENGTH_LONG).show();
            }
        });
    }

}
