package com.overmighties.pubber.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.NavigationBar;
import com.overmighties.pubber.data.PubData;

import java.util.ArrayList;

public class DetailFragment extends Fragment
{
    private ArrayList<Integer> photolist=new ArrayList<Integer>();


    public DetailFragment() {super(R.layout.detail);}

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        //To ukrywa navigation bar
        NavigationBar.smoothHide(getActivity().findViewById(R.id.nav_view));
        photolist.add(R.drawable.zdjecie1);
        photolist.add(R.drawable.zdjecie2);
        photolist.add(R.drawable.zdjecie3);
        photolist.add(R.drawable.zdjecie4);
        photolist.add(R.drawable.zdjecie5);
        photolist.add(R.drawable.zdjecie6);
        //method for getting all of informations about exact pub
        DetailAdapter();
        //method for setting up image slider by making imageview
        SetUpImageSlider(photolist);





    }

    private void DetailAdapter()
    {
        PubData pub= AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue().get(AppContainer.getInstance().getPubSearchingContainer().getPosition().getValue());
        ((TextView)requireView().findViewById(R.id.name)).setText(pub.getName());
    }

    private void SetUpImageSlider(ArrayList<Integer> fotki)
    {
        ConstraintLayout constraintLayout=(ConstraintLayout) requireView().findViewById(R.id.ImageSlider);
        ShapeableImageView shapeableImageView=new ShapeableImageView(getContext());
        ConstraintSet constraintSet=new ConstraintSet();

        ShapeAppearanceModel shapeAppearanceModel=new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,30)
                .build();
        //dlugosci
        int bigwidth=dpToPx(170);
        int bigheight=dpToPx(270);
        int smallwidth=dpToPx(100);
        int smallheight=dpToPx(130);
        //set params
        for(int i=0;i< fotki.size();i=i+3) {
            shapeableImageView = new ShapeableImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bigwidth, bigheight);
            shapeableImageView.setLayoutParams(params);
            //generate id
            shapeableImageView.setId(View.generateViewId());
            constraintLayout.addView(shapeableImageView);
            //setting up constraintset
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 80+i*(280));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.applyTo(constraintLayout);
            //background color and shape
            shapeableImageView.setBackgroundResource(fotki.get(i));
            shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);
            //next ImageView
            shapeableImageView = new ShapeableImageView(getContext());
            params = new LinearLayout.LayoutParams(smallwidth, smallheight);
            shapeableImageView.setLayoutParams(params);
            shapeableImageView.setId(View.generateViewId());
            constraintLayout.addView(shapeableImageView);
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 600+i*(280));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.applyTo(constraintLayout);
            shapeableImageView.setBackgroundResource(fotki.get(i+1));
            shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);
            //nextImageView
            shapeableImageView = new ShapeableImageView(getContext());
            params = new LinearLayout.LayoutParams(smallwidth, smallheight);
            shapeableImageView.setLayoutParams(params);
            shapeableImageView.setId(View.generateViewId());
            constraintLayout.addView(shapeableImageView);
            constraintSet.clone(constraintLayout);
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 600+i*(280));
            constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, dpToPx(140));
            constraintSet.applyTo(constraintLayout);
            shapeableImageView.setBackgroundResource(fotki.get(i+2));
            shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);
        }
        shapeableImageView = new ShapeableImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bigwidth, bigheight);
        shapeableImageView.setLayoutParams(params);
        shapeableImageView.setId(View.generateViewId());
        constraintLayout.addView(shapeableImageView);
        constraintSet.clone(constraintLayout);
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 480+(fotki.size()-3)*(280));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, dpToPx(140));
        constraintSet.applyTo(constraintLayout);

    }



    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}