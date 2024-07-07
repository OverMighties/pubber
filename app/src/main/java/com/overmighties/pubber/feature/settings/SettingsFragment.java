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
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.feature.account.AccountDetailsFragment;
import com.overmighties.pubber.util.ThemeHelper;

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
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        // Load the child fragment
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.account_details_container_view, new AccountDetailsFragment())
                    .commit();
        }

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        if (ThemeHelper.getSavedTheme(requireContext())==1) {
            ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setChecked(true);
        }
        else{
            ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setChecked(false);
        }
        ((Switch) requireView().findViewById(R.id.dark_mode_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int currentTheme = ThemeHelper.getSavedTheme(requireContext());
                int newTheme = (currentTheme == ThemeHelper.THEME_LIGHT) ? ThemeHelper.THEME_DARK : ThemeHelper.THEME_LIGHT;
                ThemeHelper.saveTheme(requireContext(), newTheme);

                // Restart the application to apply the new theme
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("openFragmentE", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

    }
}
