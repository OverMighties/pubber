package com.overmighties.pubber.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.LoadActivity;

public class PlaceChoiceFragment extends Fragment {

    public PlaceChoiceFragment() {
        super(R.layout.place_choice);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ((Button) requireView().findViewById(R.id.city1)).setOnClickListener(v->{
            ((LoadActivity) getActivity()).goToMainActivity();
        });
        ((Button) requireView().findViewById(R.id.city2)).setOnClickListener(v->{
            ((LoadActivity) getActivity()).goToMainActivity();
        });
        ((Button) requireView().findViewById(R.id.city3)).setOnClickListener(v->{
            ((LoadActivity) getActivity()).goToMainActivity();
        });
    }
}