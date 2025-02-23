package com.overmighties.pubber.util;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.overmighties.pubber.R;

import java.util.List;

public class RatingToIVConverter {
    public void convert(List<ImageView> ImageViewArraylist, Integer size, ConstraintLayout constraintLayout, Float rating, Integer marginTop, Integer distance, boolean isRating){
        Integer fullId;
        Integer halfFullId;
        Integer emptyId;
        if(isRating){
            fullId = R.drawable.beer_full;
            halfFullId = R.drawable.beer_half_full;
            emptyId = R.drawable.beer_empty;
        } else{
            fullId = R.drawable.ic_progress_indicator_full;
            halfFullId = R.drawable.ic_proggres_indicator_half;
            emptyId = R.drawable.ic_progress_indicator_empty;
        }
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

            if (rating >= n+0.75){
                ImageViewArraylist.get(n).setBackgroundResource(fullId);
            }
            else{
                if (rating >= (float)(n+0.25)){
                    ImageViewArraylist.get(n).setBackgroundResource(halfFullId);
                }
                else{
                    ImageViewArraylist.get(n).setBackgroundResource(emptyId);
                }
            }

        }
    }

    private static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
