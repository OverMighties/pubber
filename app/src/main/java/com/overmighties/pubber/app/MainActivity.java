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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        savedFragment= new SavedFragment();
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Observer<Integer> name= sort->{
            if(AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue()==1)
            {
                NavigationBar.smoothHide(findViewById(R.id.nav_view));
                LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView=inflater.inflate(R.layout.sort_pop_up,null);
                PopupWindow popupWindow=new PopupWindow(popUpView,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,true);
                popupWindow.setOnDismissListener(() -> {
                    NavigationBar.smoothPopUp(findViewById(R.id.nav_view));
                    if (((Chip) popUpView.findViewById(R.id.relevance)).isChecked()) {
                        SortUtil.sortPubs(1);
                        ((Chip) (findViewById(R.id.sort))).setText("Trafność");
                    } else if (((Chip) popUpView.findViewById(R.id.alphabetical)).isChecked()) {
                        SortUtil.sortPubs(2);
                        ((Chip) (findViewById(R.id.sort))).setText("Alfabetycznie");

                    } else if (((Chip) popUpView.findViewById(R.id.rate)).isChecked()) {
                        SortUtil.sortPubs(3);
                        ((Chip) (findViewById(R.id.sort))).setText("Najwyższa ocena");
                    } else if (((Chip) popUpView.findViewById(R.id.distance)).isChecked()){
                        SortUtil.sortPubs(4);
                        ((Chip) (findViewById(R.id.sort))).setText("Najbliżej");
                    }
            });
                chipslistener(popUpView,popupWindow);
                (findViewById(R.id.search)).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM,0,0);
                    }
                });
            }
            else
            {
                if(AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue()==2)
                {
                    NavigationBar.smoothHide(findViewById(R.id.nav_view));
                    LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popUpView=inflater.inflate(R.layout.popup2,null);
                    PopupWindow poPupWindow=new PopupWindow(popUpView,
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT,true);
                    ((TextView)popUpView.findViewById(R.id.PopUpTextView)).setText("Wybierz zakres ocen");
                    poPupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                             ArrayList<PubData> pubDataArrayList=new ArrayList<>();
                                RangeSlider slider=(RangeSlider)popUpView.findViewById(R.id.PopUpRanger);
                                if(slider.getValues().get(0)==0 & slider.getValues().get(1)==5) {((Chip)findViewById(R.id.rating)).setChecked(false);}
                                else
                                {

                                    for(PubData pub:AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue())
                                    {
                                        if(pub.getRatingOwn()>slider.getValues().get(0) & pub.getRatingOwn()<slider.getValues().get(1))
                                        {
                                            pubDataArrayList.add(pub);
                                        }
                                        AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().setValue(pubDataArrayList);
                                    }


                                }
                        }
                    });

                    popUpView.findViewById(R.id.dismis).setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {poPupWindow.dismiss();}});
                    (findViewById(R.id.search)).post(new Runnable() {
                        @Override
                        public void run() {
                            poPupWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM,0,0);
                        }
                    });
                }
                else {
                    if (AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue() == 3) {
                        NavigationBar.smoothHide(findViewById(R.id.nav_view));
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popUpView = inflater.inflate(R.layout.popup2, null);
                        PopupWindow poPupWindow = new PopupWindow(popUpView,
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT, true);
                        ((TextView) popUpView.findViewById(R.id.PopUpTextView)).setText("Wybierz zakres odległości");
                        poPupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                ArrayList<PubData> pubDataArrayList=new ArrayList<>();
                                RangeSlider slider = (RangeSlider) popUpView.findViewById(R.id.PopUpRanger);
                                if (slider.getValues().get(0) == 0 & slider.getValues().get(1) == 5) {
                                    ((Chip) findViewById(R.id.rating)).setChecked(false);
                                } else {

                                    for (PubData pub : AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue()) {
                                        if (pub.getDistance() > slider.getValues().get(0) & pub.getDistance() < slider.getValues().get(1)) {
                                            pubDataArrayList.add(pub);
                                        }
                                        AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().setValue(pubDataArrayList);
                                    }


                                }
                            }
                        });

                        popUpView.findViewById(R.id.dismis).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poPupWindow.dismiss();
                            }
                        });
                        (findViewById(R.id.search)).post(new Runnable() {
                            @Override
                            public void run() {


                                poPupWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM, 0, 0);
                            }
                        });
                    }
                }
            }

        };



        AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().observe(this,name);
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

    private void chipslistener(View popUpView,PopupWindow popupWindow)
    {
        (popUpView.findViewById(R.id.alphabetical)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Chip)(popUpView.findViewById(R.id.relevance))).setChecked(false);
                ((Chip)(popUpView.findViewById(R.id.rate))).setChecked(false);
                ((Chip)(popUpView.findViewById(R.id.distance))).setChecked(false);
            }
        });

        (popUpView.findViewById(R.id.relevance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Chip)popUpView.findViewById(R.id.alphabetical)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.rate)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.distance)).setChecked(false);
            }
        });
        (popUpView.findViewById(R.id.distance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Chip)popUpView.findViewById(R.id.alphabetical)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.relevance)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.rate)).setChecked(false);
            }
        });
        (popUpView.findViewById(R.id.rate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Chip)popUpView.findViewById(R.id.relevance)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.distance)).setChecked(false);
                ((Chip)popUpView.findViewById(R.id.alphabetical)).setChecked(false);
            }
        });

        (popUpView.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


}