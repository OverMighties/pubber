package com.overmighties.pubber.feature.auth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.NEW_USER_DETAILS_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.PubberAppViewModel;
import com.overmighties.pubber.app.exception.ErrorSigningUITypes;
import com.overmighties.pubber.core.auth.AccountApi;
import com.overmighties.pubber.core.auth.firebase.AccFirebaseDSError;
import com.overmighties.pubber.util.TriConsumer;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.Objects;
import java.util.function.BiConsumer;


public class SignUpViewModel extends PubberAppViewModel {
    private final AccountApi accountApi;

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
    public SignUpViewModel(AccountApi accountApi, SavedStateHandle savedStateHandle){
        this.accountApi = accountApi;
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
    public void onSignUpClick(BiConsumer<String,String> openAndPopUp, TriConsumer<ErrorSigningUITypes, UIText,String> responseOnError){
        if(!Objects.equals(password.getValue(), confirmPassword.getValue())){
            responseOnError.accept(ErrorSigningUITypes.AUTH_NOT_SAME_PASSWORDS,new UIText.ResourceString(R.string.NOT_MATCHING_PASSWORDS),"");
            Log.e(TAG,"User entered not matching passwords in sign up");
        }else{
            singleAction(TAG,
                    accountApi.signUp(email.getValue(),password.getValue()),
                    ()->openAndPopUp.accept(SIGN_UP_FRAGMENT,NEW_USER_DETAILS_FRAGMENT),
                    (err)->{
                        if(err instanceof AccFirebaseDSError.DifferentInternalError)
                            responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_BASIC_ERROR,((AccFirebaseDSError) err).getUserMsg(),((AccFirebaseDSError) err).getLogMessage());
                        else if(err instanceof AccFirebaseDSError) {
                            if (((AccFirebaseDSError) err).getType() == AccFirebaseDSError.Type.EMAIL)
                                responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_EMAIL_ERROR, ((AccFirebaseDSError) err).getUserMsg(), null);
                            else if (((AccFirebaseDSError) err).getType() == AccFirebaseDSError.Type.PASSWORD)
                                responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_PASSWORD_ERROR, ((AccFirebaseDSError) err).getUserMsg(), null);
                            else
                                responseOnError.accept(ErrorSigningUITypes.FIREBASE_AUTH_BASIC_ERROR, ((AccFirebaseDSError) err).getUserMsg(), null);
                        }else
                            responseOnError.accept(ErrorSigningUITypes.UNKNOWN_ERROR,null, err.getLocalizedMessage());
                    });
        }
    }
}
