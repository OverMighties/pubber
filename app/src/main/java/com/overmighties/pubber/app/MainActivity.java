package com.overmighties.pubber.app;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowInsets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.overmighties.pubber.feature.search.PubListViewModel;


public class MainActivity extends AppCompatActivity {

    public static final String TAG="MainActivity";
    private ActivityMainBinding binding;
    private PubListViewModel viewModel;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        viewModel.setCityConstraint(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController= ( (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView) ).getNavController();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView,navController);
        bottomNavSize();
    }

    void bottomNavSize()
    {

        int statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier("status_bar_height", "dimen", "android"));

        Log.i("*** Elenasys :: ", "StatusBar Height= " + statusBarHeight);

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            Log.i("takk",String.valueOf(resources.getDimensionPixelSize(resourceId)));
        }
        int navigationBarHeight=resources.getDimensionPixelSize(resourceId);

        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        if (hasBackKey && hasHomeKey) {
          //  BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.nav_view);
           // navBar.setLayoutParams(new BottomNavigationView.LayoutParams(navBar.getLayoutParams().width, navBar.getLayoutParams().height-20));
        } else {
          //  BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.nav_view);
           // navBar.setLayoutParams(new BottomNavigationView.LayoutParams(navBar.getLayoutParams().width, navBar.getLayoutParams().height-200));
        }


    }

}