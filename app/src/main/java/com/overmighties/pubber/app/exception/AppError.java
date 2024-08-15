package com.overmighties.pubber.app.exception;

import com.overmighties.pubber.app.designsystem.UIText;

import lombok.Getter;

@Getter
public abstract class AppError extends Throwable{
    private final UIText userMsg;
    private final String logMessage;
    public AppError(String logMessage, UIText userMsg) {
        super(logMessage);
        this.userMsg = userMsg;
        this.logMessage = logMessage;
    }

}

