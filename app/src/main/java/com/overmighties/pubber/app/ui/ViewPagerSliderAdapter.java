package com.overmighties.pubber.app.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ortiz.touchview.TouchImageView;

import java.util.List;

public class ViewPagerSliderAdapter extends PagerAdapter {
    private final List<Integer> images;

    public ViewPagerSliderAdapter(List<Integer> sliderItems)
    {
        this.images=sliderItems;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        TouchImageView img = new TouchImageView(container.getContext());
        img.setImageResource(images.get(position));
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
