package com.overmighties.pubber.feature.open;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.app.StartActivity;
import com.overmighties.pubber.app.settings.SettingsHandler;

import java.util.Locale;

public class SplashAnimationFragment  extends Fragment {

    public SplashAnimationFragment(){super(R.layout.fragment_splash_animation);}
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
        ImageView imageView = view.findViewById(R.id.splashAnimation_iv_container);
        if(SettingsHandler.ThemeHelper.getSavedTheme(view.getContext()).equals(SettingsHandler.ThemeHelper.THEME_LIGHT))
            imageView.setImageResource(R.drawable.animation_light_pubber);
        else
            imageView.setImageResource(R.drawable.animation_dark_pubber);

        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        animatedVectorDrawable.start();
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SettingsHandler.FirstTimeOpenHelper.getTimeOpened(view.getContext()).equals(SettingsHandler.FirstTimeOpenHelper.NOT_FIRST_TIME)){
                    SettingsHandler.ThemeHelper.applyTheme(SettingsHandler.ThemeHelper.getSavedTheme(view.getContext()));
                    SettingsHandler.LanguageHelper.setLanguage(SettingsHandler.LanguageHelper.getLanguage(view.getContext()));
                    ((StartActivity)requireActivity()).goToMainActivity();
                }
                viewModel = new ViewModelProvider(requireActivity(),
                        ViewModelProvider.Factory.from(FirstOpenViewModel.initializer)).get(FirstOpenViewModel.class);
                if (Locale.getDefault().getLanguage().equals("en")) {
                    viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.English);
                } else if (Locale.getDefault().getLanguage().equals("uk")) {
                    viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Ukrainian);
                } else {
                    viewModel.getUiState().getValue().setLanguage(FirstOpenViewModel.Language.Polish);
                }
                if (SettingsHandler.ThemeHelper.getSavedTheme(view.getContext()).equals(SettingsHandler.ThemeHelper.THEME_DARK)) {
                    viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Dark);
                } else {
                    viewModel.getUiState().getValue().setTheme(FirstOpenViewModel.Theme.Light);
                }
                navController.navigate(SplashAnimationFragmentDirections.actionSplashAnimationFragmentToWelcomeFragment());

            }
        }, 850);
    }

}
