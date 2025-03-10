package com.overmighties.pubber.core.savedpubs;

import static com.overmighties.pubber.core.savedpubs.SavedPubsMapper.MapProtoToPub;
import static com.overmighties.pubber.core.savedpubs.SavedPubsMapper.MapPubToProto;

import android.util.Log;

import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.app.core.savedpubs.PubProtoList;
import com.overmighties.pubber.core.model.Pub;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;

public class SavedPubsHandler {
    public static final String TAG = "SavedPubsHandler";
    @Getter
    private final RxDataStore<PubProtoList> savedPubsDataStore;
    @Getter
    private List<Pub> savedPubsList;
    @Getter
    private final MutableLiveData<Boolean> isDataFetched = new MutableLiveData<>();


    public SavedPubsHandler(RxDataStore<PubProtoList> dataStore){
        savedPubsDataStore = dataStore;
    }

    public void retriveSavedPubs(){
        isDataFetched.setValue(false);
        savedPubsDataStore.data()
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pubs->{
                            List<Pub> pubList = pubs.getPubsList().stream().map(pub->MapProtoToPub(pub)).collect(Collectors.toList());
                            savedPubsList = pubList;
                            isDataFetched.setValue(true);
                        },
                        throwable -> Log.e(TAG, "Error fetching saved pubs", throwable)
                );
    }

    public void addPub(Pub pub){
        savedPubsDataStore.updateDataAsync(curentdata->{
            PubProtoList updatedList = PubProtoList.newBuilder()
                    .addAllPubs(curentdata.getPubsList())
                    .addPubs(MapPubToProto(pub))
                    .build();
            return Single.just(updatedList);
        }).subscribe((r) -> {
            Log.i(TAG, "DataStore updated");
        }, throwable -> {
            Log.e(TAG, "Error updating DataStore", throwable);
        });
    }

    public void deletePub(Long pubId){
        savedPubsDataStore.updateDataAsync(curentdata->{
            PubProtoList.Builder updatedList = PubProtoList.newBuilder();
            for(var pub:curentdata.getPubsList()){
                if(pub.getPubId() != pubId)
                    updatedList.addPubs(pub);
            }
            return Single.just(updatedList.build());
        }).subscribe((r) -> {
            Log.i(TAG, "DataStore updated");
        }, throwable -> {
            Log.e(TAG, "Error updating DataStore", throwable);
        });
    }

}
