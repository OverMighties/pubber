package com.overmighties.pubber.core.auth;

import com.overmighties.pubber.core.auth.model.UserData;

import io.reactivex.rxjava3.core.Single;

public interface AccountDataSource {
    Single<UserData> currentUser();
    boolean hasUser();
    Single<UserData> signIn(String email, String password);
    Single<UserData> signInWithCredentials(String idToken);
    Single<UserData> signUp(String email, String password);
    void signOut();
    Single<Object> deleteAccount();
}
