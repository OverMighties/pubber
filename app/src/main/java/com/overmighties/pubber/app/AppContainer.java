package com.overmighties.pubber.app;



import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.network.retrofit.PubsRetrofitDataSource;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AppContainer {
  // private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
   private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();
   private final FakeLocalRepository localRepository=new FakeLocalRepository();

   public final PubsRepository pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);


}
