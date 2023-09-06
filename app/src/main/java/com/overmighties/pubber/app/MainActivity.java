package com.overmighties.pubber.app;


import static androidx.navigation.fragment.FragmentKt.findNavController;
import static com.overmighties.pubber.app.Constants.NUMBERSDAYS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_DISMISS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_DAYS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_DISMISS_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_IDS;
import static com.overmighties.pubber.app.Constants.POP_UP_TIME_TEXTIDS;
import static com.overmighties.pubber.app.Constants.SORT_POP_UP_IDS;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.overmighties.pubber.R;
import com.overmighties.pubber.databinding.ActivityMainBinding;
import com.overmighties.pubber.ui.FiltrationFragment;
import com.overmighties.pubber.ui.HotPubsFragment;
import com.overmighties.pubber.ui.HotPubsFragmentDirections;
import com.overmighties.pubber.ui.SavedFragment;
import com.overmighties.pubber.ui.SavedFragmentDirections;
import com.overmighties.pubber.ui.SearcherFragment;
import com.overmighties.pubber.ui.SearcherFragmentDirections;
import com.overmighties.pubber.util.SortUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import lombok.val;


public class MainActivity extends AppCompatActivity {


    public static final String FILE_NAME = "saved.txt";

    private HotPubsFragment hotPubsFragment;
    private SavedFragment savedFragment;
    private SearcherFragment searcherFragment;
    private View popUpView;
    private PopupWindow popupWindow;

