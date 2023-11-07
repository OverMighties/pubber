package com.overmighties.pubber.app.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaceChoiceViewModel extends ViewModel {
    private final MutableLiveData<String> cityLiveData=new MutableLiveData<>();

    public void setCity(String city)
    {
        cityLiveData.setValue(city);
    }

    public LiveData<String>  getCity()
    {
        return cityLiveData;
    }
}
