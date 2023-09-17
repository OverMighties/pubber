package com.overmighties.pubber.app;

import com.overmighties.pubber.data.PubSearchingContainer;

import lombok.Getter;


public final class AppContainer {
<<<<<<< Updated upstream
   private static  AppContainer INSTANCE=null;
   @Getter
   private PubSearchingContainer pubSearchingContainer;
=======
   //private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
   private final PubsRetrofitDataSource pubsNetworkDataSource=PubsRetrofitDataSource.getInstance();
   private final FakeLocalRepository localRepository=new FakeLocalRepository();

   public final PubsRepository pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);

>>>>>>> Stashed changes

   private AppContainer()
   {
      pubSearchingContainer=new PubSearchingContainer();
   }
   public static synchronized AppContainer getInstance()
   {
      if(INSTANCE==null)
         INSTANCE=new AppContainer();
      return INSTANCE;
   }
}
