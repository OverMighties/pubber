package com.overmighties.pubber.feature.firstopen;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.StartActivity;
import com.overmighties.pubber.app.settings.SettingsHandler;

public class ThemeFragment extends Fragment {

    private ThemeViewModel mViewModel;

    public static final String TAG="ThemeFragment";
    private Theme activityTheme;
    private NavController navController;

    private enum Theme{
        dark,
        light
    }

    // TODO change radio buttons to diffrent widget cause i cannot programically change their color to fit theme
    public ThemeFragment(){super(R.layout.fragment_theme);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        if(SettingsHandler.ThemeHelper.getSavedTheme(requireContext()) == SettingsHandler.ThemeHelper.THEME_DARK){
            activityTheme= Theme.dark;
            ((RadioButton)requireView().findViewById(R.id.radioButton_dark_f)).setChecked(true);
            changeTheme(Theme.dark);
        }
        else{
            activityTheme = Theme.light;
            ((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).setChecked(true);
            changeTheme(Theme.light);
        }

        requireView().findViewById(R.id.radioButton_dark_f).setOnTouchListener((v, event)->{
            if(!((RadioButton)requireView().findViewById(R.id.radioButton_dark_f)).isChecked() && event.getAction() == MotionEvent.ACTION_UP ){
                SettingsHandler.ThemeHelper.saveTheme(requireContext(), SettingsHandler.ThemeHelper.THEME_DARK);
                ((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).setChecked(false);
                changeTheme(Theme.dark);
            }
            return false;
        });

        requireView().findViewById(R.id.radioButton_light_f).setOnTouchListener((v, event)->{
            if(!((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).isChecked() && event.getAction() == MotionEvent.ACTION_UP ){
                SettingsHandler.ThemeHelper.saveTheme(requireContext(), SettingsHandler.ThemeHelper.THEME_LIGHT);
                ((RadioButton)requireView().findViewById(R.id.radioButton_dark_f)).setChecked(false);
                changeTheme(Theme.light);
            }
            return false;
        });

        requireView().findViewById(R.id.buttonNextEnd).setOnClickListener(v->{
            SettingsHandler.FirstTimeOpenHelper.setSecond(requireContext());
            ((StartActivity)getActivity()).goToMainActivity();
        });
    }

    private void changeTheme(Theme theme) {
        if ((activityTheme == Theme.dark && theme == Theme.dark) || (activityTheme == Theme.light && theme == Theme.light))
        {
            ((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).setBackgroundTintList(ColorStateList.valueOf(R.color.radio_button_selector));
            ((RadioButton)requireView().findViewById(R.id.radioButton_dark_f)).setBackgroundTintList(ColorStateList.valueOf(R.color.radio_button_selector));
            requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.status_bar));
            requireActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(requireContext(), R.color.surface_variant_secondary));
            ((ConstraintLayout)requireView().findViewById(R.id.fragment_theme)).setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface));
            ((TextView)requireView().findViewById(R.id.textView7)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
            ((TextView)requireView().findViewById(R.id.textView8)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
            if (theme == Theme.dark) {
                ((TextView)requireView().findViewById(R.id.TV_light_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
                ((TextView)requireView().findViewById(R.id.TV_dark_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
            }
            else {
                ((TextView) requireView().findViewById(R.id.TV_light_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
                ((TextView) requireView().findViewById(R.id.TV_dark_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface));
            }
            ((Button)requireView().findViewById(R.id.button)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_secondary_container));
            ((Button)requireView().findViewById(R.id.button)).setBackground(getResources().getDrawable(R.drawable.button_shape_container));
            ((Button)requireView().findViewById(R.id.buttonNextEnd)).setTextColor(ContextCompat.getColor(requireContext(), R.color.on_secondary_container));
            ((Button)requireView().findViewById(R.id.buttonNextEnd)).setBackground(getResources().getDrawable(R.drawable.button_shape_square_container));
        }
        else
        {
            ((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).setButtonTintList(ColorStateList.valueOf(R.color.radio_button_selector_opposing));
            ((RadioButton)requireView().findViewById(R.id.radioButton_light_f)).invalidate();
            ((RadioButton)requireView().findViewById(R.id.radioButton_dark_f)).setBackgroundTintList(ColorStateList.valueOf(R.color.radio_button_selector_opposing));
            requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.opposing_status_bar));
            requireActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(requireContext(), R.color.opposing_surface_variant_secondary));
            ((ConstraintLayout)requireView().findViewById(R.id.fragment_theme)).setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.opposing_surface));
            ((TextView)requireView().findViewById(R.id.textView7)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
            ((TextView)requireView().findViewById(R.id.textView8)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
            if (theme == Theme.dark) {
                ((TextView)requireView().findViewById(R.id.TV_light_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
                ((TextView)requireView().findViewById(R.id.TV_dark_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_primary));
            }
            else {
                ((TextView) requireView().findViewById(R.id.TV_light_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_primary));
                ((TextView) requireView().findViewById(R.id.TV_dark_f)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
            }
            ((Button)requireView().findViewById(R.id.button)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_secondary_container));
            ((Button)requireView().findViewById(R.id.button)).setBackground(getResources().getDrawable(R.drawable.button_shape_container_opposing));
            ((Button)requireView().findViewById(R.id.buttonNextEnd)).setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_secondary_container));
            ((Button)requireView().findViewById(R.id.buttonNextEnd)).setBackground(getResources().getDrawable(R.drawable.button_shape_square_container_opposing));
        }
    }


}