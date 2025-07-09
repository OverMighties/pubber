package com.overmighties.pubber.feature.owner;

import static com.overmighties.pubber.app.Constants.INFORMATION_FRAGMENT_SIGN_IN_FORM_IDS;
import static com.overmighties.pubber.app.Constants.INFORMATION_FRAGMENT_SIGN_UP_FORM_IDS;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.overmighties.pubber.R;

import java.util.List;

public class InformationFragment extends Fragment {

    public static final String TAG = "InformationFragment";

    public InformationFragment() {super(R.layout.fragment_owner_information);}
    private OwnerViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(OwnerViewModel.initializer))
                .get(OwnerViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

        requireActivity().requireViewById(R.id.main_topAppBarLayout_back).setVisibility(View.VISIBLE);
        ((MaterialToolbar)requireActivity().requireViewById(R.id.main_topAppBarView_back)).setTitle(null);

        setUpListeners();
    }

    private void setUpListeners(){
        requireView().findViewById(R.id.ownerInfo_button_sign).setOnClickListener(v->{
            styleButton((Button) v);
            unStyleButton(requireView().findViewById(R.id.ownerInfo_button_signUp));
            makeInvisibleAll();
            makeVisible(INFORMATION_FRAGMENT_SIGN_IN_FORM_IDS);
            ((Button)requireView().findViewById(R.id.ownerInfo_button_send)).setText(getString(R.string.sign_in));
        });

        requireView().findViewById(R.id.ownerInfo_button_signUp).setOnClickListener(v->{
            styleButton((Button) v);
            unStyleButton(requireView().findViewById(R.id.ownerInfo_button_sign));
            makeInvisibleAll();
            makeVisible(INFORMATION_FRAGMENT_SIGN_UP_FORM_IDS);
            ((Button)requireView().findViewById(R.id.ownerInfo_button_send)).setText(getString(R.string.send));
        });

        requireView().findViewById(R.id.ownerInfo_IV_expand).setOnClickListener(v->{
            if (requireView().findViewById(R.id.ownerInfo_TV_collInfo).getVisibility() == View.GONE){
                requireView().findViewById(R.id.ownerInfo_TV_collInfo).setVisibility(View.VISIBLE);
                ((ImageView)requireView().findViewById(R.id.ownerInfo_IV_expand)).setImageResource(R.drawable.ic_expand_less_primary);
            } else {
                requireView().findViewById(R.id.ownerInfo_TV_collInfo).setVisibility(View.GONE);
                ((ImageView)requireView().findViewById(R.id.ownerInfo_IV_expand)).setImageResource(R.drawable.ic_expand_more_primary);
            }
        });
    }

    private void styleButton(Button btn) {
        btn.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.button_shape_primary));
        btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_primary));
        btn.setTextColor(Color.TRANSPARENT);
    }

    private void unStyleButton(Button btn) {
        btn.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.button_shape_container));
        btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_secondary_container));
    }

    private void makeInvisibleAll() {
    }

    private void makeVisible(int[] ids_list){

    }
}
