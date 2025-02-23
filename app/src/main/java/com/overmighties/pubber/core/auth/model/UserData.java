package com.overmighties.pubber.core.auth.model;

import android.net.Uri;

import org.jetbrains.annotations.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {
    @Nullable
    private String userId;
    @Nullable
    private String email;
    @Nullable
    private String username;
    @Nullable
    private Uri photoUrl;
}
