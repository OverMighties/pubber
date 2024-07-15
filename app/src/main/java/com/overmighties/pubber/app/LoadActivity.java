package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.DarkModeTheme;


public class LoadActivity extends DarkModeTheme {
    public static final int LOGO_DELAY=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            findViewById(R.id.title_textView).setVisibility(View.GONE);
            goToMainActivity();
        }, LOGO_DELAY);
    }
    public void goToMainActivity()
    {
        Intent i=new Intent(LoadActivity.this, MainActivity.class);
        startActivity(i);
    }
    private  <T extends Fragment> void replaceWithFragment(Class<T> fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerViewLoad, fragment,null)
                .commit();
    }

}