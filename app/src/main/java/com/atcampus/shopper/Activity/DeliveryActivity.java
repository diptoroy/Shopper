package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atcampus.shopper.Adapter.CartAdapter;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView deliveryRecyclerView;
    private Button addAddressBtn;

    public static final int SELECT_ADDRESS = 0;
    private TextView totalCartAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        totalCartAmount = findViewById(R.id.totalAmount);

        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModels = new ArrayList<>();


        CartAdapter cartAdapter = new CartAdapter(cartItemModels,totalCartAmount);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        addAddressBtn = findViewById(R.id.address_button);
        addAddressBtn.setVisibility(View.VISIBLE);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delivery = new Intent(DeliveryActivity.this, AddressesActivity.class);
                delivery.putExtra("MODE",SELECT_ADDRESS);
                startActivity(delivery);
            }
        });
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