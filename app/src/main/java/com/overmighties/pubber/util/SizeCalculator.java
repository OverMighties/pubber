package com.overmighties.pubber.util;

import android.graphics.Paint;
import android.widget.TextView;

public class SizeCalculator {
    //Blocking default constructor
    private SizeCalculator(){
        throw new AssertionError();
    }
    public static int calculateTextViewWidth(String text, TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        return (int) paint.measureText(text);
    }
}
