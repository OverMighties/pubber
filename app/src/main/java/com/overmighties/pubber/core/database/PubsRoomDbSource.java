package com.overmighties.pubber.core.database;

import android.util.Log;

import com.overmighties.pubber.core.data.PubDataMapper;
import com.overmighties.pubber.core.data.PubEntityMapper;
import com.overmighties.pubber.core.model.Pub;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PubsRoomDbSource implements PubberLocalApi {
    private final AppDb appDb;
    public static final String TAG="PubsRoomDbSource";
    @Override
    public Single<List<Pub>> getPubs() {
        return appDb.pubsDao().getPubsWithEntities()
                .map(list->{
                    Log.i(TAG, "getPubs: fetched pubs from db in number "+list.size());
                    return list.stream()
                        .map(PubEntityMapper::mapFromEntity)
                        .collect(Collectors.toList());
                });
    }

    @Override
    public void updatePubs(List<Pub> pubs) throws RuntimeException {
        appDb.pubsDao().insertAll(pubs.stream().map(PubDataMapper::mapToEntity).collect(Collectors.toList()));
    }
}
