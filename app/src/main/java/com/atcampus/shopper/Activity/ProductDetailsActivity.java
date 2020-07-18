package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atcampus.shopper.Adapter.ProductImagesViewpagerAdapter;
import com.atcampus.shopper.Adapter.ProductsDescriptionAdapter;
import com.atcampus.shopper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager, descViewpager;
    private TabLayout tabLayout, descTabLayout;
    private List<Integer> productImages;
    private FloatingActionButton favoriteBtn;
    private static boolean CHECK_FAVORITE_BTN = false;
    private Button buyNowBtn;

    //rating layout
    private LinearLayout userratingContainer;

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

        productImages = new ArrayList<>();
        productImages.add(R.drawable.phone);
        productImages.add(R.drawable.banner);
        productImages.add(R.drawable.backpack);
        productImages.add(R.drawable.bag);

        ProductImagesViewpagerAdapter adapter = new ProductImagesViewpagerAdapter(productImages);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        //products Description
        descViewpager = findViewById(R.id.products_description_viewpager);
        descTabLayout = findViewById(R.id.products_description_tabs);

        descViewpager.setAdapter(new ProductsDescriptionAdapter(getSupportFragmentManager(), descTabLayout.getTabCount()));
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