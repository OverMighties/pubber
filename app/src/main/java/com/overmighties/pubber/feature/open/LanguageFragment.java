package com.overmighties.pubber.feature.open;

import static com.overmighties.pubber.app.Constants.LANGUAGE_FRAGMENT_RADIO_BUTTONS_IDS;

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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.util.ResourceUtil;

public class LanguageFragment extends Fragment {


    public static final String TAG="LanguageFragment";
    private NavController navController;
    private FirstOpenViewModel viewModel;

    public LanguageFragment(){super(R.layout.fragment_language);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        viewModel=new ViewModelProvider(requireActivity()).get(FirstOpenViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        switch( viewModel.getUiState().getValue().getLanguage().getLanguageCode()){
            case "pl":
                ((RadioButton)requireView().findViewById(R.id.language_radioButton_polish)).setChecked(true);
                break;
            case "en":
                ((RadioButton)requireView().findViewById(R.id.language_radioButton_english)).setChecked(true);
                break;
            case "uk":
                ((RadioButton)requireView().findViewById(R.id.language_radioButton_ukrainian)).setChecked(true);
                break;
        }
        setUpApperance();
        setUpListeners();
    }

    private void setUpApperance() {
        //Text change
        String languageCode = viewModel.getUiState().getValue().getLanguage().getLanguageCode();
        TextView title = requireView().findViewById(R.id.language_text_title);
        Button back = requireView().findViewById(R.id.language_button_back);
        Button next = requireView().findViewById(R.id.language_button_next);
        title.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.choose_language, languageCode));
        back.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.back, languageCode));
        next.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.next, languageCode));
        //theme
        Integer onSurfaceId;
        Integer onSurfaceVariantId;
        Integer primaryId;
        Integer surface;
        Integer onSecondaryContainer;
        Integer buttonDrawable;
        if(!SettingsHandler.ThemeHelper.getSavedTheme(requireContext())
                .equals(viewModel.getUiState().getValue().getTheme().getTheme())) {
            onSurfaceId = R.color.opposing_on_surface;
            onSurfaceVariantId = R.color.opposing_on_surface_variant;
            primaryId = R.color.opposing_primary;
            surface = R.color.opposing_surface;
            onSecondaryContainer = R.color.opposing_on_secondary_container;
            buttonDrawable = R.drawable.button_shape_square_container_opposing;

        } else{
            onSurfaceId = R.color.on_surface;
            onSurfaceVariantId = R.color.on_surface_variant;
            primaryId = R.color.primary;
            surface = R.color.surface;
            onSecondaryContainer = R.color.on_secondary_container;
            buttonDrawable = R.drawable.button_shape_square_container;
        }
        for(var id:LANGUAGE_FRAGMENT_RADIO_BUTTONS_IDS){
            RadioButton radioButton = requireView().findViewById(id);
            if(!radioButton.isChecked()) {
                radioButton.setTextColor(ContextCompat.getColor(requireContext(), onSurfaceId));
                radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), onSurfaceVariantId)));
            }else{
                radioButton.setTextColor(ContextCompat.getColor(requireContext(), primaryId));
                radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), primaryId)));
            }
        }
        requireView().findViewById(R.id.language_fragment).
                setBackgroundColor(ContextCompat.getColor(requireContext(), surface));
        title.setTextColor(ContextCompat.getColor(requireContext(), onSurfaceId));
        back.setBackground(ResourcesCompat.getDrawable(getResources(),buttonDrawable, null));
        back.setTextColor(ContextCompat.getColor(requireContext(), onSecondaryContainer));
        next.setBackground(ResourcesCompat.getDrawable(getResources(),buttonDrawable, null));
        next.setTextColor(ContextCompat.getColor(requireContext(), onSecondaryContainer));
    }


    private void setUpListeners() {
        requireView().findViewById(R.id.language_button_next).setOnClickListener(v->{
            navController.navigate(LanguageFragmentDirections.actionLanguageFragmentToThemeFragment());
        });
        requireView().findViewById(R.id.language_button_back).setOnClickListener(v->{
            navController.popBackStack();
        });

        RadioButton polish = requireView().findViewById(R.id.language_radioButton_polish);
        RadioButton english = requireView().findViewById(R.id.language_radioButton_english);
        RadioButton ukrainian = requireView().findViewById(R.id.language_radioButton_ukrainian);
        polish.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                english.setChecked(false);
                ukrainian.setChecked(false);
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Polish);
                setUpApperance();
            }
        }));
        english.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                polish.setChecked(false);
                ukrainian.setChecked(false);
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.English);
                setUpApperance();
            }
        }));
        ukrainian.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                english.setChecked(false);
                polish.setChecked(false);
                viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Ukrainian);
                setUpApperance();
            }
        }));
    }

}