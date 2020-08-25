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
import com.atcampus.shopper.Fragment.ProductSpeceficationFragment;
import com.atcampus.shopper.Fragment.ProductsDescriptionFragment;
import com.atcampus.shopper.Model.ProductsSpeceficationModel;
import com.atcampus.shopper.Model.RewardModel;
import com.atcampus.shopper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Integer> productImages;
    private FloatingActionButton favoriteBtn;
    private static boolean CHECK_FAVORITE_BTN = false;
    private Button buyNowBtn,cueponBtn;
    private TextView productName,averageRating,productsTotalRating,productsPrice,productsDiscountPrice,productCodText,rewardsTitle,rewardsBody;
    private TextView pTitleBody,pDetailBody;
    private ImageView productCod;

    //product details
    private ViewPager viewPager, descViewpager;
    private TabLayout tabLayout, descTabLayout;
    private ConstraintLayout productDescriptionContainer,productTitleSpecContainer;
    private String pDesc,pOtherDesc;

    //rating layout
    private LinearLayout userratingContainer;
    private TextView totalRating;
    private LinearLayout ratingsNumberContainer;
    private TextView totalRatingText,averageRatingText;
    private LinearLayout ratingProgressContainer;

    //cuepon dialog
    public static TextView cueponTitle,cueponBody,cueponExpiry;
    public static RecyclerView cueponRecyclerView;
    public static LinearLayout selected_cuepon_container;

    private List<ProductsSpeceficationModel> productsSpeceficationModelList = new ArrayList<>();

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //product image slider
        viewPager = findViewById(R.id.product_images_viewpager);
        tabLayout = findViewById(R.id.image_indicator);
        buyNowBtn = findViewById(R.id.buyNowBtn);
        cueponBtn = findViewById(R.id.cuepon_btn);

        productName = findViewById(R.id.products_name);
        averageRating = findViewById(R.id.products_rating_text);
        productsTotalRating = findViewById(R.id.products_total_rating);
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
        totalRating = findViewById(R.id.total_rating);
        ratingsNumberContainer = findViewById(R.id.ratings_number_container);
        totalRatingText = findViewById(R.id.total_rating_text);
        ratingProgressContainer = findViewById(R.id.rating_progress_container);
        averageRatingText = findViewById(R.id.rating_point);

        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document("9Mv0ZqBiqJP9XiKnWpg0")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long x = 1; x <(long) documentSnapshot.get("no_of_product_image")+1; x++){
                        productImages.add((String)documentSnapshot.get("product_image_"+x));
                    }
                    ProductImagesViewpagerAdapter adapter = new ProductImagesViewpagerAdapter(productImages);
                    viewPager.setAdapter(adapter);
                    productName.setText((String)documentSnapshot.get("product_title"));
                    averageRating.setText((String)documentSnapshot.get("average_rating"));
                    productsTotalRating.setText("("+(long) documentSnapshot.get("total_rating")+")ratings");
                    productsPrice.setText((String)"$"+documentSnapshot.get("product_price"));
                    productsDiscountPrice.setText((String)"$"+documentSnapshot.get("cutted_price"));
                    if ((boolean)documentSnapshot.get("cod")){
                        productCod.setVisibility(View.VISIBLE);
                        productCodText.setVisibility(View.VISIBLE);
                    }else {
                        productCod.setVisibility(View.INVISIBLE);
                        productCodText.setVisibility(View.INVISIBLE);
                    }
                    rewardsTitle.setText((String)documentSnapshot.get("free_cuepon")+(String)documentSnapshot.get("free_cuepon_title"));
                    rewardsBody.setText((String)documentSnapshot.get("free_cuepon_body"));
                    if ((boolean)documentSnapshot.get("use_tab_layout")){
                        productDescriptionContainer.setVisibility(View.VISIBLE);
                        productTitleSpecContainer.setVisibility(View.GONE);
                        pDesc = (String)documentSnapshot.get("product_desc");
                        pOtherDesc = (String)documentSnapshot.get("product_other_desc");

                        for (long x =1; x < (long)documentSnapshot.get("total_spec_title")+1; x++){
                            productsSpeceficationModelList.add(new ProductsSpeceficationModel((String)documentSnapshot.get("spec_title_"+x),0));
                            for (long y = 1; y < (long)documentSnapshot.get("spec_title_"+x+"_field")+1 ; y++){
                                productsSpeceficationModelList.add(new ProductsSpeceficationModel(1,(String)documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name"),(String) documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value")));
                            }
                        }
                    }else {
                        productDescriptionContainer.setVisibility(View.GONE);
                        productTitleSpecContainer.setVisibility(View.VISIBLE);
                        //pTitleBody.setText(documentSnapshot.get("product_title").toString());
                        pDetailBody.setText((String)documentSnapshot.get("product_desc"));
                    }
                    totalRating.setText((long)documentSnapshot.get("total_rating")+" ratings");
                    for (int r = 0; r < 5; r++){
                        TextView rating = (TextView) ratingsNumberContainer.getChildAt(r);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-r)+"_star")));

                        ProgressBar progressBar = (ProgressBar) ratingProgressContainer.getChildAt(r);
                        int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_rating")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-r)+"_star"))));
                    }
                    totalRatingText.setText(String.valueOf((long)documentSnapshot.get("total_rating")));
                    averageRatingText.setText((String)documentSnapshot.get("average_rating"));
                    descViewpager.setAdapter(new ProductsDescriptionAdapter(getSupportFragmentManager(), descTabLayout.getTabCount(),pDesc,pOtherDesc,productsSpeceficationModelList));
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
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
                if (CHECK_FAVORITE_BTN) {
                    CHECK_FAVORITE_BTN = false;
                    favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                } else {
                    CHECK_FAVORITE_BTN = true;
                    favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                }
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delivery = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(delivery);
            }
        });

        //cuepon dialog
        final Dialog cueponDialog = new Dialog(ProductDetailsActivity.this);
        cueponDialog.setContentView(R.layout.redeem_dialog_layout);
        cueponDialog.setCancelable(true);
        cueponDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

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
        rewardModelList.add(new RewardModel("Title for Reward 1","20-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 2","10-11-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 3","14-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 4","29-10-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 5","12-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 6","19-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList,false);
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

        //rating layout
        userratingContainer =findViewById(R.id.user_rating_container);
        for (int x = 0; x < userratingContainer.getChildCount(); x++){
            final int starPosition = x;
            userratingContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }
    }

    public static void showCueponDialogRecyclerView(){
        if (cueponRecyclerView.getVisibility() == View.GONE){
            cueponRecyclerView.setVisibility(View.VISIBLE);
            selected_cuepon_container.setVisibility(View.GONE);
        }else {
            cueponRecyclerView.setVisibility(View.GONE);
            selected_cuepon_container.setVisibility(View.VISIBLE);
        }
    }

    private void setRating(int starPosition){
        for (int x = 0; x < userratingContainer.getChildCount(); x++){
            ImageView starBtn = (ImageView) userratingContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F4BCBC")));
            if (x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFBF00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_details_toolbar, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}