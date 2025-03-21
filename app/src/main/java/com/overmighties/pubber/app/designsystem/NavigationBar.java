package com.overmighties.pubber.app.designsystem;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class NavigationBar {
    //Blocking default constructor
    private NavigationBar(){
        throw new AssertionError();
    }
    public static void smoothPopUp(View nav_bar, int duration)
    {
        nav_bar.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0,  nav_bar.getHeight(), 0);
        animate.setDuration(duration);
//        animate.setFillAfter(true);
        nav_bar.startAnimation(animate);
    }
    public static void smoothHide(View nav_bar, int duration)
    {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0,  nav_bar.getHeight());
        animate.setDuration(duration);
        nav_bar.startAnimation(animate);
        nav_bar.setVisibility(View.GONE);
    }

    public static void hideTransitionTopAndBottomBar(View top_bar, View top_bar_view, View nav_bar, int duration){
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation nav_animate = new TranslateAnimation(0, 0, 0,  nav_bar.getHeight());
        nav_animate.setDuration(duration);
        animationSet.addAnimation(nav_animate);

        TranslateAnimation top_animate = new TranslateAnimation(0, 0,  top_bar.getHeight(), 0);
        top_animate.setDuration(duration);
        animationSet.addAnimation(top_animate);

        nav_bar.startAnimation(nav_animate);
        nav_bar.setVisibility(View.INVISIBLE);

        top_bar.startAnimation(top_animate);
        top_bar_view.setVisibility(View.GONE);

    }



}
