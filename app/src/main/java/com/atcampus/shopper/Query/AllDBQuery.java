package com.atcampus.shopper.Query;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllDBQuery {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModels = new ArrayList<>();
    public static List<MultipleRecyclerviewModel> multipleRecyclerviewModels = new ArrayList<>();

    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModels.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadView(final MultipleRecyclerviewAdapter multipleRecyclerviewAdapter, final Context context) {
        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
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
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("slider_" + i).toString()
                                                , documentSnapshot.get("slider_" + i + "_color").toString()));
                                    }
                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
//                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(1,documentSnapshot.get("ads_1").toString()
//                                            ,documentSnapshot.get("ads_1_color").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    List<WishlistModel> allDeals = new ArrayList<>();
                                    List<DealsModel> dealsModelList = new ArrayList<>();
                                    long number_of_product = (long) documentSnapshot.get("number_of_product");
                                    for (long i = 1; i < number_of_product + 1; i++) {
                                        dealsModelList.add(new DealsModel(documentSnapshot.get("product_id_" + i).toString()
                                                , documentSnapshot.get("product_image_" + i).toString()
                                                , documentSnapshot.get("product_title_" + i).toString()
                                                , documentSnapshot.get("product_subtitle_" + i).toString()
                                                , documentSnapshot.get("product_price_" + i).toString()));

                                        allDeals.add(new WishlistModel(documentSnapshot.get("product_image_"+i).toString()
                                        ,documentSnapshot.get("product_full_title_" + i).toString()
                                        ,(long)documentSnapshot.get("free_coupen_" + i)
                                        ,documentSnapshot.get("average_rating_" + i).toString()
                                        ,(String) documentSnapshot.get("total_rating_" + i)
                                        ,documentSnapshot.get("product_price_" + i).toString()
                                        ,documentSnapshot.get("cutted_price_" + i).toString()
                                        ,(boolean)documentSnapshot.get("cod_" + i)));
                                    }
                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_color").toString(), dealsModelList,allDeals));
                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<DealsModel> trendingModelList = new ArrayList<>();
                                    long number_of_product = (long) documentSnapshot.get("number_of_product");
                                    for (long i = 1; i < number_of_product + 1; i++) {
                                        trendingModelList.add(new DealsModel(documentSnapshot.get("product_id_" + i).toString()
                                                , documentSnapshot.get("product_image_" + i).toString()
                                                , documentSnapshot.get("product_title_" + i).toString()
                                                , documentSnapshot.get("product_subtitle_" + i).toString()
                                                , documentSnapshot.get("product_price_" + i).toString()));
                                    }
                                    multipleRecyclerviewModels.add(new MultipleRecyclerviewModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_color").toString(), trendingModelList));
                                }
                            }
                            multipleRecyclerviewAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
