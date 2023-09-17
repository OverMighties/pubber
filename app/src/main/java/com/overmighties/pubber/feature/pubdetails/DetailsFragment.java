package com.overmighties.pubber.feature.pubdetails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.widget.ViewPager2;


import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;

public class DetailsFragment extends Fragment
{

    private ViewPager2 viewPager;
    private DetailsViewModel viewModel;
    public DetailsFragment() {super(R.layout.details);}

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        //To ukrywa navigation bar
        NavigationBar.smoothHide(getActivity().findViewById(R.id.nav_view));




    }
    /*
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

     */
}