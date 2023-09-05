package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.overmighties.pubber.R;


public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i=new Intent(LoadActivity.this, MainActivity.class);
            startActivity(i);

        }, 3000);
    }



}