package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.PlaceChoiceFragment;


public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            findViewById(R.id.title_textView).setVisibility(View.GONE);
            replaceWithFragment(PlaceChoiceFragment.class);
        }, 3000);
    }
    public void goToMainActivity()
    {
        Intent i=new Intent(LoadActivity.this, MainActivity.class);
        startActivity(i);
    }
    private  <T extends Fragment> boolean  replaceWithFragment(Class<T> fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerViewLoad, fragment,null)
                .commit();
        return true;
    }

}