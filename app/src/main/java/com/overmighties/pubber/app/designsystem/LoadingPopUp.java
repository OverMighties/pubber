package com.overmighties.pubber.app.designsystem;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.overmighties.pubber.R;

public class LoadingPopUp {
    private static ProgressBar progressBar;

    public static void show(Activity activity, Integer estimatedDurationSec){
        View popUpView = LayoutInflater.from(activity).inflate(R.layout.test, null);
        PopupWindow popupWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        progressBar = popUpView.findViewById(R.id.progressBar);
        popupWindow.showAsDropDown(activity.findViewById(R.id.main_activity), Gravity.CENTER, 0, 0);


        startProgressAnimation(estimatedDurationSec);
    }

    private static void startProgressAnimation(Integer duration) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);  // Animate from 0 to 100%
        animator.setDuration(duration*1000);                          // 100 seconds total duration
        animator.setRepeatCount(ValueAnimator.INFINITE);       // Repeat animation infinitely

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                progressBar.setProgress(progress);
            }
        });

        animator.start();
    }
}
