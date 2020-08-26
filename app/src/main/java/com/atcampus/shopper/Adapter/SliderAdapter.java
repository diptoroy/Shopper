package com.atcampus.shopper.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        ConstraintLayout bannerLayout = view.findViewById(R.id.banner_layout);
        bannerLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModels.get(position).getBackgroundColor())));
        ImageView slider_img = view.findViewById(R.id.slider_image);
        Glide.with(container.getContext()).load(sliderModels.get(position).getSlider()).apply(new RequestOptions().placeholder(R.drawable.photo)).into(slider_img);
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
