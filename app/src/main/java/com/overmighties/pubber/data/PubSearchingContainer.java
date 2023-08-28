package com.overmighties.pubber.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import lombok.Getter;

public class PubSearchingContainer {

/*
* Przechowuje dane ktore moga byc modyfikowane w kazdej chwili
* Live data pokazuje rowniez czy te zmiany zaszly
 */
    @Getter
    private final MutableLiveData<List<PubData>> listOfFiltratedPubs=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Integer> position=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> savedList =new MutableLiveData<>();
    @Getter
    private final MutableLiveData<FiltrationData> filtrationOfPubs=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> popupInformation =new MutableLiveData<>();
    @Getter
    private final MutableLiveData<List<PubData>> listOfSortedPubs=new MutableLiveData<>();
    public PubSearchingContainer()
    {

        listOfFiltratedPubs.setValue(null);
        filtrationOfPubs.setValue(null);
        position.setValue(null);
        savedList.setValue("");
        popupInformation.setValue("nie");
        listOfSortedPubs.setValue(null);

    }

}
