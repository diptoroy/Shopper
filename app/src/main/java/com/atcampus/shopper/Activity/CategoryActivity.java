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
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

import static com.atcampus.shopper.Query.AllDBQuery.allList;
import static com.atcampus.shopper.Query.AllDBQuery.categoryName;
import static com.atcampus.shopper.Query.AllDBQuery.loadView;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView categoryRecyclerView;
    private MultipleRecyclerviewAdapter multipleRecyclerviewAdapter;
    private List<MultipleRecyclerviewModel> fakeMultipleRecyclerviewModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Multiple RecyclerView
        List<SliderModel> fakeSliderModel = new ArrayList<>();
        fakeSliderModel.add(new SliderModel("null","#ffffff"));
        fakeSliderModel.add(new SliderModel("null","#ffffff"));
        fakeSliderModel.add(new SliderModel("null","#ffffff"));

        List<DealsModel> fakeDealsModel = new ArrayList<>();
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));
        fakeDealsModel.add(new DealsModel("","","","",""));

        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(0,fakeSliderModel));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(1,"","#ffffff"));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(2,"","#ffffff",fakeDealsModel,new ArrayList<WishlistModel>()));
        fakeMultipleRecyclerviewModels.add(new MultipleRecyclerviewModel(3,"","#ffffff",fakeDealsModel));


        categoryRecyclerView = findViewById(R.id.category_recyclerview);


        //multiple recyclerview
        LinearLayoutManager multipleManager = new LinearLayoutManager(this);
        multipleManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(multipleManager);
        multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(fakeMultipleRecyclerviewModels);


        int listPosition = 0;
        for (int x = 0;x < categoryName.size(); x++){
            if (categoryName.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
        }

        if (listPosition == 0){
            categoryName.add(title.toUpperCase());
            allList.add(new ArrayList<MultipleRecyclerviewModel>());
            loadView(categoryRecyclerView,this,categoryName.size() - 1,title);
        }else {
            multipleRecyclerviewAdapter = new MultipleRecyclerviewAdapter(allList.get(listPosition));
        }

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