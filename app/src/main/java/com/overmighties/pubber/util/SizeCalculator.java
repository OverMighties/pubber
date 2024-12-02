package com.overmighties.pubber.util;

import android.graphics.Paint;
import android.widget.TextView;

public class SizeCalculator {

    public static int calculateTextViewWidth(String text, TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        return (int) paint.measureText(text);
    }
}
