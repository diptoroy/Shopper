package com.atcampus.shopper.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.SliderAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    //category
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    //slider
    private ViewPager sliderPager;
    private List<SliderModel> sliderModelList;
    private SliderAdapter sliderAdapter;
    private int currentSlider = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    //slider ads
    private ImageView slider_ads;
    private ConstraintLayout slider_ads_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ///Category
        categoryRecyclerView = view.findViewById(R.id.category_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        categoryModels.add(new CategoryModel("image","All"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Electronics"));
        categoryModels.add(new CategoryModel("image","Entertainment"));
        categoryModels.add(new CategoryModel("image","Books"));
        categoryModels.add(new CategoryModel("image","Sports"));
        categoryModels.add(new CategoryModel("image","Cosmetics"));
        categoryAdapter = new CategoryAdapter(categoryModels);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //Slider
        sliderPager = view.findViewById(R.id.slider_viewpager);
        sliderModelList = new ArrayList<>();

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));

        sliderAdapter = new SliderAdapter(sliderModelList);
        sliderPager.setAdapter(sliderAdapter);
        sliderPager.setClipToPadding(false);
        sliderPager.setPageMargin(20);
        sliderPager.setCurrentItem(currentSlider);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentSlider = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                SliderLopper();
            }
        };
        sliderPager.addOnPageChangeListener(onPageChangeListener);

        startSlider();
        sliderPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SliderLopper();
                stopSlider();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    stopSlider();
                }
                return false;
            }
        });

        //slider ads
        slider_ads = view.findViewById(R.id.slider_ads);
        slider_ads_layout = view.findViewById(R.id.slider_ads_layout);

        slider_ads.setImageResource(R.drawable.banner);
        slider_ads_layout.setBackgroundColor(Color.parseColor("#FB9E8A"));
        return view;
    }

    private void SliderLopper(){
        if (currentSlider == sliderModelList.size() - 2){
            currentSlider = 2;
            sliderPager.setCurrentItem(currentSlider,false);
        }
        if (currentSlider == 1){
            currentSlider = sliderModelList.size() - 3;
            sliderPager.setCurrentItem(currentSlider,false);
        }
    }

    private void startSlider(){
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
