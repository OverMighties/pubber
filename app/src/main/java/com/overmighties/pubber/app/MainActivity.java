package com.overmighties.pubber.app;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.overmighties.pubber.feature.account.AccountViewModel;
import com.overmighties.pubber.feature.search.PubListViewModel;


public class MainActivity extends AppCompatActivity {

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
        pubListViewModel.setCityConstraint(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController= ( (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment) ).getNavController();

        MaterialToolbar topAppBar = findViewById(R.id.top_app_bar_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        AppBarConfiguration  bottomNavConfiguration =
                new AppBarConfiguration.Builder(R.id.SearcherFragment,R.id.HotPubsFragment,R.id.SavedFragment).build();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.layout.fragment_account_details,R.layout.fragment_settings).build();
        setSupportActionBar(topAppBar);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(topAppBar,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);
        bottomNavSize();
    }
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue = super.onCreateOptionsMenu(menu);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        
        if (navigationView == null) {
            getMenuInflater().inflate(R.menu.top_app_bar, menu);
            return true;
        }
        return retValue;
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
//        int navigationBarHeight=resources.getDimensionPixelSize(resourceId);
//
//        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
//
//        if (hasBackKey && hasHomeKey) {
//            BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.nav_view);
//            navBar.setLayoutParams(new BottomNavigationView.LayoutParams(navBar.getLayoutParams().width, navBar.getLayoutParams().height-20));
//        } else {
//            BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.nav_view);
//            navBar.setLayoutParams(new BottomNavigationView.LayoutParams(navBar.getLayoutParams().width, navBar.getLayoutParams().height-200));
//        }


    }

}