package com.overmighties.pubber.util;

import androidx.annotation.Nullable;

@FunctionalInterface
public interface TriConsumer<A,B,C> {
    void accept(A a,@Nullable B b,@Nullable C c);
}