package com.overmighties.pubber.feature.account;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.ACCOUNT_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SPLASH_FRAGMENT;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.core.auth.model.UserData;
import com.overmighties.pubber.util.SnackbarUI;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.util.UIText;

import java.util.Objects;
import java.util.function.BiConsumer;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AccountViewModel extends PubberAppViewModel {
    private static final String TAG="AccountViewModel";
    private final AccountDataSource accountDataSource;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<AccountDetailsUIState> userData=new MutableLiveData<>(new AccountDetailsUIState());
    public LiveData<AccountDetailsUIState> getUserData(){
        return userData;
    }
    private void updateUserData(AccountDetailsUIState newUserData){
        userData.setValue(newUserData);
        Log.i(TAG, Objects.requireNonNull(userData.getValue()).toString());
    }
    public static final ViewModelInitializer<AccountViewModel> initializer = new ViewModelInitializer<>(
            AccountViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);
                return new AccountViewModel(app.appContainer.getAccountDataSource(),  savedStateHandle);
            }
    );
    public AccountViewModel(AccountDataSource accountDataSource, SavedStateHandle savedStateHandle){
        this.accountDataSource=accountDataSource;
    }
    public void onSignOutClick(BiConsumer<String,String> openAndPopUp, TriConsumer<SnackbarUI.SnackbarTypes, UIText,String> snackbarOnError) {
        disposables.add(completableAction(TAG,
                accountDataSource::signOut,
                ()->{
                    openAndPopUp.accept(ACCOUNT_FRAGMENT,SPLASH_FRAGMENT);
                },
                (err)->{
                    if(err instanceof AccFirebaseDSError.DifferentInternalError){
                        snackbarOnError.accept(SnackbarUI.SnackbarTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                    }
                    else if(err instanceof AccFirebaseDSError){
                        snackbarOnError.accept(SnackbarUI.SnackbarTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),"");
                    } else{
                        snackbarOnError.accept(SnackbarUI.SnackbarTypes.BASIC_AUTH_ERROR,null, err.getLocalizedMessage());
                    }
                }
        ));
    }
    public void getCurrentUser() {
        singleAction(TAG,
                accountDataSource.currentUser(),
                el->updateUserData(mapToUIState(el)),
                (err)-> Log.e(TAG,"User data can't be retrieved because:"+err.getLocalizedMessage()));
    }
    public AccountDetailsUIState mapToUIState(UserData userData){
        Log.i(TAG,userData.getUserId()+userData.getEmail()+userData.getUsername());
        return new AccountDetailsUIState(
                userData.getUsername()==null ||  userData.getUsername().isBlank()?AccountDetailsUIState.UNKNOWN:userData.getUsername(),
                userData.getEmail()==null ||  userData.getEmail().isBlank()?AccountDetailsUIState.UNKNOWN:userData.getEmail(),
                AccountDetailsUIState.UNKNOWN_SEX,
                userData.getPhotoUrl()==null? Uri.EMPTY:userData.getPhotoUrl());
    }
    public void onDeleteAccountClick() {
        disposables.add(completableAction(TAG, accountDataSource::deleteAccount));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
