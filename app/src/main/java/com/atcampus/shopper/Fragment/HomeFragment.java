package com.atcampus.shopper.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.atcampus.shopper.Query.AllDBQuery.allList;
import static com.atcampus.shopper.Query.AllDBQuery.categoryModels;
import static com.atcampus.shopper.Query.AllDBQuery.categoryName;
import static com.atcampus.shopper.Query.AllDBQuery.loadCategories;
import static com.atcampus.shopper.Query.AllDBQuery.loadView;

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
    private List<CategoryModel> fakeCategoryModels  = new ArrayList<>();


    //multiple recyclerview
    private RecyclerView multipleRecyclerview;
    private MultipleRecyclerviewAdapter multipleRecyclerviewAdapter;
    private List<MultipleRecyclerviewModel> fakeMultipleRecyclerviewModels = new ArrayList<>();

    public static SwipeRefreshLayout swipeRefreshLayout;
    ImageView noConnection;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));
        noConnection = view.findViewById(R.id.noConnection);

        //Category
        categoryRecyclerView = view.findViewById(R.id.category_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);


        fakeCategoryModels.add(new CategoryModel("null",""));
        fakeCategoryModels.add(new CategoryModel("",""));
        fakeCategoryModels.add(new CategoryModel("",""));
        fakeCategoryModels.add(new CategoryModel("",""));
        fakeCategoryModels.add(new CategoryModel("",""));
        fakeCategoryModels.add(new CategoryModel("",""));
        fakeCategoryModels.add(new CategoryModel("",""));

        categoryAdapter = new CategoryAdapter(fakeCategoryModels);


        //Multiple RecyclerView
        multipleRecyclerview = view.findViewById(R.id.multiple_recyclerview);
        LinearLayoutManager multipleManager = new LinearLayoutManager(getContext());
        multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
        multipleRecyclerview.setLayoutManager(multipleManager);

        List<SliderModel> fakeSliderModel = new ArrayList<>();
        fakeSliderModel.add(new SliderModel("null","#dfdfdf"));
        fakeSliderModel.add(new SliderModel("null","#dfdfdf"));
        fakeSliderModel.add(new SliderModel("null","#dfdfdf"));

        List<DealsModel> fakeDealsModel = new ArrayList<>();
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));

        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(0,fakeSliderModel));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(1,"","#dfdfdf"));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(2,"","#dfdfdf",fakeDealsModel,new ArrayList<WishlistModel>()));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(3,"","#dfdfdf",fakeDealsModel));

        multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(fakeMultipleRecyclerviewModels);


        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            noConnection.setVisibility(View.GONE);
            ///Category
            if (categoryModels.size() == 0){
                loadCategories(categoryRecyclerView,getContext());
            }else {
                categoryAdapter = new CategoryAdapter(categoryModels);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);
            if (allList.size() == 0){
                categoryName.add("Home");
                allList.add(new ArrayList<MultipleRecyclerviewModel>());
                loadView(multipleRecyclerview,getContext(),0,"Home");
            }else {
                multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(allList.get(0));
                categoryAdapter.notifyDataSetChanged();
            }
            multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);
        } else {
            Glide.with(this).load(R.drawable.symbol).into(noConnection);
            noConnection.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);

                categoryModels.clear();
                allList.clear();
                categoryName.clear();

                if (networkInfo != null && networkInfo.isConnected() == true) {
                    noConnection.setVisibility(View.GONE);
                    loadCategories(categoryRecyclerView,getContext());
                    categoryAdapter = new CategoryAdapter(fakeCategoryModels);
                    multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(fakeMultipleRecyclerviewModels);
                    categoryRecyclerView.setAdapter(categoryAdapter);
                    multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);

                    categoryName.add("Home");
                    allList.add(new ArrayList<MultipleRecyclerviewModel>());
                    loadView(multipleRecyclerview,getContext(),0,"Home");
                }else {
                    Glide.with(getContext()).load(R.drawable.symbol).into(noConnection);
                    noConnection.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

}
