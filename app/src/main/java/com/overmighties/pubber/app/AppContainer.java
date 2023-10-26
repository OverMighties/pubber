package com.overmighties.pubber.app;



import com.overmighties.pubber.core.data.PubsRepository;
<<<<<<< Updated upstream
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
=======
import com.overmighties.pubber.core.database.AppDb;
import com.overmighties.pubber.core.database.PubsRoomDbSource;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.network.fake.FakePubsNetworkDataSource;
>>>>>>> Stashed changes
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AppContainer {
<<<<<<< Updated upstream
  // private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
   private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();
   private final FakeLocalRepository localRepository=new FakeLocalRepository();
=======
    private AppDb localDb;
     private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
    //private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();

    private final FakeLocalRepository localRepository=new FakeLocalRepository();
    //private PubsRoomDbSource localRepository;
    @Getter
    private PubsRepository pubsRepository;
    public AppContainer(AppDb localDb)
    {
        this.localDb=localDb;
        //localRepository=new PubsRoomDbSource(localDb);
        pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);
    }
>>>>>>> Stashed changes

   public final PubsRepository pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);


}
