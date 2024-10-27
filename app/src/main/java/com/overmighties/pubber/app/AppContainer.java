package com.overmighties.pubber.app;



import android.content.Context;

import com.overmighties.pubber.core.auth.AccountApi;
import com.overmighties.pubber.core.auth.firebase.AccountFirebaseDataSource;
import com.overmighties.pubber.core.data.DrinksRepository;
import com.overmighties.pubber.core.data.LocationRepositoryImpl;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.core.database.DrinksRoomDbSource;
import com.overmighties.pubber.core.database.PubberDrinksLocalApi;
import com.overmighties.pubber.core.database.PubberLocalApi;
import com.overmighties.pubber.core.database.PubsRoomDbSource;
import com.overmighties.pubber.core.drinksdataset.OfflineDrinksDataSource;
import com.overmighties.pubber.core.drinksdataset.PubberDrinkOfflineApi;
import com.overmighties.pubber.core.location.LocationRepository;
import com.overmighties.pubber.core.location.UserLocationApi;
import com.overmighties.pubber.core.location.UserLocationDataSource;
import com.overmighties.pubber.core.network.PubberNetworkApi;
import com.overmighties.pubber.core.network.fake.FakePubsNetworkDataSource;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;

import lombok.Getter;
import lombok.Setter;


public final class AppContainer {
    private final AppDb localDb;
    private final PubberNetworkApi pubsNetworkDataSource;
    private final PubberLocalApi localRepository;
    private final PubberDrinksLocalApi drinksDataSource;
    private final PubberDrinkOfflineApi drinkOfflineDataSource;
    @Getter
    private final PubsRepository pubsRepository;
    @Getter
    private final DrinksRepository drinksRepository;
    @Getter
    private final AccountApi accountDataSource;
    @Getter
    private LocationRepository locationRepository;
    public void setLocationRepository(Context context){
        this.locationRepository=new LocationRepositoryImpl(new UserLocationDataSource(context));
    }
    public AppContainer(AppDb localDb)
    {
        this.localDb=localDb;
        this.accountDataSource =new AccountFirebaseDataSource();
        //this.localRepository=new PubsRoomDbSource(localDb);1
        this.localRepository=new FakeLocalRepository();
        this.pubsNetworkDataSource=new FakePubsNetworkDataSource();
       //this.pubsNetworkDataSource= PubsRetrofitDataSource.getInstance();
        this.pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);
        this.drinksDataSource = new DrinksRoomDbSource(localDb);
        this.drinkOfflineDataSource = new OfflineDrinksDataSource();
        this.drinksRepository = new DrinksRepository(drinksDataSource, drinkOfflineDataSource);
    }



}
