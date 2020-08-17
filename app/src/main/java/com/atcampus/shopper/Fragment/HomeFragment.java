package com.atcampus.shopper.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.atcampus.shopper.Query.AllDBQuery.categoryModels;
import static com.atcampus.shopper.Query.AllDBQuery.firebaseFirestore;
import static com.atcampus.shopper.Query.AllDBQuery.loadCategories;
import static com.atcampus.shopper.Query.AllDBQuery.loadView;
import static com.atcampus.shopper.Query.AllDBQuery.multipleRecyclerviewModels;

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
    private MultipleRecyclerviewAdapter multipleRecyclerviewAdapter;

    ImageView noConnection;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noConnection = view.findViewById(R.id.noConnection);

//        //Slider
//        List<SliderModel>sliderModelList = new ArrayList<>();
//
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//
//        //deals
//        List<DealsModel>dealsModels = new ArrayList<>();
//
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));
//        dealsModels.add(new DealsModel(R.drawable.phone,"Iphone 11","6GB/128GB","TK 76000/-"));

        //multiple recyclerview

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            noConnection.setVisibility(View.GONE);
            ///Category
            categoryRecyclerView = view.findViewById(R.id.category_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(categoryModels);
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (categoryModels.size() == 0){
                loadCategories(categoryAdapter,getContext());
            }else {
                categoryAdapter.notifyDataSetChanged();
            }

            //all View
            multipleRecyclerview = view.findViewById(R.id.multiple_recyclerview);
            LinearLayoutManager multipleManager = new LinearLayoutManager(getContext());
            multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
            multipleRecyclerview.setLayoutManager(multipleManager);
            multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(multipleRecyclerviewModels);
            multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);

            if (multipleRecyclerviewModels.size() == 0){
                loadView(multipleRecyclerviewAdapter,getContext());
            }else {
                categoryAdapter.notifyDataSetChanged();
            }

        } else {
            Glide.with(this).load(R.drawable.symbol).into(noConnection);
            noConnection.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
