package com.overmighties.pubber.feature.pubdetails.TabFragments;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.graphics.Color;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

public class TabFragmentsViewModel extends ViewModel {
    @Getter
    private static final MutableLiveData<Long> pubId =new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<TabFragmentsUiState> tabFragmentsUiState =new MutableLiveData<>();
    private final MutableLiveData<View> popUpView=new MutableLiveData<View>();

    private final MutableLiveData<TabFragmentsUiState> uiState=
            new MutableLiveData(new TabFragmentsUiState());
    public LiveData<TabFragmentsUiState> getUiState(){
        return uiState;
    }

    public static final ViewModelInitializer<TabFragmentsViewModel> initializer=new ViewModelInitializer<>(
            TabFragmentsViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new TabFragmentsViewModel(savedStateHandle);
            }
    );

    public TabFragmentsViewModel(SavedStateHandle savedStateHandle){
    }

    public void SetCheckedChipsIds(ChipGroup beerChipGroup, ChipGroup drinkChipGroup, ArrayList<DrinkDto> drinksDataSet1){
        List <Integer> IdsBeer = new ArrayList<>();
        List <Integer> IdsDrink = new ArrayList<>();

        for (DrinkDto alldrinks:drinksDataSet1){
           if(alldrinks.getType().equals("Beer")){
               for(int i=0;i<beerChipGroup.getChildCount();i++){
                   if(((Chip)beerChipGroup.getChildAt(i)).getText().toString().toLowerCase().replaceAll("\\s+","").
                           equals(alldrinks.getName().toLowerCase().replaceAll("\\s+",""))){
                       IdsBeer.add(((Chip)beerChipGroup.getChildAt(i)).getId());
                   }
               }
           }
           else{
               for(int i=0;i<drinkChipGroup.getChildCount();i++){
                   if(((Chip)drinkChipGroup.getChildAt(i)).getText().toString().toLowerCase().replaceAll("\\s+","").
                           equals(alldrinks.getName().toLowerCase().replaceAll("\\s+",""))){
                       IdsDrink.add(((Chip)drinkChipGroup.getChildAt(i)).getId());
                   }
               }
           }
        }
        tabFragmentsUiState.getValue().setIdsOfBeerChips(IdsBeer);
        tabFragmentsUiState.getValue().setIdsOfDrinkChips(IdsDrink);

    }
    public void addUnderLineLink(TextView textView,int color) {
        if (textView != null) {
            SpannableString content = new SpannableString(textView.getText().toString());
            UnderlineSpan us=new UnderlineSpan();
            TextPaint tp=new TextPaint();
            tp.setColor(color);
            us.updateDrawState(tp);
            content.setSpan(us, 0, textView.getText().toString().length(), 0);
            textView.setText(content);
        }
    }

    public int stringToIntRatingConverter(String rating){
        return Integer.valueOf(rating.replace(",","."));
    }

    public void setUpGoogleTextView(TextView textView,TextAppearanceSpan red, TextAppearanceSpan blue, TextAppearanceSpan green, TextAppearanceSpan yellow){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();


        SpannableString string = new SpannableString("G");
        string.setSpan(blue,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(red,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(yellow,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("g");
        string.setSpan(blue,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("l");
        string.setSpan(green,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("e");
        string.setSpan(red,0,1,0);
        spannableStringBuilder.append(string);

        textView.setText(spannableStringBuilder);
    }
}
