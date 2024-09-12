package com.overmighties.pubber.feature.pubdetails;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.util.DimensionsConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class DetailsViewModel extends ViewModel {
    @Getter
    private static final MutableLiveData<Long> pubId =new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<PubDetailsUiState> pubDetails =new MutableLiveData<>();
    private final MutableLiveData<View> popUpView=new MutableLiveData<>();
    private final MutableLiveData<PubDetailsUiState> uiState=
            new MutableLiveData<> (new PubDetailsUiState());
    public LiveData<PubDetailsUiState> getUiState(){
        return uiState;
    }

    public void setUiState(PubDetailsUiState ui){
        uiState.setValue(ui);
    }
    @Getter
    @Setter
    private Integer openedPubPosition = null;


    public static final ViewModelInitializer<DetailsViewModel> initializer=new ViewModelInitializer<>(
            DetailsViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new DetailsViewModel(savedStateHandle);
            }
    );

    public DetailsViewModel(SavedStateHandle savedStateHandle){}


    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void setPubDetails(PubDetailsUiState pubDetailsUiState) {
        pubDetails.setValue(pubDetailsUiState);
    }

    public void takeScreenShot(View view,Context context) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        Bitmap screen = BlurImage(bitmap, context);
        Objects.requireNonNull(pubDetails.getValue()).setCurrentScreen(screen);

    }
    @SuppressLint("NewApi")
    public Bitmap BlurImage (Bitmap input, Context context)
    {
        try
        {
            RenderScript rsScript = RenderScript.create(context);
            Allocation alloc = Allocation.createFromBitmap(rsScript, input);

            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rsScript,   Element.U8_4(rsScript));
            blur.setRadius(12);
            blur.setInput(alloc);

            Bitmap result = Bitmap.createBitmap(input.getWidth(), input.getHeight(), Bitmap.Config.ARGB_8888);
            Allocation outAlloc = Allocation.createFromBitmap(rsScript, result);

            blur.forEach(outAlloc);
            outAlloc.copyTo(result);

            rsScript.destroy();

            return result;
        }
        catch (Exception e) {
            return input;
        }

    }

    public ShapeableImageView CustomingBigShapeableImageView(ShapeableImageView shapeableImageView, ConstraintLayout constraintLayout,int Pictureid,
                                                             ShapeAppearanceModel shapeAppearanceModel,int number, Context context){
        int bigwidth=dpToPx(170);
        int bigheight=dpToPx(270);

        ConstraintSet constraintSet=new ConstraintSet();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bigwidth, bigheight);
        shapeableImageView.setLayoutParams(params);
        //generate id
        shapeableImageView.setId(View.generateViewId());
        constraintLayout.addView(shapeableImageView);
        //setting up constraintset
        constraintSet.clone(constraintLayout);
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) DimensionsConverter.pxFromDp(context,8 + number*100));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.applyTo(constraintLayout);
        //Setting up Image and shape
        shapeableImageView.setBackgroundResource(Pictureid);
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);


        return shapeableImageView;
    }
    public ShapeableImageView CustomingSmallShapeableImageView(ShapeableImageView shapeableImageView, ConstraintLayout constraintLayout,int Pictureid,
                                                             ShapeAppearanceModel shapeAppearanceModel,int number, Context context){
        int smallwidth=dpToPx(100);
        int smallheight=dpToPx(130);

        ConstraintSet constraintSet=new ConstraintSet();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(smallwidth, smallheight);
        shapeableImageView.setLayoutParams(params);
        //generate id
        shapeableImageView.setId(View.generateViewId());
        constraintLayout.addView(shapeableImageView);
        //setting up constraintset
        if(number%3==1){
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) DimensionsConverter.pxFromDp(context,192+(number-1)*(100)));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.applyTo(constraintLayout);
        }
        else {
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) DimensionsConverter.pxFromDp(context,192+(number-2)*(100)));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP,dpToPx(140));
            constraintSet.applyTo(constraintLayout);
        }

        //Setting up Image and shape
        shapeableImageView.setBackgroundResource(Pictureid);
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);


        return shapeableImageView;
    }

    public void setCheckedChipsIds(ChipGroup beerChipGroup, ChipGroup drinkChipGroup, List<DrinkDto> drinksDataSet1){
        List<Integer> IdsBeer = new ArrayList<>();
        List<Integer> IdsDrink = new ArrayList<>();

        for (DrinkDto allDrinks:drinksDataSet1){
            if(allDrinks.getType().equals("Beer")){
                for(int i=0;i<beerChipGroup.getChildCount();i++){
                    if(((Chip)beerChipGroup.getChildAt(i)).getText().toString().toLowerCase().replaceAll("\\s+","").
                            equals(allDrinks.getName().toLowerCase().replaceAll("\\s+",""))){
                        IdsBeer.add(beerChipGroup.getChildAt(i).getId());
                    }
                }
            }
            else{
                for(int i=0;i<drinkChipGroup.getChildCount();i++){
                    if(((Chip)drinkChipGroup.getChildAt(i)).getText().toString().toLowerCase().replaceAll("\\s+","").
                            equals(allDrinks.getName().toLowerCase().replaceAll("\\s+",""))){
                        IdsDrink.add(drinkChipGroup.getChildAt(i).getId());
                    }
                }
            }
        }
        getUiState().getValue().setIdsOfBeerChips(IdsBeer);
        getUiState().getValue().setIdsOfDrinkChips(IdsDrink);

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
        return Integer.parseInt(rating.replace(",","."));
    }

    public void setUpGoogleTextView(TextView textView, TextAppearanceSpan red, TextAppearanceSpan blue, TextAppearanceSpan green, TextAppearanceSpan yellow, TextAppearanceSpan blue2, TextAppearanceSpan red2){
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
        string.setSpan(blue2,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("l");
        string.setSpan(green,0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("e");
        string.setSpan(red2,0,1,0);
        spannableStringBuilder.append(string);

        textView.setText(spannableStringBuilder);
    }


}
