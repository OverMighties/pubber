package com.overmighties.pubber.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.exception.ErrorSnackbarUI;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.overmighties.pubber.feature.account.AccountViewModel;
import com.overmighties.pubber.feature.alcohol.AlcoholViewModel;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.app.ui.SettingsBasicActivity;

import java.util.Objects;


public class MainActivity extends SettingsBasicActivity {

    public static final String TAG="MainActivity";
    private ActivityMainBinding binding;
    private PubListViewModel pubListViewModel;
    private AccountViewModel accountViewModel;
    private AlcoholViewModel alcoholViewModel;
    private NavController navController;
    private ConnectivityManager connectivityManager;
    private Network currentNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        pubListViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        accountViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(AccountViewModel.initializer))
                .get(AccountViewModel.class);
        alcoholViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(AlcoholViewModel.initializer))
                .get(AlcoholViewModel.class);
        pubListViewModel.get_originalDrinksData().observe(this, dataList->{
            alcoholViewModel.setAlcoholData(dataList);
        });
        pubListViewModel.fetchPubsFromRepo(0);
        pubListViewModel.fetchDrinksFromRepo(0);
        pubListViewModel.retrivePubData();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController= ( (NavHostFragment) Objects.requireNonNull(getSupportFragmentManager()
                .findFragmentById(R.id.main_navHostFragment_container))).getNavController();

        setConnectivityManager();

        BottomNavigationView bottomNavView = findViewById(R.id.main_bottomNavView);
        NavigationUI.setupWithNavController(bottomNavView, navController);
        findViewById(R.id.main_topAppBarLayout_back).setVisibility(View.GONE);
        ((MaterialToolbar)findViewById(R.id.main_topAppBarView_back)).setNavigationOnClickListener(v->{navController.popBackStack();});
        //observer for synchronizing saved pubs with pubs repository
        pubListViewModel.getSavedPubsHandler().getIsDataFetched().observe(this, isFetched->{
            if(isFetched && pubListViewModel.getPubDataLoaded().getValue()) {
                pubListViewModel.synchronizePubsRepositories();
            }
        });
        pubListViewModel.getPubDataLoaded().observe(this, isLoaded->{
            if(isLoaded && pubListViewModel.getSavedPubsHandler().getIsDataFetched().getValue()){
                pubListViewModel.synchronizePubsRepositories();
            }
        });
        //set up observer for saving pubs accross whole app
        pubListViewModel.getFavouritePubState().observe(this, pair->{
            if(pubListViewModel.getFavouritePubState().getValue().first != -1) {
                if (pubListViewModel.getFavouritePubState().getValue().second) {
                    pubListViewModel.savePub(pubListViewModel.get_originalPubData()
                            .getValue()
                            .stream()
                            .filter(pub -> pub.getPubId() == pubListViewModel.getFavouritePubState().getValue().first)
                            .findFirst());
                } else {
                    pubListViewModel.deletePub(pubListViewModel.getFavouritePubState().getValue().first);
                }
            }
        });
        //get url to pub intent
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            String path = data.getPath();
            String[] splitted = path.split("/");
            if(splitted.length == 4){
                String city = splitted[2];
                pubListViewModel.setCityConstraint(city);
                String pubId = splitted[3];
                try {
                    Long id = Long.parseLong(pubId);
                    pubListViewModel.getSearcherUiState().getValue().setLinkPubId(id);
                }catch (NumberFormatException e){
                    Log.e(TAG, "Invalid Link");
                }
            }
        }

    }



    private void setConnectivityManager(){
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetwork()==null){
            Log.e(TAG,"The application can't find internet connection on app start");
            ErrorSnackbarUI.showSnackbar(
                    findViewById(android.R.id.content).getRootView(),
                    ErrorSnackbarUI.ErrorTypes.NO_INTERNET_CONNECTION,
                    null,
                    null
            );
        }
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.i(TAG, "The default network is now: " + network);
                Toast.makeText(MainActivity.this, "You have internet connection", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.e(TAG, "The application no longer has a default network. The last default network was " + network);
                ErrorSnackbarUI.showSnackbar(
                        findViewById(android.R.id.content).getRootView(),
                        ErrorSnackbarUI.ErrorTypes.NO_INTERNET_CONNECTION,
                        null,
                        null
                );
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                Log.i(TAG, "The default network changed capabilities: " + networkCapabilities);
            }

            @Override
            public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
                Log.i(TAG, "The default network changed link properties: " + linkProperties);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.main_navHostFragment_container).navigateUp()
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = ( (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navHostFragment_container) ).getNavController();
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }





}