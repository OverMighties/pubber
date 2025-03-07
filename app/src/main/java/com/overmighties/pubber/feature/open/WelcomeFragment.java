package com.overmighties.pubber.feature.open;

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
import android.widget.TextView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.util.ResourceUtil;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment(){super(R.layout.fragment_welcome);}
    private NavController navController;

    private FirstOpenViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        viewModel=new ViewModelProvider(requireActivity()).get(FirstOpenViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        setUpApperance();
        setUpListeners();
    }

    private void setUpApperance() {
        String languageCode = viewModel.getUiState().getValue().getLanguage().getLanguageCode();
        TextView title = requireView().findViewById(R.id.welcome_text_title);
        TextView content = requireView().findViewById(R.id.welcome_text_content);
        Button next = requireView().findViewById(R.id.welcome_button_next);
        title.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.welcome_in_pubber, languageCode));
        content.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.first_time_info, languageCode));
        next.setText(ResourceUtil.getResStringLanguage(
                requireContext(), R.string.next, languageCode));
        if(!SettingsHandler.ThemeHelper.getSavedTheme(requireContext())
                .equals(viewModel.getUiState().getValue().getTheme().getTheme())){
            requireView().findViewById(R.id.welcome_fragment).setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.opposing_surface));
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
            content.setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_surface));
            next.setBackground(ResourcesCompat.getDrawable(
                    getResources(),R.drawable.button_shape_square_container_opposing, null));
            next.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.opposing_on_secondary_container));
        }
    }

    private void setUpListeners() {
        requireView().findViewById(R.id.welcome_button_next).setOnClickListener(v->{
            navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLanguageFragment());
        });
    }

}