package com.overmighties.pubber.core.auth.firebase;

import android.util.Log;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.exception.AppError;
import com.overmighties.pubber.app.designsystem.UIText;

import lombok.Getter;


@Getter
public class AccFirebaseDSError extends AppError {
    public enum Type{
        EMAIL,
        PASSWORD,
        OTHER
    }

    private final Type type;
    public static final String TAG="AccFirebaseDSError";
    public AccFirebaseDSError(String logMessage, UIText userMsg, Type type) {
        super(logMessage, userMsg);
        this.type=type;
    }

//    @Getter
//    public static final class CustomAuthException extends AccFirebaseDSError{
//        private final String errorCode;
//
//        public CustomAuthException (String errorCode, String logMes){
//            super(logMes,null);
//            this.errorCode = errorCode;
//        }
//    }
    public static final class BadFormatOfEmail extends AccFirebaseDSError{
        private static final UIText uiText= new  UIText.ResourceString (R.string.BAD_FORMAT_OF_EMAIL_MESSAGE);
        public BadFormatOfEmail(String logMsg) {
            super(logMsg,uiText,Type.EMAIL);
        }
    }
    public static final class BadFormatOfPassword extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.BAD_FORMAT_OF_PASSWORD_MESSAGE);
        public BadFormatOfPassword(String logMsg) {
            super(logMsg,uiText,Type.PASSWORD);
        }
    }
    public static final class WeakPassword extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.WEAK_PASSWORD_MESSAGE);
        public WeakPassword(String logMsg) {
            super(logMsg,uiText,Type.PASSWORD);
        }
    }
    public static final class BadPassword extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.BAD_PASSWORD_MESSAGE);
        public BadPassword(String logMsg) {
            super(logMsg,uiText,Type.PASSWORD);
        }
    }
    public static final class NoSuchEmail extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.NO_SUCH_EMAIL);
        public NoSuchEmail(String logMsg) {
            super(logMsg,uiText,Type.EMAIL);
        }
    }
    public static final class NoneLoggedAccount extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.NONE_LOGGED_ACCOUNT);
        public NoneLoggedAccount(String logMsg) {
            super(logMsg,uiText,Type.OTHER);
        }
    }
    public static final class NoDisplayName extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.NO_DISPLAY_NAME);
        public NoDisplayName(String logMsg) {
            super(logMsg,uiText,Type.OTHER);
        }
    }
    public static final class EmailIsUsed extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.EMAIL_IS_USED);
        public EmailIsUsed(String logMsg) {
            super(logMsg,uiText,Type.EMAIL);
        }
    }
    public static final class DifferentInternalError extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.UNKNOWN_ERROR);
        public DifferentInternalError(String logMsg) {
            super(logMsg,uiText,Type.OTHER);
        }
    }
    public static final class UnknownError extends AccFirebaseDSError {
        private static final UIText uiText= new  UIText.ResourceString (R.string.UNKNOWN_ERROR);
        public UnknownError(String logMsg) {
            super(logMsg,uiText,Type.OTHER);
        }
    }
    public static AccFirebaseDSError getByErrorCode(String firebaseErrorCode, String logMess){
        Log.i(TAG,firebaseErrorCode);
        switch (firebaseErrorCode){
            case "ERROR_INVALID_EMAIL" :{
                return new BadFormatOfEmail(logMess);
            }
            case "ERROR_USER_NOT_FOUND"  :{
                return new NoSuchEmail(logMess);
            }
            case "ERROR_WRONG_PASSWORD":
            case "ERROR_INVALID_CREDENTIAL" : {
                return new BadPassword(logMess);
            }
            case "ERROR_EMAIL_ALREADY_IN_USE" : {
                return new EmailIsUsed(logMess);
            }
            case "ERROR_WEAK_PASSWORD" :{
                return new WeakPassword(logMess);
            }
            default : {
                return new DifferentInternalError(logMess);
            }
        }
    }
}

