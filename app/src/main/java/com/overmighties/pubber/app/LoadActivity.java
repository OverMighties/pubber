package com.overmighties.pubber.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.PlaceChoiceFragment;
import com.overmighties.pubber.app.ui.PlaceChoiceViewModel;


public class LoadActivity extends AppCompatActivity {
    private PlaceChoiceViewModel placeChoiceViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        placeChoiceViewModel=new ViewModelProvider(this).get(PlaceChoiceViewModel.class);
        placeChoiceViewModel.getCity().observe(this, this::goToMainActivity);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            findViewById(R.id.title_textView).setVisibility(View.GONE);
            replaceWithFragment(PlaceChoiceFragment.class);
        }, 3000);
    }
    public void goToMainActivity(String city)
    {
        Intent i=new Intent(LoadActivity.this, MainActivity.class)
                .putExtra(Intent.EXTRA_TEXT,city);
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