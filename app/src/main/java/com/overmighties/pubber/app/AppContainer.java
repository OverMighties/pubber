package com.overmighties.pubber.app;

import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.network.fake.FakePubsNetworkDataSource;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AppContainer {


   @Getter
   private final MutableLiveData<List<Pub>> fakeLocalPubData=new MutableLiveData<>();

   private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
   private final FakeLocalRepository localRepository=new FakeLocalRepository();

   public final PubsRepository pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);


}
