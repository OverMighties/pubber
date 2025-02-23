package com.overmighties.pubber.sync;

public abstract class Result<T> {
    private Result() {}

    public static final class Success<T> extends Result<T> {
        public final T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends Result<T> {
        public final Exception exception;
        public final String message;

        public Error(Exception exception,String message) {
            this.exception = exception;
            this.message=message;
        }
    }
}

