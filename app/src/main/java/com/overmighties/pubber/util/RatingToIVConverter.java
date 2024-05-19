package com.overmighties.pubber.util;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.overmighties.pubber.R;

import java.util.ArrayList;

public class RatingToIVConverter {
    public void Convert(ArrayList<ImageView> ImageViewArraylist, Integer size, ConstraintLayout constraintLayout, Float raiting, Integer marginTop,Integer distance){
        for(int n = 0; n<5;n++){
            ConstraintSet constraintSet = new ConstraintSet();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpToPx(size),dpToPx(size));

            ImageViewArraylist.get(n).setLayoutParams(layoutParams);
            ImageViewArraylist.get(n).setId(View.generateViewId());

            constraintLayout.addView(ImageViewArraylist.get(n));
            constraintSet.clone(constraintLayout);
            constraintSet.connect(ImageViewArraylist.get(n).getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START,   n *dpToPx(distance));
            constraintSet.connect(ImageViewArraylist.get(n).getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, dpToPx(marginTop));
            constraintSet.applyTo(constraintLayout);

            if (raiting >= n+0.75){
                ImageViewArraylist.get(n).setBackgroundResource(R.drawable.beer_full);
            }
            else{
                if (raiting >= (float)(n+0.25)){
                    ImageViewArraylist.get(n).setBackgroundResource(R.drawable.beer_half_full);
                }
                else{
                    ImageViewArraylist.get(n).setBackgroundResource(R.drawable.beer_empty);
                }
            }

        }
    }

    private static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
