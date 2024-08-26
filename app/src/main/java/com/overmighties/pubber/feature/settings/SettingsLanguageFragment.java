package com.overmighties.pubber.feature.settings;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.SettingsHandler;

public class SettingsLanguageFragment extends Fragment {

    public static final String TAG="SettingsLanguageFragment";
    private SettingsLanguageViewModel viewModel;
    private NavController navController;

    private int highlighted; // 0 = polish 1 = english


    public SettingsLanguageFragment(){super(R.layout.fragment_settings_language);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        Log.i(TAG,"on create");
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(SettingsLanguageViewModel.initializer))
                .get(SettingsLanguageViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);

        if (requireActivity().findViewById(R.id.bottom_nav_view) != null)
            NavigationBar.smoothHide(requireActivity().findViewById(R.id.bottom_nav_view), 200);

        if(SettingsHandler.LanguageHelper.getLanguage(requireContext()) == 0){
            highlightPolish();
            highlighted = 0;
        }
        else{
           highlightEnglish();
           highlighted = 1;
        }

        requireView().findViewById(R.id.IV_polish).setOnClickListener(v->{
            if (highlighted == 1){
                SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_POLISH);
                // Restart the application to apply the language
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("openSettings", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                navController.popBackStack();
                startActivity(intent);
                requireActivity().finish();

            }
        });

        requireView().findViewById(R.id.IV_english).setOnClickListener(v->{
            if (highlighted == 0){
                SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_ENGLISH);
                // Restart the application to apply the language
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("openSettings", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                navController.popBackStack();
                startActivity(intent);
                requireActivity().finish();

            }
        });

        requireView().findViewById(R.id.IV_goback_l).setOnClickListener(v-> navController.popBackStack());

    }

    public  void highlightPolish(){
        MaterialCardView polish_cardview = requireView().findViewById(R.id.CardView_polish);
        polish_cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),68), (int) DimensionsConverter.pxFromDp(requireContext(), 44)));
        polish_cardview.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.primary));
        polish_cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 2.5f));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.settings_language));
        constraintSet.connect(R.id.CardView_polish, ConstraintSet.TOP, R.id.IV_goback_l, ConstraintSet.BOTTOM, (int) DimensionsConverter.pxFromDp(requireContext(), 8));
        constraintSet.connect(R.id.CardView_polish, ConstraintSet.START, R.id.settings_language, ConstraintSet.START, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        constraintSet.applyTo(requireView().findViewById(R.id.settings_language));
    }

    public void redo_highlightPolish(){
        MaterialCardView polish_cardview = requireView().findViewById(R.id.CardView_polish);
        polish_cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),64), (int) DimensionsConverter.pxFromDp(requireContext(), 40)));
        polish_cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 0f));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.settings_language));
        constraintSet.connect(R.id.CardView_polish, ConstraintSet.TOP, R.id.IV_goback_l, ConstraintSet.BOTTOM, (int) DimensionsConverter.pxFromDp(requireContext(), 8));
        constraintSet.connect(R.id.CardView_polish, ConstraintSet.START, R.id.settings_language, ConstraintSet.START, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        constraintSet.applyTo(requireView().findViewById(R.id.settings_language));
    }

    public void highlightEnglish(){
        MaterialCardView english_cardview = requireView().findViewById(R.id.CardView_english);

        english_cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),84), (int) DimensionsConverter.pxFromDp(requireContext(), 44)));
        english_cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 2.5f));
        english_cardview.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.primary));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.settings_language));
        constraintSet.connect(R.id.CardView_english, ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.CardView_english, ConstraintSet.BOTTOM, R.id.CardView_polish, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.CardView_english, ConstraintSet.START, R.id.CardView_polish, ConstraintSet.END, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        constraintSet.applyTo(requireView().findViewById(R.id.settings_language));
    }

    public void redo_highlightEnglish(){
        MaterialCardView english_cardview = requireView().findViewById(R.id.CardView_english);

        english_cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),80), (int) DimensionsConverter.pxFromDp(requireContext(), 40)));
        english_cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 0f));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.settings_language));
        constraintSet.connect(R.id.CardView_english, ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.CardView_english, ConstraintSet.BOTTOM, R.id.CardView_polish, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.CardView_english, ConstraintSet.START, R.id.CardView_polish, ConstraintSet.END, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        constraintSet.applyTo(requireView().findViewById(R.id.settings_language));
    }
    

}