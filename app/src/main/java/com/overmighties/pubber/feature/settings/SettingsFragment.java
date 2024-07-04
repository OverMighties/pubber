package com.overmighties.pubber.feature.settings;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.feature.account.AccountDetailsFragment;
import com.overmighties.pubber.util.UIText;

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
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        // Load the child fragment
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.account_details_container_view, new AccountDetailsFragment())
                    .commit();
        }
    }
}
