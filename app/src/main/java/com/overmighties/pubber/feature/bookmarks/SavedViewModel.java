package com.overmighties.pubber.feature.bookmarks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

public class SavedViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<String> savedList =new MutableLiveData<>();
}
