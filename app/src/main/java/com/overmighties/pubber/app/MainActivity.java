package com.overmighties.pubber.app;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.ui.AppBarConfiguration;

import com.overmighties.pubber.R;
import com.overmighties.pubber.data.PubData;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.overmighties.pubber.ui.HotPubsFragment;
import com.overmighties.pubber.ui.SavedFragment;
import com.overmighties.pubber.ui.SearcherFragment;
import com.overmighties.pubber.util.SortUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String FILE_NAME = "saved.txt";

    private HotPubsFragment hotPubsFragment;
    private SavedFragment savedFragment;
    private SearcherFragment searcherFragment;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searcherFragment = new SearcherFragment();
        savedFragment = new SavedFragment();
        hotPubsFragment = new HotPubsFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_saved, R.id.navigation_hot_pubs)
                .build();
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_list) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searcherFragment).commit();
                    return true;
                } else {
                    if (item.getItemId() == R.id.navigation_saved) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, savedFragment).commit();
                        return true;
                    } else {
                        if (item.getItemId() == R.id.navigation_hot_pubs) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, hotPubsFragment).commit();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Observer<String> name = sort -> {
            if (AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue().equals("nie")) {}else{
                NavigationBar.smoothHide(findViewById(R.id.nav_view));
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.sort_pop_up, null);
                PopupWindow popupWindow = new PopupWindow(popUpView,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, true);


                listeners(popupWindow, popUpView);
                //checking which one kind of sorting is selected
                check(popUpView);


                (findViewById(R.id.search)).post(new Runnable() {
                    @Override
                    public void run() {


                        popupWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        };


        AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().observe(this, name);
        /*
        final Observer<String> name = save -> {
                if((AppContainer.getInstance().getPubSearchingContainer().getSavedlist().getValue()).equals(""))
                {
                    load();
                    Log.d("tak",AppContainer.getInstance().getPubSearchingContainer().getSavedlist().getValue());
                }
                else
                {
                    try {
                        Log.d("tak",AppContainer.getInstance().getPubSearchingContainer().getSavedlist().getValue());
                        save(AppContainer.getInstance().getPubSearchingContainer().getSavedlist().getValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        };
        AppContainer.getInstance().getPubSearchingContainer().getSavedlist().observe(this, name);

         */

    }

    private void check(View popUpView) {
        String word = ((TextView) findViewById(R.id.sorttext)).getText().toString();
        if (word.equals(" Trafność")) {
            ((RadioButton) popUpView.findViewById(R.id.trafnosc)).setChecked(true);
        } else {
            if (word.equals(" Alfabetycznie")) {
                ((RadioButton) popUpView.findViewById(R.id.alfabetycznie)).setChecked(true);
            } else {
                if (word.equals(" Najwyżej oceniane")) {
                    ((RadioButton) popUpView.findViewById(R.id.ocena)).setChecked(true);
                } else {
                    ((RadioButton) popUpView.findViewById(R.id.najodleglosc)).setChecked(true);
                }
            }
        }
    }

    private void listeners(PopupWindow popupWindow, View popupView) {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                NavigationBar.smoothPopUp(findViewById(R.id.nav_view));
                TextView text = findViewById(R.id.sorttext);
                SortUtil sort = new SortUtil();
                if (((RadioButton) popupView.findViewById(R.id.trafnosc)).isChecked()) {
                    text.setText(" Trafność");
                    sort.sortUtli(1);
                } else {
                    if (((RadioButton) popupView.findViewById(R.id.ocena)).isChecked()) {
                        text.setText(" Najwyżej oceniane");
                        sort.sortUtli(3);

                    } else {
                        if (((RadioButton) popupView.findViewById(R.id.alfabetycznie)).isChecked()) {
                            text.setText(" Alfabetycznie");
                            sort.sortUtli(2);
                        } else {
                            text.setText(" Najmniejsza Odległość");
                            sort.sortUtli(4);
                        }
                    }
                }


            }
        });
        ((ConstraintLayout) popupView.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ((RadioButton) popupView.findViewById(R.id.trafnosc)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.trafnosc)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.ocena)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.najodleglosc)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.alfabetycznie)).setChecked(false);
                } else {
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.ocena)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.ocena)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.trafnosc)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.najodleglosc)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.alfabetycznie)).setChecked(false);
                } else {
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.najodleglosc)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.najodleglosc)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.ocena)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.trafnosc)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.alfabetycznie)).setChecked(false);
                } else {
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.alfabetycznie)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.alfabetycznie)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.ocena)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.najodleglosc)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.trafnosc)).setChecked(false);
                } else {
                }
            }
        });
    }
}