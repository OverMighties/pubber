package com.overmighties.pubber.app;




import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.core.database.PubsRoomDbSource;
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;


import lombok.Getter;


public final class AppContainer {
    private AppDb localDb;
    // private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
    private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();

    //private final FakeLocalRepository localRepository=new FakeLocalRepository();
    private PubsRoomDbSource localRepository;
    @Getter
    private PubsRepository pubsRepository;
    public AppContainer(AppDb localDb)
    {
        this.localDb=localDb;
        localRepository=new PubsRoomDbSource(localDb);
        pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);
    }



}
