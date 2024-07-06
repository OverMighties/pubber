package com.overmighties.pubber.feature.auth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;

import android.util.Log;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.exception.ErrorHandler;
import com.overmighties.pubber.core.auth.AccountDataSource;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.util.SnackbarUI;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.util.UIText;

import java.util.Objects;
import java.util.function.BiConsumer;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class SignUpViewModel extends PubberAppViewModel {
    private final AccountDataSource accountDataSource;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static final int NONE_RES=-1;

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
    public void onSignUpClick(BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorHandler.ErrorTypes, UIText,String> snackbarOnError){
        if(!Objects.equals(password.getValue(), confirmPassword.getValue())){
            snackbarOnError.accept(ErrorHandler.ErrorTypes.BASIC_AUTH_ERROR,new UIText.ResourceString(R.string.NOT_MATCHING_PASSWORDS),"");
            Log.e(TAG,"User entered not matching passwords in sign up");
        }else{
            singleAction(TAG,
                    accountDataSource.signUp(email.getValue(),password.getValue()),
                    ()->openAndPopUp.accept(SIGN_UP_FRAGMENT,SEARCHER_FRAGMENT),
                    (err)->{
                        if(err instanceof AccFirebaseDSError.DifferentInternalError)
                            snackbarOnError.accept(ErrorHandler.ErrorTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                        else if(err instanceof AccFirebaseDSError)
                            snackbarOnError.accept(ErrorHandler.ErrorTypes.FIREBASE_AUTH_ERROR,((AccFirebaseDSError) err).getUserMsg(),"");
                        else
                            snackbarOnError.accept(ErrorHandler.ErrorTypes.BASIC_AUTH_ERROR,null, err.getLocalizedMessage());
                    });
        }
    }

    @StringRes
    public static int getMessageResId(SnackbarUI.SnackbarTypes type) {
        switch (type) {
            case BASIC_AUTH_ERROR:{
                return R.string.auth_error_message;
            }
            case NO_INTERNET_CONNECTION :{
                return R.string.no_internet_connection_message;
            }
            case MARKED_AS_FAVORITE :{
                return R.string.marked_as_favorite_message;
            }
            case COMMENT_ADDED :{
                return R.string.comment_added_message;
            }
            default: Log.e(TAG,"Unknown SnackbarType: " + type);
        }
        return NONE_RES;
    }
}
