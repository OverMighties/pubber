package com.overmighties.pubber.core.network.retrofit;



import com.overmighties.pubber.core.network.model.PubDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface PubsNetworkApi {
    @GET("/pubs/*")
    Single<List<PubDto>> getPubs();
}
