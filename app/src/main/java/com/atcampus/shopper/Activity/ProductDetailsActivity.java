package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.atcampus.shopper.Adapter.ProductImagesViewpagerAdapter;
import com.atcampus.shopper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Integer> productImages;
    private FloatingActionButton favoriteBtn;
    private static boolean CHECK_FAVORITE_BTN = false;

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

        productImages = new ArrayList<>();
        productImages.add(R.drawable.phone );
        productImages.add(R.drawable.banner );
        productImages.add(R.drawable.backpack );
        productImages.add(R.drawable.bag );

        ProductImagesViewpagerAdapter adapter = new ProductImagesViewpagerAdapter(productImages);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager,true);

        favoriteBtn = findViewById(R.id.favorite_btn);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CHECK_FAVORITE_BTN){
                    CHECK_FAVORITE_BTN = false;
                    favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                }else {
                    CHECK_FAVORITE_BTN = true;
                    favoriteBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#D10000")));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_details_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }else if (id == R.id.main_search){
            return true;
        }else if (id == R.id.main_cart){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}