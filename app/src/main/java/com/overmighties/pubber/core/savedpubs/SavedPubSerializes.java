package com.overmighties.pubber.core.savedpubs;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;

import com.overmighties.pubber.app.core.savedpubs.PubProtoList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class SavedPubSerializes implements Serializer<PubProtoList> {

    public static final String TAG = "SavedPubsSerializer";

    @Override
    public PubProtoList getDefaultValue() {
        return PubProtoList.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super PubProtoList> continuation) {
        try {
            return PubProtoList.parseFrom(inputStream);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage() + "Cannout read from the file");
            return null;
        }
    }

    @Nullable
    @Override
    public Object writeTo(PubProtoList favouritePub, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            favouritePub.writeTo(outputStream);
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage() + "Cannout write to the file");
            return false;
        }
    }
}
