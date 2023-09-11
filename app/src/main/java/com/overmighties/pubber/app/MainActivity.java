package com.overmighties.pubber.app;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.ui.AppBarConfiguration;

import com.overmighties.pubber.R;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.overmighties.pubber.ui.HotPubsFragment;
import com.overmighties.pubber.feature.bookmarks.SavedFragment;
import com.overmighties.pubber.feature.search.SearcherFragment;
import com.overmighties.pubber.util.SortPubsBy;
import com.overmighties.pubber.util.SortUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_saved, R.id.navigation_hot_pubs)
                .build();
        navView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navigation_list) {
                return replaceWithFragment( SearcherFragment.class);
            } else if (item.getItemId() == R.id.navigation_saved) {
                return replaceWithFragment( SavedFragment.class);
            } else if(item.getItemId() == R.id.navigation_hot_pubs) {
                return replaceWithFragment( HotPubsFragment.class);
            }
            return false;
        });

    }
    private  <T extends Fragment> boolean  replaceWithFragment(Class<T> fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment,null)
                .commit();
        return true;
    }



}