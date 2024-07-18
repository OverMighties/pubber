package com.overmighties.pubber.app;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.overmighties.pubber.R;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.overmighties.pubber.feature.account.AccountViewModel;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.app.ui.SettingsBasicActivity;

import java.util.Objects;


public class MainActivity extends SettingsBasicActivity {

    public static final String TAG="MainActivity";
    private ActivityMainBinding binding;
    private PubListViewModel pubListViewModel;
    private AccountViewModel accountViewModel;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pubListViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        accountViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(AccountViewModel.initializer))
                .get(AccountViewModel.class);
//        pubListViewModel.setCityConstraint(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        pubListViewModel.getPubsFromRepo(0);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController= ( (NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).getNavController();

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
//      AppBarConfiguration  bottomNavConfiguration =
//                new AppBarConfiguration.Builder(R.id.SearcherFragment, R.id.SavedFragment, R.id.SettingsFragment).build();
        NavigationUI.setupWithNavController(bottomNavView, navController);
        bottomNavSize();
        if ((getIntent().hasExtra("openSettings") && getIntent().getBooleanExtra("openSettings", false))) {
            bottomNavView.setSelectedItemId(bottomNavView.getMenu().getItem(2).getItemId());
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = ( (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment) ).getNavController();
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    void bottomNavSize()
    {
        int statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier("status_bar_height", "dimen", "android"));

        Log.i(TAG, "StatusBar Height= " + statusBarHeight);

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            Log.i(TAG,"tak: "+ resources.getDimensionPixelSize(resourceId));
        }


    }


}