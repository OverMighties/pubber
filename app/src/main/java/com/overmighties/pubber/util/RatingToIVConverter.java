package com.overmighties.pubber.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.overmighties.pubber.R;

import java.util.List;

public class RatingToIVConverter {
    public void convert(List<ImageView> ImageViewArraylist, Integer height, ConstraintLayout constraintLayout, Float rating, Integer marginTop, Integer distance, boolean isRating, Context context){
        Integer fullId;
        Integer halfFullId;
        Integer emptyId;
        if(isRating){
            fullId = R.drawable.file;
            halfFullId = R.drawable.v4;
            emptyId = R.drawable.v5;
        } else{
            fullId = R.drawable.ic_progress_indicator_full;
            halfFullId = R.drawable.ic_proggres_indicator_half;
            emptyId = R.drawable.ic_progress_indicator_empty;
        }
        for(int n = 0; n<5;n++){
            ConstraintSet constraintSet = new ConstraintSet();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (DimensionsConverter.pxFromDp(context, height)*132/244), (int) DimensionsConverter.pxFromDp(context, height));

            ImageViewArraylist.get(n).setLayoutParams(layoutParams);
            ImageViewArraylist.get(n).setId(View.generateViewId());

            constraintLayout.addView(ImageViewArraylist.get(n));
            constraintSet.clone(constraintLayout);
            constraintSet.connect(ImageViewArraylist.get(n).getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START,   n *(int) DimensionsConverter.pxFromDp(context, distance));
            constraintSet.connect(ImageViewArraylist.get(n).getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(ImageViewArraylist.get(n).getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);

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

}
