package com.overmighties.pubber.app.designsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.overmighties.pubber.R;
import com.overmighties.pubber.util.DimensionsConverter;

public class InfoLayout {

    private static int[] anchorScreenLocation = new int[2];




    public static void show(Context context, ViewStub viewStub, View anchorView, String message){
        viewStub.setOnInflateListener(((stub, inflated) -> {
            anchorView.getLocationOnScreen(anchorScreenLocation);
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            //set inflated view under the anchorview
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewStub.getLayoutParams();
            layoutParams.setMargins(0, anchorScreenLocation[1],0, 0);
            viewStub.setLayoutParams(layoutParams);
            //set triangle ImageView Location on screen, first calculate biast
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout) inflated);
            constraintSet.setHorizontalBias(R.id.information_image_triangle, anchorScreenLocation[0]/screenWidth);
            constraintSet.applyTo((ConstraintLayout) inflated);
            ((TextView)inflated.findViewById(R.id.information_text_message)).setText(message);
            showAnimatedView(inflated, anchorView);
        }));

        viewStub.inflate();
    }

    private static void showAnimatedView(final View expandableView, View anchorView) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1.0f,
                0, 1.0f,
                Animation.ABSOLUTE, anchorScreenLocation[0],
                Animation.ABSOLUTE, -anchorView.getHeight());

        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(true);

        expandableView.startAnimation(scaleAnimation);
    }
}
