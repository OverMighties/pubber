package com.overmighties.pubber.core.network.retrofit;



import com.overmighties.pubber.core.network.PubberNetworkDataSource;
import com.overmighties.pubber.core.network.model.PubDto;


import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PubsRetrofitDataSource implements PubberNetworkDataSource {

    private static final PubsRetrofitDataSource INSTANCE= new PubsRetrofitDataSource();
    private PubsNetworkApi networkApi;
    public static final String TAG="PubsRetrofitDataSource";
    private static final String URL = "https://pubber-rest-api-49572a95c20a.herokuapp.com";
    
    private PubsRetrofitDataSource()
    {
        networkApi =  new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(PubsNetworkApi.class);
    }
    public static PubsRetrofitDataSource getInstance()
    {
        return INSTANCE;
    }
    public Single<List<PubDto>> getPubs()
    {
        return networkApi.getPubs();
    }

}
