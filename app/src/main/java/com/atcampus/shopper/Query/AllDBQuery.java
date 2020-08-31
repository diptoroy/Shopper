package com.atcampus.shopper.Query;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Fragment.HomeFragment;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllDBQuery {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModels = new ArrayList<>();
//    public static List<MultipleRecyclerviewModel> multipleRecyclerviewModels = new ArrayList<>();
    public static List<List<MultipleRecyclerviewModel>> allList = new ArrayList<>();
    public static List<String> categoryName = new ArrayList<>();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModels.add(new CategoryModel((String)documentSnapshot.get("icon"), (String)documentSnapshot.get("categoryName")));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModels);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadView(final RecyclerView multipleRecyclerview, final Context context, final int index, String category) {
        firebaseFirestore.collection("CATEGORIES")
                .document(category.toUpperCase())
                .collection("BANNERSLIDER")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_slider = (long) documentSnapshot.get("no_of_slider");
                                    for (long i = 1; i < no_of_slider + 1; i++) {
                                        sliderModelList.add(new SliderModel((String)documentSnapshot.get("slider_" + i)
                                                , (String)documentSnapshot.get("slider_" + i + "_color")));
                                    }
                                    allList.get(index).add(new MultipleRecyclerviewModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
//                                    allList.get(index).add(new MultipleRecyclerviewModel(1,documentSnapshot.get("ads_1").toString()
//                                            ,documentSnapshot.get("ads_1_color").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    List<WishlistModel> allDeals = new ArrayList<>();
                                    List<DealsModel> dealsModelList = new ArrayList<>();
                                    long number_of_product = (long) documentSnapshot.get("number_of_product");
                                    for (long i = 1; i < number_of_product + 1; i++) {
                                        dealsModelList.add(new DealsModel((String)documentSnapshot.get("product_id_" + i)
                                                , (String)documentSnapshot.get("product_image_" + i)
                                                , (String)documentSnapshot.get("product_title_" + i)
                                                , (String)documentSnapshot.get("product_subtitle_" + i)
                                                ,(String)documentSnapshot.get("product_price_" + i)));

                                        allDeals.add(new WishlistModel((String)documentSnapshot.get("product_image_"+i)
                                        ,(String)documentSnapshot.get("product_full_title_" + i)
                                        ,(long)documentSnapshot.get("free_coupen_" + i)
                                        ,(String)documentSnapshot.get("average_rating_" + i)
                                        ,(String) documentSnapshot.get("total_rating_" + i)
                                        ,(String)documentSnapshot.get("product_price_" + i)
                                        ,(String)documentSnapshot.get("cutted_price_" + i)
                                        ,(boolean)documentSnapshot.get("cod_" + i)));
                                    }
                                    allList.get(index).add(new MultipleRecyclerviewModel(2, (String)documentSnapshot.get("layout_title"), (String)documentSnapshot.get("layout_color"), dealsModelList,allDeals));
                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<DealsModel> trendingModelList = new ArrayList<>();
                                    long number_of_product = (long) documentSnapshot.get("number_of_product");
                                    for (long i = 1; i < number_of_product + 1; i++) {
                                        trendingModelList.add(new DealsModel((String)documentSnapshot.get("product_id_" + i)
                                                , (String)documentSnapshot.get("product_image_" + i)
                                                , (String)documentSnapshot.get("product_title_" + i)
                                                , (String)documentSnapshot.get("product_subtitle_" + i)
                                                , (String)documentSnapshot.get("product_price_" + i)));
                                    }
                                    allList.get(index).add(new MultipleRecyclerviewModel(3, (String)documentSnapshot.get("layout_title"), (String)documentSnapshot.get("layout_color"), trendingModelList));
                                }
                            }
                            MultipleRecyclerviewAdapter multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(allList.get(index));
                            multipleRecyclerview.setAdapter(multipleRecyclerviewAdapter);
                            multipleRecyclerviewAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
