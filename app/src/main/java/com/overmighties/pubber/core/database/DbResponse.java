package com.overmighties.pubber.core.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DbResponse {

    private String message;
    private Status status;


    public enum Status
    {
        OK,
        UNKNOWN_ERROR
    }
}
