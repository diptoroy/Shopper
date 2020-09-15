package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atcampus.shopper.Adapter.ProductImagesViewpagerAdapter;
import com.atcampus.shopper.Adapter.ProductsDescriptionAdapter;
import com.atcampus.shopper.Adapter.RewardAdapter;
import com.atcampus.shopper.Fragment.BlankFragment;
import com.atcampus.shopper.Fragment.CartFragment;
import com.atcampus.shopper.Fragment.ProductSpeceficationFragment;
import com.atcampus.shopper.Fragment.ProductsDescriptionFragment;
import com.atcampus.shopper.Fragment.SigninFragment;
import com.atcampus.shopper.Fragment.SignupFragment;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.Model.ProductsSpeceficationModel;
import com.atcampus.shopper.Model.RewardModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atcampus.shopper.Activity.RegisterActivity.setSignUpFragment;
import static com.atcampus.shopper.Query.AllDBQuery.cartItemModels;

public class ProductDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Integer> productImages;
    public static FloatingActionButton favoriteBtn;
    public static boolean CHECK_FAVORITE_BTN = false;
    public static boolean CHECK_CART_BTN = false;
    private Button buyNowBtn, cueponBtn;
    private TextView productName, averageRatingMiniview, totalRatingMiniView, productsPrice, productsDiscountPrice, productCodText, rewardsTitle, rewardsBody;
    private TextView pTitleBody, pDetailBody;
    private ImageView productCod;
    private LinearLayout addToCartBtn;
    private LinearLayout productsCuponLayout;

    //product details
    private ViewPager viewPager, descViewpager;
    private TabLayout tabLayout, descTabLayout;
    private ConstraintLayout productDescriptionContainer, productTitleSpecContainer;
    private String pDesc, pOtherDesc;


    public static LinearLayout rateNowContainer;
    private TextView totalRatings, totalRatingsFigure, averageRating;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsPrgressBarContainer;

    public static int initialRating;

    //cuepon dialog
    public static TextView cueponTitle, cueponBody, cueponExpiry;
    public static RecyclerView cueponRecyclerView;
    public static LinearLayout selected_cuepon_container;

    private List<ProductsSpeceficationModel> productsSpeceficationModelList = new ArrayList<>();

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DocumentSnapshot documentSnapshot;

    public static String productID;
    public static boolean running_query_list = false;
    public static boolean running_rating_list = false;
    public static boolean running_cart_list = false;

    private Dialog userAlertDialog;
    private Dialog loadingDialog;

    public static MenuItem cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //dialog
        userAlertDialog = new Dialog(ProductDetailsActivity.this);
        userAlertDialog.setContentView(R.layout.user_sign_in_dialog);
        userAlertDialog.setCancelable(true);
        userAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signInBtn = userAlertDialog.findViewById(R.id.sign_in_btn);
        Button signUpBtn = userAlertDialog.findViewById(R.id.sign_up_btn);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninFragment.disableCloseBtn = true;
                SignupFragment.disableCloseBtn = true;
                userAlertDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninFragment.disableCloseBtn = true;
                SignupFragment.disableCloseBtn = true;
                userAlertDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        //loading dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //product image slider
        viewPager = findViewById(R.id.product_images_viewpager);
        tabLayout = findViewById(R.id.image_indicator);
        buyNowBtn = findViewById(R.id.buyNowBtn);
        cueponBtn = findViewById(R.id.cuepon_btn);

        initialRating = -1;
        productName = findViewById(R.id.products_name);

        averageRatingMiniview = findViewById(R.id.products_rating_text);    //averageRating
        totalRatingMiniView = findViewById(R.id.products_total_rating);  //productsTotalRating

        productsPrice = findViewById(R.id.products_price);
        productsDiscountPrice = findViewById(R.id.products_discount_price);
        productCod = findViewById(R.id.product_cod);
        productCodText = findViewById(R.id.cod_text);
        rewardsTitle = findViewById(R.id.reward_title);
        rewardsBody = findViewById(R.id.reward_details);
        productDescriptionContainer = findViewById(R.id.products_desc_container);
        productTitleSpecContainer = findViewById(R.id.product_title_spec_container);
        pTitleBody = findViewById(R.id.products_title_body);
        pDetailBody = findViewById(R.id.products_detail_body);
        addToCartBtn = findViewById(R.id.add_to_cart);
        productsCuponLayout = findViewById(R.id.products_cupon_layout);


        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsPrgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.average_rating);

        productID = getIntent().getStringExtra("PRODUCT_ID").toString().replace("/", "-");
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_image") + 1; x++) {
                        productImages.add((String) documentSnapshot.get("product_image_" + x));
                    }
                    ProductImagesViewpagerAdapter adapter = new ProductImagesViewpagerAdapter(productImages);
                    viewPager.setAdapter(adapter);
                    productName.setText((String) documentSnapshot.get("product_title"));

                    averageRatingMiniview.setText((String) documentSnapshot.get("average_rating"));
                    totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_rating") + ")ratings");

                    productsPrice.setText((String) "$" + documentSnapshot.get("product_price"));
                    productsDiscountPrice.setText((String) "$" + documentSnapshot.get("cutted_price"));
                    if ((boolean) documentSnapshot.get("cod")) {
                        productCod.setVisibility(View.VISIBLE);
                        productCodText.setVisibility(View.VISIBLE);
                    } else {
                        productCod.setVisibility(View.INVISIBLE);
                        productCodText.setVisibility(View.INVISIBLE);
                    }
                    rewardsTitle.setText((long) documentSnapshot.get("free_cuepon") + (String) documentSnapshot.get("free_cuepon_title"));
                    rewardsBody.setText((String) documentSnapshot.get("free_cuepon_body"));
                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDescriptionContainer.setVisibility(View.VISIBLE);
                        productTitleSpecContainer.setVisibility(View.GONE);
                        pDesc = (String) documentSnapshot.get("product_desc");
                        pOtherDesc = (String) documentSnapshot.get("product_other_desc");

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {
                            productsSpeceficationModelList.add(new ProductsSpeceficationModel((String) documentSnapshot.get("spec_title_" + x), 0));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_field") + 1; y++) {
                                productsSpeceficationModelList.add(new ProductsSpeceficationModel(1, (String) documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name"), (String) documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value")));
                            }
                        }
                    } else {
                        productDescriptionContainer.setVisibility(View.GONE);
                        productTitleSpecContainer.setVisibility(View.VISIBLE);
                        //pTitleBody.setText(documentSnapshot.get("product_title").toString());
                        pDetailBody.setText((String) documentSnapshot.get("product_desc"));
                    }
                    totalRatings.setText((long) documentSnapshot.get("total_rating") + " ratings"); //totalRating

                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);

                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsPrgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_rating")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));

                    }
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_rating")));   //totalRatingText
                    averageRating.setText((String) documentSnapshot.get("average_rating"));   //averageRatingText
                    descViewpager.setAdapter(new ProductsDescriptionAdapter(getSupportFragmentManager(), descTabLayout.getTabCount(), pDesc, pOtherDesc, productsSpeceficationModelList));

                    if (currentUser != null) {
                        if (AllDBQuery.myRating.size() == 0) {
                            AllDBQuery.loadRating(ProductDetailsActivity.this);
                        }

                        if (AllDBQuery.cartList.size() == 0) {
                            AllDBQuery.loadCart(ProductDetailsActivity.this, loadingDialog, false,new TextView(ProductDetailsActivity.this));
                        }

                        if (AllDBQuery.wishList.size() == 0) {
                            AllDBQuery.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);
                        } else {
                            loadingDialog.dismiss();
                        }

                    } else {
                        loadingDialog.dismiss();
                    }


                    if (AllDBQuery.myRatedIds.contains(productID)) {
                        int index = AllDBQuery.myRatedIds.indexOf(productID);
                        initialRating = Integer.parseInt(String.valueOf(AllDBQuery.myRating.get(index))) - 1;
                        setRating(initialRating);
                    }

                    if (AllDBQuery.cartList.contains(productID)) {
                        CHECK_CART_BTN = true;
                    } else {
                        CHECK_CART_BTN = false;
                    }

                    if (AllDBQuery.wishList.contains(productID)) {
                        CHECK_FAVORITE_BTN = true;
                        favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                    } else {
                        favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                        CHECK_FAVORITE_BTN = false;
                    }

                    if((boolean)documentSnapshot.get("stock")){
                        addToCartBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currentUser == null) {
                                    userAlertDialog.show();
                                } else {
                                    if (!running_cart_list) {
                                        running_cart_list = true;
                                        if (CHECK_CART_BTN) {
                                            running_cart_list = false;
                                            Toast.makeText(ProductDetailsActivity.this, "Already added to cart!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> addProduct = new HashMap<>();
                                            addProduct.put("product_id_" + String.valueOf(AllDBQuery.cartList.size()), productID);
                                            addProduct.put("list_size", (long) (AllDBQuery.cartList.size() + 1));

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (AllDBQuery.cartItemModels.size() != 0) {
                                                            AllDBQuery.cartItemModels.add(0,new CartItemModel(CartItemModel.CART_ITEM, productID
                                                                    , (String) documentSnapshot.get("product_image_1")
                                                                    , (String) documentSnapshot.get("product_title")
                                                                    , (long) documentSnapshot.get("free_cuepon")
                                                                    , (String) documentSnapshot.get("product_price")
                                                                    , (String) documentSnapshot.get("cutted_price")
                                                                    , (long) 1
                                                                    , (long) 0
                                                                    , (long) 0
                                                            ,(boolean) documentSnapshot.get("stock")));
                                                        }

                                                        CHECK_CART_BTN = true;
                                                        AllDBQuery.cartList.add(productID);
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                                                        invalidateOptionsMenu();
                                                        running_cart_list = false;
                                                    } else {
//                                        favoriteBtn.setEnabled(true);
                                                        running_cart_list = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }
                                }
                            }
                        });
                    }else {
                        buyNowBtn.setVisibility(View.GONE);
                        TextView stockText = (TextView) addToCartBtn.getChildAt(0);
                        stockText.setText("Out of stock");
                        stockText.setTextColor(getResources().getColor(R.color.colorPrimary));
                        stockText.setCompoundDrawables(null,null,null,null);
                    }
                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager, true);

        //products Description
        descViewpager = findViewById(R.id.products_description_viewpager);
        descTabLayout = findViewById(R.id.products_description_tabs);


        descViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(descTabLayout));
        descTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                descViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        favoriteBtn = findViewById(R.id.favorite_btn);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    userAlertDialog.show();
                } else {
//                    favoriteBtn.setEnabled(false);
                    if (!running_query_list) {
                        running_query_list = true;
                        if (CHECK_FAVORITE_BTN) {
                            int index = AllDBQuery.wishList.indexOf(productID);
                            AllDBQuery.removeWishlist(index, ProductDetailsActivity.this);
                            favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                        } else {
                            favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_id_" + String.valueOf(AllDBQuery.wishList.size()), productID);
                            addProduct.put("list_size", (long) (AllDBQuery.wishList.size() + 1));
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                                    if (AllDBQuery.wishlistModels.size() != 0) {
                                                        AllDBQuery.wishlistModels.add(new WishlistModel(productID, (String) documentSnapshot.get("product_image_1")
                                                                , (String) documentSnapshot.get("product_title")
                                                                , (long) documentSnapshot.get("free_coupen")
                                                                , (String) documentSnapshot.get("average_rating")
                                                                , (long) documentSnapshot.get("total_rating")
                                                                , (String) documentSnapshot.get("product_price")
                                                                , (String) documentSnapshot.get("cutted_price")
                                                                , (boolean) documentSnapshot.get("cod")));
                                                    }
                                                    CHECK_FAVORITE_BTN = true;
                                                    favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                                                    AllDBQuery.wishList.add(productID);
                                                    Toast.makeText(ProductDetailsActivity.this, "Added to wishlist successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                       favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));

                                        String error = task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                    }
                                    running_query_list = false;
                                }
                            });

                        }
                    }
                }
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                if (currentUser == null) {
                    userAlertDialog.show();
                } else {
                    DeliveryActivity.cartItemModelList = new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID
                            , (String) documentSnapshot.get("product_image_1")
                            , (String) documentSnapshot.get("product_title")
                            , (long) documentSnapshot.get("free_cuepon")
                            , (String) documentSnapshot.get("product_price")
                            , (String) documentSnapshot.get("cutted_price")
                            , (long) 1
                            , (long) 0
                            , (long) 0
                            ,(boolean) documentSnapshot.get("stock")));
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));

                    if (AllDBQuery.cartItemModels.size() == 0){
                        AllDBQuery.loadAddress(ProductDetailsActivity.this,loadingDialog);
                    }else {
                        loadingDialog.dismiss();
                        Intent delivery = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(delivery);
                    }
                }

            }
        });



        //cuepon dialog
        final Dialog cueponDialog = new Dialog(ProductDetailsActivity.this);
        cueponDialog.setContentView(R.layout.redeem_dialog_layout);
        cueponDialog.setCancelable(true);
        cueponDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView all_select_btn = cueponDialog.findViewById(R.id.all_select_btn);
        cueponRecyclerView = cueponDialog.findViewById(R.id.cuepon_recyclerView);
        selected_cuepon_container = cueponDialog.findViewById(R.id.selected_cuepon_container);

        cueponTitle = cueponDialog.findViewById(R.id.reward_title);
        cueponBody = cueponDialog.findViewById(R.id.reward_details);
        cueponExpiry = cueponDialog.findViewById(R.id.reward_date);

        TextView originalPrice = cueponDialog.findViewById(R.id.original_price_text);
        TextView cueponPrice = cueponDialog.findViewById(R.id.cuepon_price_text);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cueponRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Title for Reward 1", "20-12-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 2", "10-11-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 3", "14-12-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 4", "29-10-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 5", "12-12-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 6", "19-12-2020", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList, false);
        cueponRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        all_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCueponDialogRecyclerView();
            }
        });

        cueponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cueponDialog.show();
            }
        });


        rateNowContainer = findViewById(R.id.rate_now_container);

        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        userAlertDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rating_list) {

                                running_rating_list = true;
                                setRating(starPosition);

                                Map<String, Object> updateRating = new HashMap<>();
                                if (AllDBQuery.myRatedIds.contains(productID)) {
                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);
                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);
                                    updateRating.put("average_rating", calculateRating((long) starPosition - initialRating, true));
                                } else {
                                    updateRating.put((starPosition + 1) + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_rating", calculateRating((long) starPosition + 1, false));
                                    updateRating.put("total_rating", (long) documentSnapshot.get("total_rating") + 1);
                                }
                                firebaseFirestore.collection("PRODUCTS")
                                        .document(productID)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> myRating = new HashMap<>();
                                            if (AllDBQuery.myRatedIds.contains(productID)) {
                                                myRating.put("rating_" + AllDBQuery.myRatedIds.indexOf(productID), (long) starPosition + 1);
                                            } else {
                                                myRating.put("list_size", (long) AllDBQuery.myRatedIds.size() + 1);
                                                myRating.put("product_id_" + AllDBQuery.myRatedIds.size(), productID);
                                                myRating.put("rating_" + AllDBQuery.myRatedIds.size(), (long) (starPosition + 1));
                                            }
                                            firebaseFirestore.collection("USERS")
                                                    .document(currentUser.getUid())
                                                    .collection("USER_DATA")
                                                    .document("MY_RATING")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        if (AllDBQuery.myRatedIds.contains(productID)) {

                                                            AllDBQuery.myRating.set(AllDBQuery.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                                            finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));
                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                        } else {
                                                            AllDBQuery.myRatedIds.add(productID);
                                                            AllDBQuery.myRating.add((long) (starPosition + 1));
                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_rating") + 1) + ")ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("total_rating") + 1 + " ratings");
                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1));

                                                            Toast.makeText(ProductDetailsActivity.this, "Thank you.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingfigures = (TextView) ratingsNoContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingsPrgressBarContainer.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                            progressBar.setMax(maxProgress);

                                                            progressBar.setProgress(Integer.parseInt((String) ratingfigures.getText()));
                                                        }
                                                        initialRating = starPosition;
                                                        averageRating.setText((calculateRating(0, true)));
                                                        averageRatingMiniview.setText((calculateRating(0, true)));

                                                        if (AllDBQuery.wishList.contains(productID) && AllDBQuery.wishlistModels.size() != 0) {
                                                            int index = AllDBQuery.wishList.indexOf(productID);
                                                            AllDBQuery.wishlistModels.get(index).setProductRating(averageRating.getText().toString());
                                                            AllDBQuery.wishlistModels.get(index).setProductTotalRating(Long.parseLong(totalRatingsFigure.getText().toString()));

                                                        }
                                                    } else {
                                                        setRating(initialRating);
                                                        String error = task.getException().getMessage();
                                                    }
                                                    running_rating_list = false;
                                                }
                                            });
                                        } else {
                                            running_rating_list = false;
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        }
                    }

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            productsCuponLayout.setVisibility(View.GONE);
        } else {
            productsCuponLayout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {

            if (AllDBQuery.myRating.size() == 0) {
                AllDBQuery.loadRating(ProductDetailsActivity.this);
            }

            if (AllDBQuery.cartList.size() == 0) {
                AllDBQuery.loadCart(ProductDetailsActivity.this, loadingDialog, false,new TextView(ProductDetailsActivity.this));
            }

            if (AllDBQuery.wishList.size() == 0) {
                AllDBQuery.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);

            } else {
                loadingDialog.dismiss();
            }

        } else {
            loadingDialog.dismiss();
        }


        if (AllDBQuery.myRatedIds.contains(productID)) {
            int index = AllDBQuery.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(AllDBQuery.myRating.get(index))) - 1;
            setRating(initialRating);
        }

        if (AllDBQuery.cartList.contains(productID)) {
            CHECK_CART_BTN = true;
        } else {
            CHECK_CART_BTN = false;
        }

        if (AllDBQuery.wishList.contains(productID)) {
            CHECK_FAVORITE_BTN = true;
            favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
        } else {
            favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
            CHECK_FAVORITE_BTN = false;
        }
    }

    public static void showCueponDialogRecyclerView() {
        if (cueponRecyclerView.getVisibility() == View.GONE) {
            cueponRecyclerView.setVisibility(View.VISIBLE);
            selected_cuepon_container.setVisibility(View.GONE);
        } else {
            cueponRecyclerView.setVisibility(View.GONE);
            selected_cuepon_container.setVisibility(View.VISIBLE);
        }
    }


    public static void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {

            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));

            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }


    private String calculateRating(long currentPosition, boolean update) {
        Double totalStar = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView rateNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStar = totalStar + (Long.parseLong(rateNo.getText().toString()) * x);
        }
        totalStar = totalStar + currentPosition;

        if (update) {
            return String.valueOf(totalStar / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStar / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_details_toolbar, menu);

//        cartItem = menu.findItem(R.id.navigation_cart);
//        if (AllDBQuery.cartList.size() > 0){
//            cartItem.setActionView(R.layout.badge_layout);
//            ImageView icon = cartItem.getActionView().findViewById(R.id.badge_icon);
//            icon.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
//            TextView text = cartItem.getActionView().findViewById(R.id.badge_count);
//            text.setText(String.valueOf(AllDBQuery.cartList.size()));
//
//            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (currentUser == null) {
//                        userAlertDialog.show();
//                    } else {
//                        Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
//                        startActivity(cartIntent);
//                    }
//                }
//            });
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search) {
            return true;
        } else if (id == R.id.main_cart) {
            if (currentUser == null) {
                userAlertDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }
}