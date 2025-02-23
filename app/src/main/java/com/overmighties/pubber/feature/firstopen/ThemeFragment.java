package com.overmighties.pubber.feature.firstopen;

import static com.overmighties.pubber.app.Constants.THEME_FRAGMENT_RADIO_BUTTONS_IDS;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.StartActivity;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.util.ResourceUtil;

public class ThemeFragment extends Fragment {


    public static final String TAG="ThemeFragment";
    private Theme activityTheme;
    private NavController navController;

    private FirstOpenViewModel viewModel;

    private enum Theme{
        dark,
        light
    }

    public ThemeFragment(){super(R.layout.fragment_theme);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        viewModel=new ViewModelProvider(requireActivity()).get(FirstOpenViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        switch( viewModel.getUiState().getValue().getTheme().getTheme()){
            case "null":
                ((RadioButton)requireView().findViewById(R.id.theme_radioButton_phone)).setChecked(true);
                break;
            case SettingsHandler.ThemeHelper.THEME_DARK:
                ((RadioButton)requireView().findViewById(R.id.theme_radioButton_dark)).setChecked(true);
                break;
            case SettingsHandler.ThemeHelper.THEME_LIGHT:
                ((RadioButton)requireView().findViewById(R.id.theme_radioButton_light)).setChecked(true);
                break;
        }
        setUpApperance();
        setUpListeners();
    }

    private void setUpApperance() {
        //Text change
        String languageCode = viewModel.getUiState().getValue().getLanguage().getLanguageCode();
        TextView title = requireView().findViewById(R.id.theme_text_title);
        Button back = requireView().findViewById(R.id.theme_button_back);
        Button next = requireView().findViewById(R.id.theme_button_next);
        RadioButton phone = requireView().findViewById(R.id.theme_radioButton_phone);
        RadioButton dark = requireView().findViewById(R.id.theme_radioButton_dark);
        RadioButton light = requireView().findViewById(R.id.theme_radioButton_light);
        title.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.choose_theme, languageCode));
        back.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.back, languageCode));
        next.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.next, languageCode));
        phone.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.theme, languageCode));
        dark.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.dark, languageCode));
        light.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.light, languageCode));
        //theme
        Integer onSurfaceId;
        Integer onSurfaceVariantId;
        Integer primaryId;
        Integer statusBarId;
        Integer navigationBarId;
        Integer surface;
        Integer onSecondaryContainer;
        Integer buttonDrawable;
        if(!SettingsHandler.ThemeHelper.getSavedTheme(requireContext())
                .equals(viewModel.getUiState().getValue().getTheme().getTheme())) {
            onSurfaceId = R.color.opposing_on_surface;
            onSurfaceVariantId = R.color.opposing_on_surface_variant;
            primaryId = R.color.opposing_primary;
            statusBarId = R.color.opposing_status_bar;
            navigationBarId = R.color.opposing_surface_variant_secondary;
            surface = R.color.opposing_surface;
            onSecondaryContainer = R.color.opposing_on_secondary_container;
            buttonDrawable = R.drawable.button_shape_square_container_opposing;

        } else{
            onSurfaceId = R.color.on_surface;
            onSurfaceVariantId = R.color.on_surface_variant;
            primaryId = R.color.primary;
            statusBarId = R.color.status_bar;
            navigationBarId = R.color.surface_variant_secondary;
            surface = R.color.surface;
            onSecondaryContainer = R.color.on_secondary_container;
            buttonDrawable = R.drawable.button_shape_square_container;
        }
        for(var id:THEME_FRAGMENT_RADIO_BUTTONS_IDS){
            RadioButton radioButton = requireView().findViewById(id);
            if(!radioButton.isChecked()) {
                radioButton.setTextColor(ContextCompat.getColor(requireContext(), onSurfaceId));
                radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), onSurfaceVariantId)));
            }else{
                radioButton.setTextColor(ContextCompat.getColor(requireContext(), primaryId));
                radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), primaryId)));
            }
        }
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(requireContext(), statusBarId));
        window.setNavigationBarColor(ContextCompat.getColor(requireContext(), navigationBarId));
        requireView().findViewById(R.id.theme_fragment).
                setBackgroundColor(ContextCompat.getColor(requireContext(), surface));
        title.setTextColor(ContextCompat.getColor(requireContext(), onSurfaceId));
        back.setBackground(ResourcesCompat.getDrawable(getResources(),buttonDrawable, null));
        back.setTextColor(ContextCompat.getColor(requireContext(), onSecondaryContainer));
        next.setBackground(ResourcesCompat.getDrawable(getResources(),buttonDrawable, null));
        next.setTextColor(ContextCompat.getColor(requireContext(), onSecondaryContainer));
    }


    private void setUpListeners() {
        requireView().findViewById(R.id.theme_button_next).setOnClickListener(v->{
            SettingsHandler.FirstTimeOpenHelper.setSecond(requireContext());
            SettingsHandler.ThemeHelper.saveTheme(requireContext(), viewModel.getUiState().getValue().getTheme().getTheme());
            SettingsHandler.LanguageHelper.saveLanguage(requireContext(), viewModel.getUiState().getValue().getLanguage().getNativeName());
            ((StartActivity)getActivity()).goToMainActivity();
        });
        requireView().findViewById(R.id.theme_button_back).setOnClickListener(v->{
            navController.popBackStack();
        });

        RadioButton phone = requireView().findViewById(R.id.theme_radioButton_phone);
        RadioButton dark = requireView().findViewById(R.id.theme_radioButton_dark);
        RadioButton light = requireView().findViewById(R.id.theme_radioButton_light);
        phone.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                dark.setChecked(false);
                light.setChecked(false);
                viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme
                        .getThemeByName(SettingsHandler.ThemeHelper.getDefaultTheme(requireContext())));
                setUpApperance();
            }
        }));
        dark.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                phone.setChecked(false);
                light.setChecked(false);
                viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Dark);
                setUpApperance();
            }
        }));
        light.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                dark.setChecked(false);
                phone.setChecked(false);
                viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Light);
                setUpApperance();
            }
        }));
    }

}