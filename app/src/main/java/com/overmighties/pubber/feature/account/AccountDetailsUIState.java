package com.overmighties.pubber.feature.account;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsUIState {
    public static final String UNKNOWN_SEX ="Unknown sex";
    public static final String UNKNOWN ="Unknown";
    public static final Integer NUMBER_OF_FIELDS=4;
    private String username=UNKNOWN;
    private String email=UNKNOWN;
    private String sex=UNKNOWN_SEX;
    private Uri photoUrl=Uri.EMPTY;

}
