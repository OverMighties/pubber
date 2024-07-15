package com.overmighties.pubber.feature.bookmarks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

@Getter
public class SavedViewModel extends ViewModel {
    private final MutableLiveData<String> savedList =new MutableLiveData<>();
}
