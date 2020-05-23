package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<SliderModel> sliderModels;

    public SliderAdapter(List<SliderModel> sliderModels) {
        this.sliderModels = sliderModels;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider,container,false);
        ImageView slider_img = view.findViewById(R.id.slider_image);
        slider_img.setImageResource(sliderModels.get(position).getSlider());
        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModels.size();
    }

}
