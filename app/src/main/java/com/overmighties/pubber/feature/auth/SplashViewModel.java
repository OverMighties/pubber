package com.overmighties.pubber.feature.auth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.Intent;
import android.util.Log;

import androidx.credentials.GetCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;


import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.exception.ErrorSnackbarUI;
import com.overmighties.pubber.app.navigation.PubberNavRoutes;
import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.core.auth.model.UserData;
import com.overmighties.pubber.util.NotificationSnackbarUI;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.util.UIText;

import java.util.function.BiConsumer;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SplashViewModel extends PubberAppViewModel {
    private static final String TAG="SplashViewModel";
    private final AccountDataSource accountDataSource;
    public static final ViewModelInitializer<SplashViewModel> initializer = new ViewModelInitializer<>(
        SplashViewModel.class,
        creationExtras -> {
            PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
            assert app != null;
            SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);
            return new SplashViewModel(app.appContainer.getAccountDataSource(),  savedStateHandle);
        }
    );
    private final CompositeDisposable disposables = new CompositeDisposable();
    public SplashViewModel(AccountDataSource accountDataSource, SavedStateHandle savedStateHandle){
        this.accountDataSource=accountDataSource;
    }
    public void onAppStart(BiConsumer<String,String> openAndPopUp){
        if (accountDataSource.hasUser())
            openAndPopUp.accept(PubberNavRoutes.SPLASH_FRAGMENT, PubberNavRoutes.SEARCHER_FRAGMENT);
    }
    //To api<14
    private Single<UserData> firebaseAuthWithGoogle(Intent data, SignInClient signInClient)  {
        // Google Sign In was successful, authenticate with Firebase
        SignInCredential credential = null;
        try {
            credential = signInClient.getSignInCredentialFromIntent(data);
        } catch (ApiException e) {
            return Single.error(e);
        }
        String idToken = credential.getGoogleIdToken();
        return  accountDataSource.signInWithCredentials(idToken);
    }
    //From SDK api>=14
    private Single<UserData> firebaseAuthWithGoogle(GetCredentialResponse response)  {
        if (response.getCredential() instanceof PasswordCredential) {
            PasswordCredential credential= (PasswordCredential) response.getCredential();
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
                return accountDataSource.signInWithCredentials(googleIdTokenCredential.getIdToken());
            }
        }
        Log.e(TAG, "No valid credential response");
        return Single.error(new GoogleSignInError(null));
    }
    //To api<14
    public void handleSignInResult(Intent data, SignInClient signInClient, BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorSnackbarUI.ErrorTypes, UIText, String> snackbarOnError) {
        singleAction(TAG, firebaseAuthWithGoogle(data, signInClient),
            () -> openAndPopUp.accept(PubberNavRoutes.SPLASH_FRAGMENT, PubberNavRoutes.SEARCHER_FRAGMENT),
            (err) -> {
                if(err instanceof AccFirebaseDSError.DifferentInternalError)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                else if (err instanceof AccFirebaseDSError)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH, ((AccFirebaseDSError) err).getUserMsg(),"");
                else if (err instanceof ApiException)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.GOOGLE_SIGN_IN, new UIText.ResourceString(R.string.GOOGLE_API_EXCEPTION),err.getLocalizedMessage());
                else
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.UNKNOWN_ERROR,null,err.getLocalizedMessage());
            });
    }
    //From SDK api>=14
    public void handleSignInResult(GetCredentialResponse credentialResponse, BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorSnackbarUI.ErrorTypes, UIText,String> snackbarOnError) {
        singleAction(TAG, firebaseAuthWithGoogle(credentialResponse),
            () -> openAndPopUp.accept(PubberNavRoutes.SPLASH_FRAGMENT, PubberNavRoutes.SEARCHER_FRAGMENT),
            (err) -> {
                if(err instanceof AccFirebaseDSError.DifferentInternalError)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                else if (err instanceof AccFirebaseDSError)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH, ((AccFirebaseDSError) err).getUserMsg(),"");
                else if (err instanceof ApiException)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH, new UIText.ResourceString(R.string.CREDENTIAL_NOT_PRESENT),"");
                else if (err instanceof GoogleSignInError)
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.GOOGLE_SIGN_IN,((GoogleSignInError) err).getUserMsg(),"");
                else
                    snackbarOnError.accept(ErrorSnackbarUI.ErrorTypes.UNKNOWN_ERROR,null,err.getLocalizedMessage());
            });
    }

}
