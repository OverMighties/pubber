package com.overmighties.pubber.app;




import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.core.database.PubsRoomDbSource;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;


import lombok.Getter;


public final class AppContainer {
    private final AppDb localDb;
    // private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
    private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();

    //private final FakeLocalRepository localRepository;
    private final PubsRoomDbSource localRepository;
    @Getter
    private PubsRepository pubsRepository;
    public AppContainer(AppDb localDb)
    {
        this.localDb=localDb;
        localRepository=new PubsRoomDbSource(localDb);
        //localRepository=new FakeLocalRepository();
        pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);
    }



}
