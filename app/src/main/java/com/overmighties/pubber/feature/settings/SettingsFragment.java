package com.overmighties.pubber.feature.settings;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.designsystem.UIText;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.app.util.PermissionHandler;
import com.overmighties.pubber.feature.account.AccountViewModel;
import com.overmighties.pubber.feature.search.PubListViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String TAG="SettingsFragment";
    private NavController navController;
    private PubListViewModel pubListViewModel;
    private AccountViewModel accountViewModel;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        accountViewModel = new ViewModelProvider(requireActivity())
                .get(AccountViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        pubListViewModel = pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);

        requireActivity().requireViewById(R.id.main_topAppBarLayout_back).setVisibility(View.VISIBLE);
        ((MaterialToolbar)requireActivity().requireViewById(R.id.main_topAppBarView_back)).setTitle(getString(R.string.settings));

        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);

        SwitchPreferenceCompat notificationPref = findPreference(getString(R.string.notifications_key));
        if(notificationPref!=null){
            notificationPref.setChecked((PermissionHandler.hasPermission(requireContext(), POST_NOTIFICATIONS)==true?true:false));
            notificationPref.setOnPreferenceChangeListener(((preference, newValue) -> {
                //TODO if permission wasn't granted ask for it
                if((boolean) newValue && !PermissionHandler.hasPermission(requireContext(), POST_NOTIFICATIONS)){

                }
                return true;
            }));
        }

        SwitchPreferenceCompat locationPref = findPreference(getString(R.string.localization_key));
        if(locationPref!=null){
            locationPref.setChecked(PermissionHandler.hasLocationPermission(requireContext())==true?true:false);
            locationPref.setOnPreferenceChangeListener(((preference, newValue) -> {
                //TODO if permission wasn't granted ask for it
                if((boolean) newValue && !PermissionHandler.hasLocationPermission(requireContext())){

                }
                return true;
            }));
        }

        ListPreference languagePref = findPreference(getString(R.string.language_key));
        if(languagePref!=null){
            languagePref.setValue(SettingsHandler.LanguageHelper.getLanguage(requireContext()));
            languagePref.setOnPreferenceChangeListener(((preference, newValue) -> {
                resetActivity();
                return true;
            }));
        }

        ListPreference themePref = findPreference(getString(R.string.theme_key));
        if(themePref!=null){
            themePref.setValue(SettingsHandler.ThemeHelper.getSavedTheme(requireContext()));
            themePref.setOnPreferenceChangeListener(((preference, newValue) -> {
                resetActivity();
                return true;
            }));
        }

        Preference aboutUsPref = findPreference(getString(R.string.about_us_key));
        if(aboutUsPref!=null){
            aboutUsPref.setOnPreferenceClickListener((v)->{
                navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToSettingsAboutFragment());
                return true;
            });
        }

        Preference feedbackPref = findPreference(getString(R.string.send_feedback_key));
        if (feedbackPref != null) {
            feedbackPref.setOnPreferenceClickListener((v)->{
                navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToSettingsMessageFragment());
                return true;
            });
        }

        Preference accountInfoPref = findPreference(getString(R.string.account_key));
        if(accountInfoPref!=null){
            accountInfoPref.setOnPreferenceClickListener((v)->{
                requireActivity().requireViewById(R.id.main_topAppBarLayout_back).setVisibility(View.GONE);
                navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountDetailsFragment());
                return true;
            });
        }

        Preference logOutPref = findPreference(getString(R.string.account_log_out_key));
        if(logOutPref!=null){
            logOutPref.setOnPreferenceClickListener((v)->{
                navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountDetailsFragment());
                accountViewModel.onSignOutClick(
                        (from,to)-> navController.navigate(getNavDirections(from,to)),
                        (errorType, uiText,logMes) -> showSnackbar(view,errorType,(UIText.ResourceString)uiText,logMes));
                return true;
            });
        }

    }

    private void resetActivity(){
        //TODO when I reset activity context in CustomListPreference doesn't update so that it shows it in previours theme possible sollution is to restart activity with recrate and pass data in viewmodel or in sharedpreference. To be decided how to fix it
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.putExtra("openSettings", true);
        intent.putExtra("city", pubListViewModel.getCityConstraint().getValue());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.main_bottomNavView).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.main_topAppBarLayout_back).setVisibility(View.VISIBLE);

    }
    @Override
    public void onStop(){
        super.onStop();
        ((MaterialToolbar)requireActivity().requireViewById(R.id.main_topAppBarView_back)).setTitle(null);
    }
}
