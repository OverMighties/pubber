package com.overmighties.pubber.feature.pubdetails;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
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

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.app.PubberApp;

import lombok.Getter;
import lombok.Setter;

public class DetailsViewModel extends ViewModel {
    @Getter
    private static final MutableLiveData<Long> pubId =new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<PubDetailsUiState> pubDetails =new MutableLiveData<>();
    private final MutableLiveData<View> popUpView=new MutableLiveData<View>();

    private final MutableLiveData<PubDetailsUiState> uiState=
            new MutableLiveData(new PubDetailsUiState());
    public LiveData<PubDetailsUiState>getUiState(){
        return uiState;
    }

    public static final ViewModelInitializer<DetailsViewModel> initializer=new ViewModelInitializer<>(
            DetailsViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new DetailsViewModel(savedStateHandle);
            }
    );

    public DetailsViewModel(SavedStateHandle savedStateHandle){
    }


    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setPubDetails(PubDetailsUiState pubDetailsUiState) {
        pubDetails.setValue(pubDetailsUiState);
    }

    public void takeScreenShot(View view,Context context) {

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();

        if(view.getDrawingCache() == null){
            pubDetails.getValue().setCurrentScreen(null);
        }
        else {
            Bitmap snapshot = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
            Bitmap screen=BlurImage(snapshot,context);
            pubDetails.getValue().setCurrentScreen(screen);
        }

    }
    @SuppressLint("NewApi")
    Bitmap BlurImage (Bitmap input, Context context)
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
                                                             ShapeAppearanceModel shapeAppearanceModel,int number){
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
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 80+number*(280));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.applyTo(constraintLayout);
        //Setting up Image and shape
        shapeableImageView.setBackgroundResource(Pictureid);
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);


        return shapeableImageView;
    }
    public ShapeableImageView CustomingSmallShapeableImageView(ShapeableImageView shapeableImageView, ConstraintLayout constraintLayout,int Pictureid,
                                                             ShapeAppearanceModel shapeAppearanceModel,int number){
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
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 600+(number-1)*(280));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.applyTo(constraintLayout);
        }
        else {
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 600+(number-2)*(280));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP,dpToPx(140));
            constraintSet.applyTo(constraintLayout);
        }

        //Setting up Image and shape
        shapeableImageView.setBackgroundResource(Pictureid);
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);


        return shapeableImageView;
    }


}
