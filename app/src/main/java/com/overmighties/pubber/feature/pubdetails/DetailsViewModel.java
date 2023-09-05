package com.overmighties.pubber.feature.pubdetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailsViewModel extends ViewModel {
    private final MutableLiveData<Long> pubId =new MutableLiveData<>();
    private final MutableLiveData<PubDetailsUiState> pubDetails =new MutableLiveData<>();
}
