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
import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Adapter.SliderAdapter;
import com.atcampus.shopper.Adapter.TrendingAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
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
        List<SliderModel>sliderModelList = new ArrayList<>();

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));

        //deals
        List<DealsModel>dealsModels = new ArrayList<>();

        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));

        //multiple recyclerview
        multipleRecyclerview = view.findViewById(R.id.multiple_recyclerview);
        LinearLayoutManager multipleManager = new LinearLayoutManager(getContext());
        multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
        multipleRecyclerview.setLayoutManager(multipleManager);

        List<MultipleRecyclerviewModel> multipleRecyclerviewModels = new ArrayList<>();
        multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(0,sliderModelList));
        multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(1,R.drawable.banner,"#000000"));
        multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(2,"Deals",dealsModels));
        multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(3,"Trending",dealsModels));


        MultipleRecyclerviewAdapter multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(multipleRecyclerviewModels);
        multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);
        multipleRecyclerviewAdapter.notifyDataSetChanged();

        return view;
    }

}