    private ActivityMainBinding binding;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searcherFragment = new SearcherFragment();
        savedFragment = new SavedFragment();
        hotPubsFragment = new HotPubsFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_saved, R.id.navigation_hot_pubs)
                .build();



        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_list) {

                    switch (AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().getValue())
                    {
                        case 2:
                            Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(HotPubsFragmentDirections.hotPubstoSearcher());
                            break;
                        case 3:
                            Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(SavedFragmentDirections.savedToSearcher());
                            break;
                    }
                    AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().setValue(1);
                    return true;
                } else {
                    if (item.getItemId() == R.id.navigation_hot_pubs) {

                        switch (AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().getValue())
                        {
                            case 1:
                                Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(SearcherFragmentDirections.searcherToHotPubs());
                                break;
                            case 3:
                                Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(SavedFragmentDirections.savedToHotPubs());
                                break;
                        }
                        AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().setValue(2);
                        return true;
                    } else {
                        if (item.getItemId() == R.id.navigation_saved) {
                            switch (AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().getValue())
                            {
                                case 1:
                                    Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(SearcherFragmentDirections.searcherToSaved());
                                    break;
                                case 2:
                                    Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView).navigate(HotPubsFragmentDirections.hotPubstoSaved());
                                    break;
                            }
                            AppContainer.getInstance().getPubSearchingContainer().getNavigationInformation().setValue(3);
                            return true;
                        }
                    }
                }
                return false;
            }
        });



    //Code for all of pop ups in the program
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Observer<String> name = sort -> {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            switch (AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().getValue()) {

                case "days":
                    //Down Menus days in FilterFragment

                    popUpView = inflater.inflate(R.layout.menu_pop_up_days, null);
                    popupWindow = new PopupWindow(popUpView,
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT, true);
                    //Listeners For popup days in filtrarion screen
                    listenersmenudays(popupWindow, popUpView);
                    (findViewById(R.id.FiltrationScreen)).post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(findViewById(R.id.FiltrationScreen), Gravity.BOTTOM, 0, 0);
                        }
                    });
                    break;
                case "time":
                    popUpView = inflater.inflate(R.layout.menu_pop_up_time, null);
                    popupWindow = new PopupWindow(popUpView,
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT, true);
                    //Listeners For popup time in filtrarion screen
                    listenersmenutime(popupWindow, popUpView);
                    (findViewById(R.id.FiltrationScreen)).post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(findViewById(R.id.FiltrationScreen), Gravity.BOTTOM, 0, 0);
                        }
                    });
                    break;
                case "sort":
                    NavigationBar.smoothHide(findViewById(R.id.nav_view));
                    popUpView = inflater.inflate(R.layout.sort_pop_up, null);
                    popupWindow = new PopupWindow(popUpView,
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT, true);
                    //setting listeners in sorting popupview
                    listeners(popupWindow, popUpView);
                    //checking which one kind of sorting is selected
                    check(popUpView);
                    (findViewById(R.id.search)).post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(findViewById(R.id.search), Gravity.BOTTOM, 0, 0);
                        }
                    });
                    break;

            }
        };
        AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().observe(this,name);
    }


    private void listenersmenutime(PopupWindow popupWindow, View popUpView)
    {
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        for(int i=1;i<51;i++){numbers.add(i);}
        for (var id : POP_UP_TIME_IDS) {
            for(Integer n:numbers)
            {
                popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unchecktime(n, popUpView,id);
                    }
                });
                break;
            }
            numbers.remove(number);
            number=number+1;
        }
        for(var id:POP_UP_TIME_DISMISS_IDS)
        {
            popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    findViewById(R.id.view2).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
                    ((ImageView)findViewById(R.id.imageView8)).setImageResource(R.drawable.arrowblack);
                }
            });

        }
    }

    private void unchecktime(Integer n, View popUpView,Integer Background) {
        for (var id : POP_UP_TIME_IDS)
        {
            popUpView.findViewById(id).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        for (var id : POP_UP_TIME_TEXTIDS)
        {

            ((TextView) popUpView.findViewById(id)).setTextColor(Color.parseColor("#000000"));
        }
        popUpView.findViewById(Background).setBackgroundColor(Color.parseColor("#693D2B"));
        ((TextView) popUpView.findViewById(POP_UP_TIME_TEXTIDS[n-1])).setTextColor(Color.parseColor("#FFFFFF"));
        String text=(((TextView)popUpView.findViewById(POP_UP_TIME_TEXTIDS[n-1])).getText().toString());
        ((TextView)(findViewById(R.id.textView18))).setText(text);
    }


    private void listenersmenudays(PopupWindow popupWindow,View popUpView)
    {
        for(var id:POP_UP_DAYS_DISMISS_IDS)
        {
            popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    findViewById(R.id.view).setBackgroundResource(R.drawable.menu_drop_out_list_shape);
                    ((ImageView)findViewById(R.id.imageView7)).setImageResource(R.drawable.arrowblack);
                }
            });
        }
        ArrayList <Integer>numbers=new ArrayList<>();
        Integer number=1;
        for(var n:NUMBERSDAYS){numbers.add(n);}
            for (var id : POP_UP_DAYS_IDS) {
                for(Integer n:numbers)
                {
                    popUpView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            uncheckdays(n, popUpView);
                        }
                    });
                    break;
                }
                numbers.remove(number);
                number=number+1;
        }


    }

    //Method for unchecking in pop up in FiltrationScreen
    private void uncheckdays(Integer checked,View popUpView)
    {
        Log.d("tak",checked.toString());
        popUpView.findViewById(R.id.jeden).setBackgroundResource(R.drawable.menu_drop_out_list_shape_up);
        ((TextView) popUpView.findViewById(R.id.textViewjeden)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.dwa).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView) popUpView.findViewById(R.id.textViewdwa)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.trzy).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView) popUpView.findViewById(R.id.textViewtrzy)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.cztery).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView) popUpView.findViewById(R.id.textViewcztery)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.piec).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView) popUpView.findViewById(R.id.textViewpiec)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.szesc).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView) popUpView.findViewById(R.id.textViewszesc)).setTextColor(Color.parseColor("#000000"));
        popUpView.findViewById(R.id.siedem).setBackgroundResource(R.drawable.menu_drop_out_list_shape_down);
        ((TextView) popUpView.findViewById(R.id.textViewsiedem)).setTextColor(Color.parseColor("#000000"));
        switch (checked) {
            case 1:
                popUpView.findViewById(R.id.jeden).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_up);
                ((TextView) popUpView.findViewById(R.id.textViewjeden)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Poniedziałek");
                break;
            case 2:
                popUpView.findViewById(R.id.dwa).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(R.id.textViewdwa)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Wtorek");
                break;
            case 3:
                popUpView.findViewById(R.id.trzy).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(R.id.textViewtrzy)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Środa");
                break;
            case 4:
                popUpView.findViewById(R.id.cztery).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(R.id.textViewcztery)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Czwartek");
                break;
            case 5:
                popUpView.findViewById(R.id.piec).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(R.id.textViewpiec)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Piątek");
                break;
            case 6:
                popUpView.findViewById(R.id.szesc).setBackgroundColor(Color.parseColor("#693D2B"));
                ((TextView) popUpView.findViewById(R.id.textViewszesc)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Sobota");
                break;
            case 7:
                popUpView.findViewById(R.id.siedem).setBackgroundResource(R.drawable.menu_drop_out_shape_highlight_down);
                ((TextView) popUpView.findViewById(R.id.textViewsiedem)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView)(findViewById(R.id.textView17))).setText("Niedziela");
                break;
        }
    }




    private void check(View popUpView) {
        String word = ((TextView) findViewById(R.id.sorttext)).getText().toString();
        switch (word)
        {
            case " Trafność":
                ((RadioButton) popUpView.findViewById(R.id.trafnosc)).setChecked(true);
                break;
            case " Alfabetycznie":
                ((RadioButton) popUpView.findViewById(R.id.alfabetycznie)).setChecked(true);
                break;
            case " Najwyżej oceniane":
                ((RadioButton) popUpView.findViewById(R.id.ocena)).setChecked(true);
                break;
            case  " Najmniejsza Odległość":
                ((RadioButton) popUpView.findViewById(R.id.najodleglosc)).setChecked(true);
                break;
        }
    }






    private void listeners (PopupWindow popupWindow, View popupView){
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                NavigationBar.smoothPopUp(findViewById(R.id.nav_view));
                TextView text = findViewById(R.id.sorttext);
                SortUtil sort = new SortUtil();
                if (((RadioButton) popupView.findViewById(R.id.trafnosc)).isChecked()) {
                    text.setText(" Trafność");
                    sort.sortUtli(1);
                } else {
                    if (((RadioButton) popupView.findViewById(R.id.ocena)).isChecked()) {
                        text.setText(" Najwyżej oceniane");
                        sort.sortUtli(3);
                    } else {
                        if (((RadioButton) popupView.findViewById(R.id.alfabetycznie)).isChecked()) {
                            text.setText(" Alfabetycznie");
                            sort.sortUtli(2);
                        } else {
                            text.setText(" Najmniejsza Odległość");
                            sort.sortUtli(4);
                        }
                    }
                }
            }
        });
        ((ConstraintLayout) popupView.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    popupWindow.dismiss();
                                }
        });
        for (var id : SORT_POP_UP_IDS) {
            ((RadioButton) popupView.findViewById(id)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((RadioButton) popupView.findViewById(id)).isChecked()) {
                        ((RadioButton) popupView.findViewById(R.id.ocena)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.najodleglosc)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.alfabetycznie)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.trafnosc)).setChecked(false);
                        ((RadioButton) popupView.findViewById(id)).setChecked(true);
                    } else {
                    }
                }
            });
        }

    }
}

