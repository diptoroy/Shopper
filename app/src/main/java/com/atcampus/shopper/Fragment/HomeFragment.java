package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.SliderAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    private ViewPager sliderPager;
    private List<SliderModel> sliderModelList;
    private SliderAdapter sliderAdapter;
    private int currentSlider = 2;

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

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round));

        sliderAdapter = new SliderAdapter(sliderModelList);
        sliderPager.setClipToPadding(false);
        sliderPager.setPageMargin(20);


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
}
