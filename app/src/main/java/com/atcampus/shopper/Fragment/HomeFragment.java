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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.DealsAdapter;
import com.atcampus.shopper.Adapter.SliderAdapter;
import com.atcampus.shopper.Adapter.TrendingAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
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

    //deals
    private TextView dealsText;
    private Button dealsBtn;
    private RecyclerView dealsRecyclerview;
    private List<DealsModel> dealsModels;
    private DealsAdapter dealsAdapter;

    //trending
    private TextView trendingText;
    private Button trendingBtn;
    private GridView trendingGridView;

    //multiple recyclerview
    private RecyclerView multipleRecyclerview;


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
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
        categoryModels.add(new CategoryModel("image","Dress"));
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

        //deals
        dealsText = view.findViewById(R.id.deals_text);
        dealsBtn = view.findViewById(R.id.deals_button);
        dealsRecyclerview = view.findViewById(R.id.deals_recyclerview);
        dealsModels = new ArrayList<>();

        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));

        dealsAdapter = new DealsAdapter(dealsModels);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        dealsRecyclerview.setLayoutManager(layoutManager1);

        dealsRecyclerview.setAdapter(dealsAdapter);
        dealsAdapter.notifyDataSetChanged();

        //trending
        trendingText = view.findViewById(R.id.trending_text);
        trendingBtn = view.findViewById(R.id.trending_button);
        trendingGridView = view.findViewById(R.id.trending_recyclerview);

        trendingGridView.setAdapter(new TrendingAdapter(dealsModels));

        //multiple recyclerview
        multipleRecyclerview = view.findViewById(R.id.multiple_recyclerview);
        LinearLayoutManager multipleManager = new LinearLayoutManager(getContext());
        multipleManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        multipleRecyclerview.setLayoutManager(multipleManager);

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
