package com.overmighties.pubber.core.database.fake;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.database.PubberLocalDataSource;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.network.model.PubDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FakeLocalRepository implements PubberLocalDataSource {

    public FakeLocalRepository(){}
    @Override
    public Single<List<Pub>> getPubs() {
        return  null;
    }

    @Override
    public void updatePubs(Single<List<Pub>> pubs) {

    }


}
