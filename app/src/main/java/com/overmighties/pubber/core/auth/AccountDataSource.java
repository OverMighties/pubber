package com.overmighties.pubber.core.auth;

import androidx.annotation.NonNull;

import com.overmighties.pubber.core.auth.model.UserData;

import io.reactivex.rxjava3.core.Single;

public interface AccountDataSource {
    UserData currentUser();
//    boolean getLastUser();
    Single<UserData> signIn(String email, String password);
    Single<UserData> signInWithCredentials(String idToken);
    Single<UserData> signUp(String email, String password);
    void signOut();
    Single<Object> deleteAccount();
    Single<Object> updateUserEmail(@NonNull String email);
    Single<Object> updateUserProfile(@NonNull UserData userChangedData);
}
