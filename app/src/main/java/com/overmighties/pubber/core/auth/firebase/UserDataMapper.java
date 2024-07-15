package com.overmighties.pubber.core.auth.firebase;

import com.google.firebase.auth.FirebaseUser;
import com.overmighties.pubber.core.auth.model.UserData;

public class UserDataMapper {
    public static UserData mapToUserData(FirebaseUser firebaseUser){
        return  UserData.builder()
                .userId(firebaseUser.getUid())
                .email(firebaseUser.getEmail())
                .username(firebaseUser.getDisplayName())
                .photoUrl(firebaseUser.getPhotoUrl())
                .build();
    }
}
