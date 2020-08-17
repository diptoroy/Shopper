package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.atcampus.shopper.Adapter.DealsAdapter;
import com.atcampus.shopper.Adapter.TrendingAdapter;
import com.atcampus.shopper.Adapter.WishlistAdapter;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;
    public static List<DealsModel> dealsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        int view_code = getIntent().getIntExtra("viewCode", -1);

        if (view_code == 0) {
            //RecyclerView
            viewAllRecyclerView = findViewById(R.id.view_All_Recycler_View);
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            viewAllRecyclerView.setLayoutManager(linearLayoutManager);

            List<WishlistModel> wishlistModelsList = new ArrayList<>();
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 12 Max", 1, "4.5", 46, "$799", "$849", "Cash On Delivery Available."));
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 11", 2, "4.2", 34, "$699", "$749", "Cash On Delivery Available."));
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 12 Max", 0, "3.2", 56, "$599", "$649", "No Cash On Delivery Available."));
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 8", 0, "5.0", 49, "$799", "$849", "Cash On Delivery Available."));
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 10", 3, "4.0", 16, "$799", "$849", "No Cash On Delivery Available."));
            wishlistModelsList.add(new WishlistModel(R.drawable.phone, "Iphone 12 ", 0, "5.0", 93, "$799", "$849", "Cash On Delivery Available."));

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelsList, false);
            viewAllRecyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();

        } else if (view_code == 1) {
            //GridView
            viewAllGridView = findViewById(R.id.view_All_Grid_View);
            viewAllGridView.setVisibility(View.VISIBLE);


            TrendingAdapter trendingAdapter = new TrendingAdapter(dealsModels);
            viewAllGridView.setAdapter(trendingAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}