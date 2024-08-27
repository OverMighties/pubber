package com.overmighties.pubber.feature.firstopen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.feature.settings.SettingsHandler;

public class LanguageFragment extends Fragment {

    private LanguageViewModel mViewModel;

    public static final String TAG="LanguageFragment";
    private NavController navController;

    private enum Highlight_type{
        polish,
        ukrainian,
        english
    }
    //TODO check system's language and determine which one should be checked at start

    public LanguageFragment(){super(R.layout.fragment_language);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.buttonNextT).setOnClickListener(v->{
            navController.navigate(LanguageFragmentDirections.actionLanguageFragmentToThemeFragment());
        });
        requireView().findViewById(R.id.CardView_polish).setOnClickListener(v->{
            highlight(Highlight_type.polish);
            unHigliht(Highlight_type.english);
            unHigliht(Highlight_type.ukrainian);
            changeLanguage(Highlight_type.polish);
            SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_POLISH);

        });
        requireView().findViewById(R.id.CardView_english).setOnClickListener(v->{
            highlight(Highlight_type.english);
            unHigliht(Highlight_type.polish);
            unHigliht(Highlight_type.ukrainian);
            SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_ENGLISH);
            changeLanguage(Highlight_type.english);
        });
        requireView().findViewById(R.id.CardView_ukrainian).setOnClickListener(v->{
            highlight(Highlight_type.ukrainian);
            unHigliht(Highlight_type.english);
            unHigliht(Highlight_type.polish);
        });
    }

    public void highlight(Highlight_type type){
        MaterialCardView cardview;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.language_fragment));
        TextView textView;
        int width = 80;
        int height = 44;
        int top_id = 0;
        int top_margin = 0;
        if (type == Highlight_type.english) {
            cardview = requireView().findViewById(R.id.CardView_english);
            width = 84;
            textView = requireView().findViewById(R.id.TV_english);
        }
        else if(type == Highlight_type.ukrainian){
            cardview = requireView().findViewById(R.id.CardView_ukrainian);
            width = 64;
            textView = requireView().findViewById(R.id.TV_ukrainian);
        }
        else{
            cardview = requireView().findViewById(R.id.CardView_polish);
            width = 68;
            textView = requireView().findViewById(R.id.TV_polish);
        }
        cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),width), (int) DimensionsConverter.pxFromDp(requireContext(), height)));
        cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 2.5f));
        cardview.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.primary));

        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.language_fragment));
        constraintSet.connect(cardview.getId(), ConstraintSet.TOP, top_id, ConstraintSet.TOP, (int) DimensionsConverter.pxFromDp(requireContext(), top_margin));
        if (type == Highlight_type.english)
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.CardView_polish, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.language_fragment, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP);
        }
        else if(type == Highlight_type.ukrainian)
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.CardView_polish, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.language_fragment, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP);
        }
        else
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.language_fragment, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.language_fragment, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.textView5, ConstraintSet.BOTTOM, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        }
        constraintSet.applyTo(requireView().findViewById(R.id.language_fragment));

        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
    }

    public void unHigliht(Highlight_type type){
        MaterialCardView cardview;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.language_fragment));
        TextView textView;
        int width = 80;
        int height = 40;
        int top_id = 0;
        int top_margin = 0;
        if (type == Highlight_type.english) {
            cardview = requireView().findViewById(R.id.CardView_english);
            width = 80;
            textView = requireView().findViewById(R.id.TV_english);
        }
        else if(type == Highlight_type.ukrainian){
            cardview = requireView().findViewById(R.id.CardView_ukrainian);
            width = 60;
            textView = requireView().findViewById(R.id.TV_ukrainian);
        }
        else{
            cardview = requireView().findViewById(R.id.CardView_polish);
            width = 64;
            textView = requireView().findViewById(R.id.TV_polish);
        }
        cardview.setLayoutParams(new ConstraintLayout.LayoutParams(
                (int) DimensionsConverter.pxFromDp(requireContext(),width), (int) DimensionsConverter.pxFromDp(requireContext(), height)));
        cardview.setStrokeWidth((int) DimensionsConverter.pxFromDp(requireContext(), 0));
        cardview.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.primary));

        constraintSet.clone((ConstraintLayout) requireView().findViewById(R.id.language_fragment));
        constraintSet.connect(cardview.getId(), ConstraintSet.TOP, top_id, ConstraintSet.TOP, (int) DimensionsConverter.pxFromDp(requireContext(), top_margin));
        if (type == Highlight_type.english)
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.CardView_polish, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.language_fragment, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP);
        }
        else if(type == Highlight_type.ukrainian)
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.CardView_polish, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.language_fragment, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.CardView_polish, ConstraintSet.TOP);
        }
        else
        {
            constraintSet.connect(cardview.getId(), ConstraintSet.START, R.id.language_fragment, ConstraintSet.START);
            constraintSet.connect(cardview.getId(), ConstraintSet.END, R.id.language_fragment, ConstraintSet.END);
            constraintSet.connect(cardview.getId(), ConstraintSet.TOP, R.id.textView5, ConstraintSet.BOTTOM, (int) DimensionsConverter.pxFromDp(requireContext(), 16));
        }
        constraintSet.applyTo(requireView().findViewById(R.id.language_fragment));

        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
    }

    public void changeLanguage(Highlight_type type){
        String textTV = "";
        String textB = "";
        if (type == Highlight_type.english){
            textTV = "Choose language";
            textB = "Next";
        }
        else if(type == Highlight_type.ukrainian){

        }
        else{
            textTV = "Wybierz język";
            textB = "Dalej";
        }
        ((TextView)requireView().findViewById(R.id.textView5)).setText(textTV);
        ((Button)requireView().findViewById(R.id.buttonNextT)).setText(textB);
    }


}