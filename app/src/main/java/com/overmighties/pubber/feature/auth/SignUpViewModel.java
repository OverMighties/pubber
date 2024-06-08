package com.overmighties.pubber.feature.auth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.navigation.PubberNavRoutes;
import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.util.SnackbarUI;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.util.UIText;

import java.util.Objects;
import java.util.function.BiConsumer;


public class SignUpViewModel extends PubberAppViewModel {
    private final AccountDataSource accountDataSource;
    private static final String TAG="SignUpViewModel";
    public static final ViewModelInitializer<SignUpViewModel> initializer = new ViewModelInitializer<>(
            SignUpViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);
                return new SignUpViewModel(app.appContainer.getAccountDataSource(),  savedStateHandle);
            }
    );
    public SignUpViewModel(AccountDataSource accountDataSource, SavedStateHandle savedStateHandle){
        this.accountDataSource=accountDataSource;
    }
    private final MutableLiveData<String> email=new MutableLiveData<>();
    public void updateEmail(String newEmail){
        email.setValue(newEmail);
    }
    private final MutableLiveData<String> password=new MutableLiveData<>();
    public void updatePassword(String newPassword){
        password.setValue(newPassword);
    }
    private final MutableLiveData<String> confirmPassword=new MutableLiveData<>();
    public void updateConfirmPassword(String newConfirmPassword){
        confirmPassword.setValue(newConfirmPassword);
    }
    public void onSignUpClick(BiConsumer<String,String> openAndPopUp, TriConsumer<SnackbarUI.SnackbarTypes, UIText,String> snackbarOnError){
        if(!Objects.equals(password.getValue(), confirmPassword.getValue())){
            snackbarOnError.accept(SnackbarUI.SnackbarTypes.BASIC_AUTH_ERROR,new UIText.ResourceString(R.string.NOT_MATCHING_PASSWORDS),"");
            Log.e(TAG,"User entered not matching passwords in sign up");
        }else{
            singleAction(TAG,
                    accountDataSource.signUp(email.getValue(),password.getValue()),
                    ()->openAndPopUp.accept(SIGN_UP_FRAGMENT,SEARCHER_FRAGMENT),
                    (err)->{
                        if(err instanceof AccFirebaseDSError.DifferentInternalError)
                            snackbarOnError.accept(SnackbarUI.SnackbarTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                        else if(err instanceof AccFirebaseDSError)
                            snackbarOnError.accept(SnackbarUI.SnackbarTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),"");
                        else
                            snackbarOnError.accept(SnackbarUI.SnackbarTypes.BASIC_AUTH_ERROR,null, err.getLocalizedMessage());
                    });
        }
    }
}
