package com.overmighties.pubber.util;


import android.content.Context;

public class DimensionsConverter {
    //Blocking default constructor
    private DimensionsConverter(){
        throw new AssertionError();
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
