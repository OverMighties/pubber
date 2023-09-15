package com.overmighties.pubber.core.database;

import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.sync.Result;


import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PubberLocalDataSource {
    Single<List<Pub>> getPubs();
    void updatePubs(List<Pub>  pubs) throws RuntimeException;
}
