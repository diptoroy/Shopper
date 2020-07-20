package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.atcampus.shopper.Adapter.AddressAdapter;
import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

public class AddressesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView addressRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        addressRecyclerView = findViewById(R.id.addresses_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecyclerView.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754"));
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754"));
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754"));

        AddressAdapter addressAdapter = new AddressAdapter(addressModelList);
        addressRecyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}