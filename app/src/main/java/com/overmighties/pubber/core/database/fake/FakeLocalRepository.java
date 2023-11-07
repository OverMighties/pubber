package com.overmighties.pubber.core.database.fake;

import android.util.Log;

import com.overmighties.pubber.core.database.PubberLocalDataSource;
import com.overmighties.pubber.core.model.Pub;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class FakeLocalRepository implements PubberLocalDataSource {
    public static final String TAG="FakeLocalRepository";
    private List<Pub> fakeLocalPubData;
    public FakeLocalRepository(){}
    @Override
    public Single<List<Pub>> getPubs() {
        return  Single.create(emit->emit.onSuccess(fakeLocalPubData));
    }

    @Override
    public void updatePubs(List<Pub> pubs) throws RuntimeException{

        fakeLocalPubData=pubs;
        /*
           Disposable d=pubs
                   .subscribe(
                           list ->fakeLocalPubData=list,
                           err ->{
                               Log.e(TAG, "updatePubs: " + err.getLocalizedMessage());
                               throw new RuntimeException("updatePubs: " + err.getLocalizedMessage());
                           });
           if(d.isDisposed()) {
               d.dispose();
           }

         */
    }


}
