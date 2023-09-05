package com.overmighties.pubber.app;

import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.database.fake.FakeLocalRepository;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.network.fake.FakePubsNetworkDataSource;
import com.overmighties.pubber.core.network.retrofit.PubsNetworkApi;
import com.overmighties.pubber.data.PubListContainer;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@NoArgsConstructor
public final class AppContainer {

   @Getter
   private PubListContainer pubSearchingContainer;
   @Getter
   private final MutableLiveData<List<Pub>> fakeLocalPubData=new MutableLiveData<>();

   private final FakePubsNetworkDataSource pubsNetworkDataSource=new FakePubsNetworkDataSource();
   private final FakeLocalRepository localRepository=new FakeLocalRepository();

   public final PubsRepository pubsRepository=new PubsRepository(pubsNetworkDataSource,localRepository);


}
