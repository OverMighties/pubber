package com.overmighties.pubber.data;

import androidx.lifecycle.MutableLiveData;

import com.overmighties.pubber.data.model.PubUiState;

import java.util.List;

import lombok.Getter;

public class PubListContainer {

/*
* Przechowuje dane ktore moga byc modyfikowane w kazdej chwili
* Live data pokazuje rowniez czy te zmiany zaszly
 */
    @Getter
    private final MutableLiveData<String> pubList =new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> savedList =new MutableLiveData<>();
    @Getter
    private final MutableLiveData<FilterData> filtrationOfPubs=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> popupInformation =new MutableLiveData<>();

    public PubListContainer()
    {
        filtrationOfPubs.setValue(null);
        savedList.setValue("");
        popupInformation.setValue("nie");

    }

}
