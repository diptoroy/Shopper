package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.atcampus.shopper.Adapter.AddressAdapter;
import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atcampus.shopper.Activity.DeliveryActivity.SELECT_ADDRESS;

public class AddressesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView addressRecyclerView;
    private static AddressAdapter addressAdapter;
    private Button activeBtn;
    private FloatingActionButton addBtn;
    private TextView addressCount;
    private Dialog loadingDialog;

    private int preAddress;

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
        addBtn = findViewById(R.id.add_btn);
        addressCount = findViewById(R.id.address_count);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        preAddress = AllDBQuery.selectedAddress;

        addressRecyclerView = findViewById(R.id.addresses_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecyclerView.setLayoutManager(linearLayoutManager);

        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS){
            activeBtn.setVisibility(View.VISIBLE);
        }else {
            activeBtn.setVisibility(View.GONE);
        }
        activeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AllDBQuery.selectedAddress != preAddress){
                    final int preAddressIndex = preAddress;
                    loadingDialog.show();
                    Map<String,Object> updateAddress = new HashMap<>();
                    updateAddress.put("selected_"+String.valueOf(preAddress+1),false);
                    updateAddress.put("selected_"+String.valueOf(AllDBQuery.selectedAddress+1),true);

                    preAddress = AllDBQuery.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                            .update(updateAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                finish();
                            }else {
                                preAddress = preAddressIndex;
                                String error = task.getException().getMessage();
                                Toast.makeText(AddressesActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                }else {
                    finish();
                }
            }
        });

        addressAdapter = new AddressAdapter(AllDBQuery.addressModels,mode);
        addressRecyclerView.setAdapter(addressAdapter);
        ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddress = new Intent(AddressesActivity.this,AddAddressActivity.class);
                addAddress.putExtra("INTENT","nope");
                startActivity(addAddress);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        addressCount.setText(String.valueOf(AllDBQuery.addressModels.size())+" Saved addresses");
    }

    public static void refreshItem(int deselect, int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            if (AllDBQuery.selectedAddress != preAddress){
                AllDBQuery.addressModels.get(AllDBQuery.selectedAddress).setSelected(false);
                AllDBQuery.addressModels.get(preAddress).setSelected(true);
                AllDBQuery.selectedAddress = preAddress;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        if (AllDBQuery.selectedAddress != preAddress){
            AllDBQuery.addressModels.get(AllDBQuery.selectedAddress).setSelected(false);
            AllDBQuery.addressModels.get(preAddress).setSelected(true);
            AllDBQuery.selectedAddress = preAddress;
        }
        super.onBackPressed();
    }
}