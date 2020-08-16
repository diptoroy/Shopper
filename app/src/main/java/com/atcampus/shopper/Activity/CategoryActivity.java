package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.atcampus.shopper.Adapter.MultipleRecyclerviewAdapter;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.Model.MultipleRecyclerviewModel;
import com.atcampus.shopper.Model.SliderModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);

//        //Slider
//        List<SliderModel> sliderModelList = new ArrayList<>();
//
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FB9E8A"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#FB9E8A"));
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

        //multiple recyclerview
        LinearLayoutManager multipleManager = new LinearLayoutManager(this);
        multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(multipleManager);

        List<MultipleRecyclerviewModel> multipleRecyclerviewModels = new ArrayList<>();


        MultipleRecyclerviewAdapter multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(multipleRecyclerviewModels);
        categoryRecyclerView.setAdapter(multipleRecyclerviewAdapter);
        multipleRecyclerviewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchicon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search){
            return true;
        }else if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}