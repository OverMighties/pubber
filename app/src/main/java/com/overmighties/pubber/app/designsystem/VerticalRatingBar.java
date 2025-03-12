package com.overmighties.pubber.app.designsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.overmighties.pubber.R;
import com.overmighties.pubber.util.DimensionsConverter;

public class VerticalRatingBar extends View {
    private int numStars = 5;
    private float rating = 0f;

    private Drawable emptyBeer, halfBeer, fullBeer;
    private int beerWidth, beerHeight, fullBeerHeight, padding;

    public VerticalRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        emptyBeer = AppCompatResources.getDrawable(getContext(), R.drawable.ic_beer_empty_60);
        halfBeer = AppCompatResources.getDrawable(getContext(), R.drawable.ic_beer_half_full_60);
        fullBeer = AppCompatResources.getDrawable(getContext(), R.drawable.ic_beer_full_64);

        beerWidth = (int) DimensionsConverter.pxFromDp(getContext(), 32);
        beerHeight = (int) DimensionsConverter.pxFromDp(getContext(), 60);
        fullBeerHeight = (int) DimensionsConverter.pxFromDp(getContext(), 72);
        padding = (int) DimensionsConverter.pxFromDp(getContext(), 32);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (beerWidth + padding) * numStars - padding;
        int height = fullBeerHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < numStars; i++) {
            int x = i * (beerWidth + padding);
            int yOffset = fullBeerHeight - beerHeight;

            emptyBeer.setBounds(x, yOffset, x + beerWidth, fullBeerHeight);
            emptyBeer.draw(canvas);

            if (rating > i + 0.75) {
                fullBeer.setBounds(x, 0, x + beerWidth, fullBeerHeight);
                fullBeer.draw(canvas);
            } else if (rating > i + 0.25) {
                halfBeer.setBounds(x, yOffset, x + beerWidth, fullBeerHeight);
                halfBeer.draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float touchX = event.getX();
            float newRating = ((touchX / (beerWidth + padding)) + 1);
            rating = Math.round(newRating * 2) / 2.0f;
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        invalidate();
    }
}
