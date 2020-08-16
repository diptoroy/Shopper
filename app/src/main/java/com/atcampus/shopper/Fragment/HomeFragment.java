package com.atcampus.shopper.Fragment;

import android.graphics.Color;
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
    private List<CategoryModel> categoryModels;

    //multiple recyclerview
    private RecyclerView multipleRecyclerview;
    private MultipleRecyclerviewAdapter multipleRecyclerviewAdapter;

    //Firebase Firestore
    private FirebaseFirestore firebaseFirestore;


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

        categoryModels = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModels);
        categoryRecyclerView.setAdapter(categoryAdapter);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModels.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });



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
        multipleRecyclerview = view.findViewById(R.id.multiple_recyclerview);
        LinearLayoutManager multipleManager = new LinearLayoutManager(getContext());
        multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
        multipleRecyclerview.setLayoutManager(multipleManager);

        final List<MultipleRecyclerviewModel> multipleRecyclerviewModels = new ArrayList<>();
        multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(multipleRecyclerviewModels);
        multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);

        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
                .collection("BANNERSLIDER")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                if ((long)documentSnapshot.get("view_type") == 0){
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_slider = (long) documentSnapshot.get("no_of_slider");
                                    for (long i = 1 ;i < no_of_slider + 1;i++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("slider_"+i).toString()
                                                ,documentSnapshot.get("slider_"+i+"_color").toString()));
                                    }
                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(0,sliderModelList));
                                }else if ((long)documentSnapshot.get("view_type") == 1){
//                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(1,documentSnapshot.get("ads_1").toString()
//                                            ,documentSnapshot.get("ads_1_color").toString()));
                                }else if ((long)documentSnapshot.get("view_type") == 2){

                                }else if ((long)documentSnapshot.get("view_type") == 3){

                                }
                            }
                            multipleRecyclerviewAdapter.notifyDataSetChanged();
                        }else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        return view;
    }

}
