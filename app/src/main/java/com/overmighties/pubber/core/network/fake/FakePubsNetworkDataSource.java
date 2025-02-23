package com.overmighties.pubber.core.network.fake;

import com.overmighties.pubber.core.network.PubberNetworkApi;
import com.overmighties.pubber.core.network.model.PubDto;
import com.overmighties.pubber.core.data_test.TestRepoPubsDataSet;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FakePubsNetworkDataSource implements PubberNetworkApi {

    @Override
    public Single<List<PubDto>> getPubs()
    {
       return Single.create(emit->emit.onSuccess(TestRepoPubsDataSet.getInstance().getDataSet()));
    }
}
