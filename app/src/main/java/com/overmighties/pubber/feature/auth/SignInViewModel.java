package com.overmighties.pubber.feature.auth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_IN_FRAGMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.exception.ErrorSigningUIHandler;
import com.overmighties.pubber.app.exception.ErrorSigningUITypes;
import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.util.NotificationSnackbarUI;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.util.UIText;

import java.util.function.BiConsumer;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignInViewModel extends PubberAppViewModel {
    private static final String TAG="SignInViewModel";
    private final AccountDataSource accountDataSource;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static final ViewModelInitializer<SignInViewModel> initializer = new ViewModelInitializer<>(
            SignInViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new SignInViewModel(app.appContainer.getAccountDataSource(),  savedStateHandle);
            }
    );
    public SignInViewModel(AccountDataSource accountDataSource, SavedStateHandle savedStateHandle){
        this.accountDataSource=accountDataSource;
    }
    private final MutableLiveData<String> email=new MutableLiveData<>();
    public LiveData<String> getEmail(){
        return email;
    }
    public void updateEmail(String newEmail){
        email.setValue(newEmail);
    }

    private final MutableLiveData<String> password=new MutableLiveData<>();
    public LiveData<String> getPassword(){
        return password;
    }
    public void updatePassword(String newPassword){
        password.setValue(newPassword);
    }
    public void onSignInClick(BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorSigningUITypes, UIText,String> responseOnError){
        singleAction(TAG,
                accountDataSource.signIn(email.getValue(),password.getValue()),
                ()-> openAndPopUp.accept(SIGN_IN_FRAGMENT,SEARCHER_FRAGMENT),
                (err)->{
                    if(err instanceof AccFirebaseDSError.DifferentInternalError)
                        responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_BASIC_ERROR,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                    else if(err instanceof AccFirebaseDSError){
                        if (((AccFirebaseDSError) err).getType() == AccFirebaseDSError.Type.EMAIL)
                            responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_EMAIL_ERROR, ((AccFirebaseDSError) err).getUserMsg(), "");
                        else if (((AccFirebaseDSError) err).getType() == AccFirebaseDSError.Type.PASSWORD)
                            responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_PASSWORD_ERROR, ((AccFirebaseDSError) err).getUserMsg(), "");
                        else
                            responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_BASIC_ERROR, ((AccFirebaseDSError) err).getUserMsg(), "");
                    }
                    else
                        responseOnError.accept(ErrorSigningUITypes.UNKNOWN_ERROR,null, err.getLocalizedMessage());
                }
        );
    }
//    public void onSignUpClick(BiConsumer<String,String> openAndPopUp){
//        openAndPopUp.accept(PubberNavRoutes.SIGN_IN_FRAGMENT,PubberNavRoutes.SIGN_UP_FRAGMENT);
//    }
}
