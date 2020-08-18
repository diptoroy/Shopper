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
    public static List<WishlistModel> wishlistModelsList;

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