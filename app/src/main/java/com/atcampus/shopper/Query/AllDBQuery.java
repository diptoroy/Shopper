package com.atcampus.shopper.Query;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Adapter.CategoryAdapter;
import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Fragment.CartFragment;
import com.atcampus.shopper.Fragment.HomeFragment;
import com.atcampus.shopper.Fragment.WishlistFragment;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atcampus.shopper.Activity.ProductDetailsActivity.productID;
import static com.atcampus.shopper.Activity.ProductDetailsActivity.running_query_list;

public class AllDBQuery {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static List<CategoryModel> categoryModels = new ArrayList<>();
    public static List<List<MultipleRecyclerviewModel>> allList = new ArrayList<>();
    public static List<String> categoryName = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistModel> wishlistModels = new ArrayList<>();

    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModels = new ArrayList<>();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {
        categoryModels.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModels.add(new CategoryModel((String) documentSnapshot.get("icon"), (String) documentSnapshot.get("categoryName")));
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
                                        sliderModelList.add(new SliderModel((String) documentSnapshot.get("slider_" + i)
                                                , (String) documentSnapshot.get("slider_" + i + "_color")));
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
                                        dealsModelList.add(new DealsModel((String) documentSnapshot.get("product_id_" + i)
                                                , (String) documentSnapshot.get("product_image_" + i)
                                                , (String) documentSnapshot.get("product_title_" + i)
                                                , (String) documentSnapshot.get("product_subtitle_" + i)
                                                , (String) documentSnapshot.get("product_price_" + i)));

                                            allDeals.add(new WishlistModel((String) documentSnapshot.get("product_id_" + i)
                                                    , (String) documentSnapshot.get("product_image_" + i)
                                                    , (String) documentSnapshot.get("product_full_title_" + i)
                                                    , (long) documentSnapshot.get("free_coupen_" + i)
                                                    , (String) documentSnapshot.get("average_rating_" + i)
                                                    , (long) documentSnapshot.get("total_rating_" + i)
                                                    , (String) documentSnapshot.get("product_price_" + i)
                                                    , (String) documentSnapshot.get("cutted_price_" + i)
                                                    , (boolean) documentSnapshot.get("cod_" + i)));


                                    }
                                    allList.get(index).add(new MultipleRecyclerviewModel(2, (String) documentSnapshot.get("layout_title"), (String) documentSnapshot.get("layout_color"), dealsModelList, allDeals));
                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<DealsModel> trendingModelList = new ArrayList<>();
                                    long number_of_product = (long) documentSnapshot.get("number_of_product");
                                    for (long i = 1; i < number_of_product + 1; i++) {
                                        trendingModelList.add(new DealsModel((String) documentSnapshot.get("product_id_" + i)
                                                , (String) documentSnapshot.get("product_image_" + i)
                                                , (String) documentSnapshot.get("product_title_" + i)
                                                , (String) documentSnapshot.get("product_subtitle_" + i)
                                                , (String) documentSnapshot.get("product_price_" + i)));
                                    }
                                    allList.get(index).add(new MultipleRecyclerviewModel(3, (String) documentSnapshot.get("layout_title"), (String) documentSnapshot.get("layout_color"), trendingModelList));
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

    public static void loadWishlist(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add((String) task.getResult().get("product_id_" + x));

                        if (AllDBQuery.wishList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.CHECK_FAVORITE_BTN = true;
                            if (ProductDetailsActivity.favoriteBtn != null) {
                                ProductDetailsActivity.favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                            }
                        } else {
                            if (ProductDetailsActivity.favoriteBtn != null) {
                                ProductDetailsActivity.favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                            }
                            ProductDetailsActivity.CHECK_FAVORITE_BTN = false;
                        }

                        if (loadProductData) {
                            wishlistModels.clear();
                            final String pId = (String) task.getResult().get("product_id_" + x);
                            firebaseFirestore.collection("PRODUCTS").document(pId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishlistModels.add(new WishlistModel(pId
                                                ,(String) task.getResult().get("product_image_1")
                                                , (String) task.getResult().get("product_title")
                                                , (long) task.getResult().get("free_cuepon")
                                                , (String) task.getResult().get("average_rating")
                                                , (long) task.getResult().get("total_rating")
                                                , (String) task.getResult().get("product_price")
                                                , (String) task.getResult().get("cutted_price")
                                                , (boolean) task.getResult().get("cod")));

                                        WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeWishlist(final int index, final Context context) {
        final String removeProductId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updatewishList = new HashMap<>();

        for (int x = 0; x < wishList.size(); x++) {
            updatewishList.put("product_id_" + x, wishList.get(x));
        }
        updatewishList.put("list_size", (long) wishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updatewishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModels.size() != 0) {
                        wishlistModels.remove(index);
                        WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.CHECK_FAVORITE_BTN = false;
                    Toast.makeText(context, "Remove Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (ProductDetailsActivity.favoriteBtn != null) {
                        ProductDetailsActivity.favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                    }
                    wishList.add(index,removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_query_list = false;
            }
        });
    }

    public static void loadRating(final Context context){
        if (!ProductDetailsActivity.running_rating_list) {
            ProductDetailsActivity.running_rating_list = true;

            myRatedIds.clear();
            myRating.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATING")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {


                            myRatedIds.add(task.getResult().get("product_id_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));
                            if (task.getResult().get("product_id_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.initialRating = (Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x)))) - 1;

                                if (ProductDetailsActivity.rateNowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }
                            }
                        }

                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailsActivity.running_rating_list = false;
                }
            });
        }
    }

    public static void loadCart(final Context context, final Dialog dialog,final boolean loadProductData){
        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartList.add((String) task.getResult().get("product_id_" + x));

                        if (AllDBQuery.cartList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.CHECK_CART_BTN = true;
                        } else {
                            ProductDetailsActivity.CHECK_CART_BTN = false;
                        }

                        if (loadProductData) {
                            cartItemModels.clear();
                            final String pId = (String) task.getResult().get("product_id_" + x);
                            firebaseFirestore.collection("PRODUCTS").document(pId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int index = 0;
                                        if (cartList.size() >= 2){
                                            index = cartList.size() - 2;
                                        }
                                        cartItemModels.add(index,new CartItemModel(CartItemModel.CART_ITEM,pId
                                                ,(String) task.getResult().get("product_image_1")
                                                , (String) task.getResult().get("product_title")
                                                , (long) task.getResult().get("free_cuepon")
                                                , (String) task.getResult().get("product_price")
                                                , (String) task.getResult().get("cutted_price")
                                                , (long) 1
                                                , (long) 0
                                                , (long) 0));

                                        if (cartList.size() == 1){
                                            cartItemModels.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                        }
                                        if (cartList.size() == 0){
                                            cartItemModels.clear();
                                        }
                                        CartFragment.cartAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeCart(final int index, final Context context){
        final String removeProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updatecartList = new HashMap<>();

        for (int x = 0; x < cartList.size(); x++) {
            updatecartList.put("product_id_" + x, cartList.get(x));
        }
        updatecartList.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updatecartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModels.size() != 0) {
                        cartItemModels.remove(index);
                        CartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if (cartList.size() == 0){
                        cartItemModels.clear();
                    }
                    Toast.makeText(context, "Remove Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    cartList.add(index,removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_cart_list = false;
            }
        });
    }

    public static void clearData() {
        categoryModels.clear();
        categoryName.clear();
        allList.clear();
        wishList.clear();
        wishlistModels.clear();
        cartList.clear();
        cartItemModels.clear();
    }
}
