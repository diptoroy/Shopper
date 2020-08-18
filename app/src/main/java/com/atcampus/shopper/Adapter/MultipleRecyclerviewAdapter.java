package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Activity.ViewAllActivity;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultipleRecyclerviewAdapter extends RecyclerView.Adapter {

    private List<MultipleRecyclerviewModel> multipleRecyclerviewModels;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public MultipleRecyclerviewAdapter(List<MultipleRecyclerviewModel> multipleRecyclerviewModels) {
        this.multipleRecyclerviewModels = multipleRecyclerviewModels;
        recycledViewPool = new RecyclerView.RecycledViewPool();
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
                String resource = multipleRecyclerviewModels.get(position).getResource();
                String backgroundColor = multipleRecyclerviewModels.get(position).getBackgroundColor();
                ((SliderAdsViewHolder)holder).setSliderAds(resource,backgroundColor);
                break;
            case MultipleRecyclerviewModel.DEALS_DAY:
                String dealsday_title = multipleRecyclerviewModels.get(position).getTitle();
                List<DealsModel> dealsModels = multipleRecyclerviewModels.get(position).getDealsModelList();
                List<WishlistModel> wishlistModels = multipleRecyclerviewModels.get(position).getAllDeals();
                String dColor = multipleRecyclerviewModels.get(position).getBackgroundColor();
                ((DealsDayViewHolder) holder).setDealsDayLayout(dealsModels,dealsday_title,dColor,wishlistModels);
                break;
            case MultipleRecyclerviewModel.TRENDING:
                String trending_title = multipleRecyclerviewModels.get(position).getTitle();
                List<DealsModel> trendingModels = multipleRecyclerviewModels.get(position).getDealsModelList();
                String tColor = multipleRecyclerviewModels.get(position).getBackgroundColor();
                ((TrendingViewHolder) holder).setTrendingAdapter(trendingModels,trending_title,tColor);
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
        private int currentSlider;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            sliderPager = itemView.findViewById(R.id.slider_viewpager);

        }

        private void setSliderViewpager(final List<SliderModel> sliderModelList){
            currentSlider = 2;
            if (timer != null){
                timer.cancel();
            }

            arrangedList = new ArrayList<>();
            for (int x = 0;x < sliderModelList.size();x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
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
                        SliderLopper(arrangedList);
                    }

                }
            };
            sliderPager.addOnPageChangeListener(onPageChangeListener);

            startSlider(arrangedList);
            sliderPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SliderLopper(arrangedList);
                    stopSlider();
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        startSlider(arrangedList);
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

        private void setSliderAds(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner)).into(slider_ads);
            slider_ads_layout.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class DealsDayViewHolder extends RecyclerView.ViewHolder{

        private TextView dealsText;
        private Button dealsBtn;
        private RecyclerView dealsRecyclerview;
        private ConstraintLayout container;

        public DealsDayViewHolder(@NonNull View itemView) {
            super(itemView);

            dealsText = itemView.findViewById(R.id.deals_text);
            dealsBtn = itemView.findViewById(R.id.deals_button);
            dealsRecyclerview = itemView.findViewById(R.id.deals_recyclerview);
            container = itemView.findViewById(R.id.deals_container);
            dealsRecyclerview.setRecycledViewPool(recycledViewPool);
        }
        private void setDealsDayLayout(List<DealsModel> dealsModels, final String title, String color, final List<WishlistModel> allDeals){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            dealsText.setText(title);
            if (dealsModels.size() > 8){
                dealsBtn.setVisibility(View.VISIBLE);
                dealsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistModelsList = allDeals;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("viewCode",0);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
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
        private GridLayout trending_grid_layout;
        private ConstraintLayout trendingContainer;
        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);

            trendingText = itemView.findViewById(R.id.trending_text);
            trendingBtn = itemView.findViewById(R.id.trending_button);
            trending_grid_layout = itemView.findViewById(R.id.trending_grid_layout);
            trendingContainer = itemView.findViewById(R.id.trending_container);
        }

        private void setTrendingAdapter(final List<DealsModel> dealsModels, final String title, String tColor){
            trendingText.setText(title);
            trendingContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(tColor)));
            for (int x = 1;x < 4;x++){
                ImageView image = trending_grid_layout.getChildAt(x).findViewById(R.id.dealsitem_image);
                TextView name = trending_grid_layout.getChildAt(x).findViewById(R.id.dealsitem_name);
                TextView spec = trending_grid_layout.getChildAt(x).findViewById(R.id.dealsitem_spec);
                TextView price = trending_grid_layout.getChildAt(x).findViewById(R.id.dealsitem_price);

//                image.setImageResource(dealsModels.get(x).getDealsImage());
                Glide.with(itemView.getContext()).load(dealsModels.get(x).getDealsImage()).apply(new RequestOptions().placeholder(R.drawable.phone)).into(image);
                name.setText(dealsModels.get(x).getDealsName());
                spec.setText(dealsModels.get(x).getDealsSpec());
                price.setText("$"+dealsModels.get(x).getDealsPrice()+"");

                trending_grid_layout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                trending_grid_layout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productIntent);
                    }
                });
            }
            trendingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.dealsModels = dealsModels;
                    Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                    viewAllIntent.putExtra("viewCode",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }
}
