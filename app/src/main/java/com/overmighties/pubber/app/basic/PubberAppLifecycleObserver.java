package com.overmighties.pubber.app.basic;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import lombok.NonNull;

public class PubberAppLifecycleObserver implements DefaultLifecycleObserver {

    private final PubberAppLifecycleListener listener;

    public PubberAppLifecycleObserver(PubberAppLifecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        listener.onAppForegrounded(); // Notify that the app is in the foreground
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        listener.onAppBackgrounded(); // Notify that the app is in the background
    }
}
