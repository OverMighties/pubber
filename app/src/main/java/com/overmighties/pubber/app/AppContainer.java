package com.overmighties.pubber.app;



import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccountFirebaseDataSource;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.core.database.PubsRoomDbSource;
import com.overmighties.pubber.core.network.fake.FakePubsNetworkDataSource;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;

import lombok.Getter;


public final class AppContainer {
    private final AppDb localDb;
    private final FakePubsNetworkDataSource pubsNetworkDataSource;
    private final FakeLocalRepository localRepository;
    //private final PubsRoomDbSource localRepository;
    @Getter
    private final PubsRepository pubsRepository;
    @Getter
    private final AccountDataSource accountDataSource;
    public AppContainer(AppDb localDb)
    {
        this.localDb=localDb;
        this.accountDataSource=new AccountFirebaseDataSource();
        //localRepository=new PubsRoomDbSource(localDb);
        localRepository=new FakeLocalRepository();
        pubsNetworkDataSource=new FakePubsNetworkDataSource();
        //pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();
        pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);
    }



}
