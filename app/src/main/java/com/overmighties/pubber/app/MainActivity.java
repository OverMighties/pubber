package com.overmighties.pubber.app;

import static androidx.navigation.ui.BottomNavigationViewKt.setupWithNavController;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.overmighties.pubber.R;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.overmighties.pubber.feature.hotpubs.HotPubsFragment;
import com.overmighties.pubber.feature.bookmarks.SavedFragment;
import com.overmighties.pubber.feature.search.SearcherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.overmighties.pubber.feature.search.SearcherFragmentDirections;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController= ( (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView) ).getNavController();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView,navController);
    }

}