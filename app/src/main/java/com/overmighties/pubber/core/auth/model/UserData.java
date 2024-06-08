package com.overmighties.pubber.core.auth.model;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {
    private String userId;
    private String email;
    private String username;
    private Uri photoUrl;
}
