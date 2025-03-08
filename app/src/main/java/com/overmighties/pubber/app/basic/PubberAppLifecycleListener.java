package com.overmighties.pubber.app.basic;

public interface PubberAppLifecycleListener {
    void onAppForegrounded();
    void onAppResumed();
    void onAppBackgrounded();
}
