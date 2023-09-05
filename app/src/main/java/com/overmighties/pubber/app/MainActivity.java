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

    public static final String FILE_NAME = "saved.txt";
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
         final Observer<String> name = sort -> {
            if (!AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue().equals("nie")){
                NavigationBar.smoothHide(findViewById(R.id.nav_view));
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.sort_pop_up, null);
                PopupWindow sortPubsPopUpWindow = new PopupWindow(popUpView,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, true);
                listenersForSortingPopUpWindow(sortPubsPopUpWindow, popUpView);
                //checking which one kind of sorting is selected
                checkCurrentSortRadioButton(popUpView);
                (findViewById(R.id.search)).post(() -> sortPubsPopUpWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM, 0, 0));
            }
        };
        AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().observe(this, name);
    }
    private  <T extends Fragment> boolean  replaceWithFragment(Class<T> fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment,null)
                .commit();
        return true;
    }
    private  String getStringById(int id)
    {
        return getResources().getString(id);
    }

    private void checkCurrentSortRadioButton(View popupView) {
        String word = ((TextView) findViewById(R.id.textViewSortType_searcher)).getText().toString();
        if(getStringById(R.string.sort_relevance).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(true);
        }
        if(getStringById(R.string.sort_rating).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(true);
        }
        if(getStringById(R.string.sort_alphabetical).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(true);
        }
        if(getStringById(R.string.sort_distance).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(true);
        }

    }

    private void listenersForSortingPopUpWindow(PopupWindow popupWindow, View popupView) {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                NavigationBar.smoothPopUp(findViewById(R.id.nav_view));
                TextView text = findViewById(R.id.textViewSortType_searcher);
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).isChecked()) {
                    text.setText( getStringById(R.string.sort_relevance));
                    SortUtil.sortingPubData(SortPubsBy.RELEVANCE);
                } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).isChecked()) {
                    text.setText(getStringById(R.string.sort_rating));
                    SortUtil.sortingPubData(SortPubsBy.RATING);
                } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).isChecked()) {
                    text.setText(getStringById(R.string.sort_alphabetical));
                    SortUtil.sortingPubData(SortPubsBy.ALPHABETICAL);
                } else{
                    text.setText(getStringById(R.string.sort_distance));
                    SortUtil.sortingPubData(SortPubsBy.DISTANCE);
                }
            }
        });
        ((ConstraintLayout) popupView.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(false);
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(false);
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(false);
                }
            }
        });
        ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(false);
                }
            }
        });


    }
}