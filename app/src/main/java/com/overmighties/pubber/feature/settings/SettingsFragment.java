package com.overmighties.pubber.feature.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.util.SettingsHandler;

public class SettingsFragment extends Fragment {
    public static final String TAG="SettingsFragment";
    private SettingsViewModel viewModel;
    private NavController navController;

    public SettingsFragment(){
        super(R.layout.fragment_settings);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"on create");
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);


    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);



        if (SettingsHandler.ThemeHelper.getSavedTheme(requireContext())==1) {
            ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setChecked(true);
        }
        else{
            ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setChecked(false);
        }

        ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newTheme = (isChecked) ? SettingsHandler.ThemeHelper.THEME_DARK : SettingsHandler.ThemeHelper.THEME_LIGHT;
                SettingsHandler.ThemeHelper.saveTheme(requireContext(), newTheme);

                // Restart the application to apply the new theme
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("openSettings", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        if(SettingsHandler.NotificationsHelper.getNotificationState(requireContext()) == SettingsHandler.NotificationsHelper.NOTIFICATIONS_ON){
            ((Switch)requireView().findViewById(R.id.notification_switch)).setChecked(true);
        }
        else{
            ((Switch)requireView().findViewById(R.id.notification_switch)).setChecked(false);

        }

        ((Switch)requireView().findViewById(R.id.notification_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newState = (isChecked) ? SettingsHandler.NotificationsHelper.NOTIFICATIONS_ON : SettingsHandler.NotificationsHelper.NOTIFICATIONS_OFF;
                SettingsHandler.NotificationsHelper.saveNotificationState(requireContext(), newState);
            }
        });


        requireView().findViewById(R.id.account_IV).setOnClickListener(v->{
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountDetailsFragment());
        });

        requireView().findViewById(R.id.language_go_to).setOnClickListener(v->{
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToSettingsLanguageFragment());
        });

        requireView().findViewById(R.id.send_message_go_to).setOnClickListener(v->{
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToSettingsMessageFragment());
        });

        requireView().findViewById(R.id.about_us_go_to).setOnClickListener(v->{
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToSettingsAboutFragment());
        });


    }

    @Override
    public void onResume() {

        super.onResume();
        if(getActivity().findViewById(R.id.bottom_nav_view).getVisibility()==View.GONE)
            NavigationBar.smoothPopUp(getActivity().findViewById(R.id.bottom_nav_view));

    }
}
