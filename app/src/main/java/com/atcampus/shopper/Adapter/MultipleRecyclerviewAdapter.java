package com.atcampus.shopper.Adapter;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.atcampus.shopper.Model.DealsModel;
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
            case 2:
                return MultipleRecyclerviewModel.DEALS_DAY;
            case 3:
                return MultipleRecyclerviewModel.TRENDING;
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
            case MultipleRecyclerviewModel.DEALS_DAY:
                View dealsDay = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealsday_layout, parent, false);
                return new DealsDayViewHolder(dealsDay);
            case MultipleRecyclerviewModel.TRENDING:
                View trending = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_layout, parent, false);
                return new TrendingViewHolder(trending);
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
            case MultipleRecyclerviewModel.DEALS_DAY:
                String dealsday_title = multipleRecyclerviewModels.get(position).getTitle();
                List<DealsModel> dealsModels = multipleRecyclerviewModels.get(position).getDealsModelList();
                ((DealsDayViewHolder) holder).setDealsDayLayout(dealsModels,dealsday_title);
                break;
            case MultipleRecyclerviewModel.TRENDING:
                String trending_title = multipleRecyclerviewModels.get(position).getTitle();
                List<DealsModel> trendingModels = multipleRecyclerviewModels.get(position).getDealsModelList();
                ((TrendingViewHolder) holder).setTrendingAdapter(trendingModels,trending_title);
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

    public class DealsDayViewHolder extends RecyclerView.ViewHolder{

        private TextView dealsText;
        private Button dealsBtn;
        private RecyclerView dealsRecyclerview;

        public DealsDayViewHolder(@NonNull View itemView) {
            super(itemView);

            dealsText = itemView.findViewById(R.id.deals_text);
            dealsBtn = itemView.findViewById(R.id.deals_button);
            dealsRecyclerview = itemView.findViewById(R.id.deals_recyclerview);
        }
        private void setDealsDayLayout(List<DealsModel> dealsModels,String title){
            dealsText.setText(title);
            if (dealsModels.size() > 8){
                dealsBtn.setVisibility(View.VISIBLE);
            }else {
                dealsBtn.setVisibility(View.INVISIBLE);
            }
            DealsAdapter dealsAdapter = new DealsAdapter(dealsModels);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(itemView.getContext());
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            dealsRecyclerview.setLayoutManager(layoutManager1);

            dealsRecyclerview.setAdapter(dealsAdapter);
            dealsAdapter.notifyDataSetChanged();
        }
    }

    private class TrendingViewHolder extends RecyclerView.ViewHolder{
        private TextView trendingText;
        private Button trendingBtn;
        private GridView trendingGridView;
        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);

            trendingText = itemView.findViewById(R.id.trending_text);
            trendingBtn = itemView.findViewById(R.id.trending_button);
            trendingGridView = itemView.findViewById(R.id.trending_recyclerview);
        }

        private void setTrendingAdapter(List<DealsModel> dealsModels,String title){
            trendingText.setText(title);
            trendingGridView.setAdapter(new TrendingAdapter(dealsModels));
        }
    }
}
