package com.atcampus.shopper.Adapter;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultipleRecyclerviewAdapter extends RecyclerView.Adapter {

    private List<MultipleRecyclerviewModel> multipleRecyclerviewModels;

    public MultipleRecyclerviewAdapter(List<MultipleRecyclerviewModel> multipleRecyclerviewModels) {
        this.multipleRecyclerviewModels = multipleRecyclerviewModels;
    }

    @Override
    public int getItemViewType(int position) {
        switch (multipleRecyclerviewModels.get(position).getType()) {
            case 0:
                return MultipleRecyclerviewModel.SLIDER;
            case 1:
                return MultipleRecyclerviewModel.SLIDER_ADS;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MultipleRecyclerviewModel.SLIDER:
                View sliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
                return new SliderViewHolder(sliderView);
            case MultipleRecyclerviewModel.SLIDER_ADS:
                View sliderAds = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_ads, parent, false);
                return new SliderAdsViewHolder(sliderAds);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (multipleRecyclerviewModels.get(position).getType()) {
            case MultipleRecyclerviewModel.SLIDER:
                List<SliderModel> sliderModelList = multipleRecyclerviewModels.get(position).getSliderModelList();
                ((SliderViewHolder) holder).setSliderViewpager(sliderModelList);
                break;
            case MultipleRecyclerviewModel.SLIDER_ADS:
                int resource = multipleRecyclerviewModels.get(position).getResource();
                String backgroundColor = multipleRecyclerviewModels.get(position).getBackgroundColor();
                ((SliderAdsViewHolder)holder).setSliderAds(resource,backgroundColor);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return multipleRecyclerviewModels.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager sliderPager;
        private int currentSlider = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            sliderPager = itemView.findViewById(R.id.slider_viewpager);

        }

        private void setSliderViewpager(final List<SliderModel> sliderModelList){
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            sliderPager.setAdapter(sliderAdapter);
            sliderPager.setClipToPadding(false);
            sliderPager.setPageMargin(20);
            sliderPager.setCurrentItem(currentSlider);

            final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentSlider = position;
                }

                @Override
                public void onPageScrollStateChanged(int position) {
                    if (position == ViewPager.SCROLL_STATE_IDLE){
                        SliderLopper(sliderModelList);
                    }

                }
            };
            sliderPager.addOnPageChangeListener(onPageChangeListener);

            startSlider(sliderModelList);
            sliderPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SliderLopper(sliderModelList);
                    stopSlider();
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        startSlider(sliderModelList);
                    }
                    return false;
                }
            });
        }
        private void SliderLopper(List<SliderModel> sliderModelList){
            if (currentSlider == sliderModelList.size() - 2){
                currentSlider = 2;
                sliderPager.setCurrentItem(currentSlider,false);
            }
            if (currentSlider == 1){
                currentSlider = sliderModelList.size() - 3;
                sliderPager.setCurrentItem(currentSlider,false);
            }
        }

        private void startSlider(final List<SliderModel> sliderModelList){
            final Handler handler = new Handler();
            final Runnable uptodate = new Runnable() {
                @Override
                public void run() {
                    if (currentSlider >= sliderModelList.size()){
                        currentSlider = 1;
                    }
                    sliderPager.setCurrentItem(currentSlider++,true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(uptodate);
                }
            },DELAY_TIME,PERIOD_TIME);
        }

        private void stopSlider(){
            timer.cancel();
        }

    }



    public class SliderAdsViewHolder extends RecyclerView.ViewHolder {
        private ImageView slider_ads;
        private ConstraintLayout slider_ads_layout;

        public SliderAdsViewHolder(@NonNull View itemView) {
            super(itemView);
            slider_ads = itemView.findViewById(R.id.slider_ads);
            slider_ads_layout = itemView.findViewById(R.id.slider_ads_layout);
        }

        private void setSliderAds(int resource, String color) {
            slider_ads.setImageResource(resource);
            slider_ads_layout.setBackgroundColor(Color.parseColor(color));
        }
    }
}
