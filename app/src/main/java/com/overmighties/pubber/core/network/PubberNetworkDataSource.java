package com.overmighties.pubber.core.network;

import com.overmighties.pubber.core.network.model.PubDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PubberNetworkDataSource {
     Single<List<PubDto>> getPubs();
}
