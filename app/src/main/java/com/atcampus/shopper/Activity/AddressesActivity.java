package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.atcampus.shopper.Adapter.AddressAdapter;
import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

import static com.atcampus.shopper.Activity.DeliveryActivity.SELECT_ADDRESS;

public class AddressesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView addressRecyclerView;
    private static AddressAdapter addressAdapter;
    private Button activeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        activeBtn = findViewById(R.id.active_btn);

        addressRecyclerView = findViewById(R.id.addresses_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecyclerView.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754",true));
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754",false));
        addressModelList.add(new AddressModel("Full Name :"+" Dipto Roy","Address :"+" Lakshmipur,Gaibandha","Pincode : "+" 234754",false));


        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS){
            activeBtn.setVisibility(View.VISIBLE);
        }else {
            activeBtn.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(addressModelList,mode);
        addressRecyclerView.setAdapter(addressAdapter);
        ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect,int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
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