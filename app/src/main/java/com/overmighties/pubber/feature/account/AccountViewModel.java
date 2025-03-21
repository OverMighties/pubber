package com.overmighties.pubber.feature.account;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import static com.overmighties.pubber.app.navigation.PubberNavRoutes.ACCOUNT_DETAILS_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SPLASH_FRAGMENT;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.exception.ErrorSnackbarUI;
import com.overmighties.pubber.core.auth.AccountApi;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.core.auth.model.UserData;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.Objects;
import java.util.function.BiConsumer;

public class AccountViewModel extends PubberAppViewModel {
    private static final String TAG="AccountViewModel";
    private final AccountApi accountApi;
    private final MutableLiveData<AccountDetailsUIState> userData;
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
    public AccountViewModel(AccountApi accountApi, SavedStateHandle savedStateHandle){
        this.accountApi = accountApi;
        userData = new MutableLiveData<>(new AccountDetailsUIState());
    }
    public void onSignOutClick(BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorSnackbarUI.ErrorTypes, UIText, String> snackbarOnError) {
        disposables.add(completableAction(TAG,
                accountApi::signOut,
                ()-> openAndPopUp.accept(ACCOUNT_DETAILS_FRAGMENT,SPLASH_FRAGMENT),
                (err)->{
                    if(err instanceof AccFirebaseDSError.DifferentInternalError){
                        snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                    }
                    else if(err instanceof AccFirebaseDSError){
                        snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH,((AccFirebaseDSError) err).getUserMsg(),"");
                    } else{
                        snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.UNKNOWN_ERROR,null, err.getLocalizedMessage());
                    }
                }
        ));
    }
    public void updateEmail(String email,TriConsumer<ErrorSnackbarUI.ErrorTypes, UIText, String> snackbarOnError){
        singleAction(TAG,
                accountApi.updateUserEmail(email),
                el->userData.getValue().setEmail(email),
                (err)->{
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.USER_ACCOUNT,null, err.getLocalizedMessage());
                    Log.e(TAG,"User email can't be updated because:"+err.getLocalizedMessage());
                });
    }
    public void updateDisplayName(String displayName, TriConsumer<ErrorSnackbarUI.ErrorTypes, UIText, String> snackbarOnError, Runnable onComplete){
        singleAction(TAG,
                accountApi.updateUserProfile(new UserData(null,userData.getValue().getEmail()
                        ,displayName,userData.getValue().getPhotoUrl())),
                el-> {
                    userData.getValue().setUsername(displayName);
                    onComplete.run();
                },
                (err)->{
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.USER_ACCOUNT,null, err.getLocalizedMessage());
                    Log.e(TAG,"User display name can't be updated because: "+err.getLocalizedMessage());
                });
    }
    public void getCurrentUser() {
        UserData userData= accountApi.currentUser();
        if(userData!=null)
            updateUserData(mapToUIState(accountApi.currentUser()));
    }
    public static AccountDetailsUIState mapToUIState(UserData userData){
        Log.i(TAG,userData.getUserId()+userData.getEmail()+userData.getUsername());
        return new AccountDetailsUIState(
                false,
                userData.getUsername()==null ||  userData.getUsername().isBlank()?AccountDetailsUIState.UNKNOWN:userData.getUsername(),
                userData.getEmail()==null ||  userData.getEmail().isBlank()?AccountDetailsUIState.UNKNOWN:userData.getEmail(),
                AccountDetailsUIState.UNKNOWN_SEX,
                userData.getPhotoUrl()==null? Uri.EMPTY:userData.getPhotoUrl());
    }
    public void onDeleteAccountClick() {
        disposables.add(completableAction(TAG, accountApi::deleteAccount));
    }
}
