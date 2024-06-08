package com.overmighties.pubber.feature.auth;


import com.overmighties.pubber.R;
import com.overmighties.pubber.app.exception.AppError;
import com.overmighties.pubber.util.UIText;

public class GoogleSignInError extends AppError {
    private static final UIText uiText= new  UIText.ResourceString (R.string.GOOGLE_SIGN_IN_FAILED);
    public GoogleSignInError(String logMessage) {
        super(logMessage, uiText);
    }
}
