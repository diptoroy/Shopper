package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Button addAddressBtn;
    private EditText areaText,flatText,picodeText,cityText,landMarkText,nameText,mobileText,anotherMobileText;
    private Spinner divisionSpinner;
    private Dialog loadingDialog;

    private String selectedDivision;
    private String[] divisionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        areaText = findViewById(R.id.area_street_edittext);
        flatText = findViewById(R.id.flat_building_edittext);
        picodeText = findViewById(R.id.pincode_edittext);
        cityText = findViewById(R.id.city_edittext);
        landMarkText = findViewById(R.id.landmark_edittext);
        nameText = findViewById(R.id.name_edittext);
        mobileText = findViewById(R.id.mobile_edittext);
        anotherMobileText = findViewById(R.id.another_mobile_edittext);

        //loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        divisionArray = getResources().getStringArray(R.array.bd_division);
        divisionSpinner = findViewById(R.id.division_spinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,divisionArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(spinnerAdapter);
        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionArray[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addAddressBtn = findViewById(R.id.add_address_btn);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cityText.getText())){
                    if (!TextUtils.isEmpty(areaText.getText())){
                        if (!TextUtils.isEmpty(flatText.getText())){
                            if (!TextUtils.isEmpty(picodeText.getText()) && picodeText.getText().length() == 4){
                                if (!TextUtils.isEmpty(nameText.getText())){
                                    if (!TextUtils.isEmpty(mobileText.getText()) && mobileText.getText().length() == 11){
                                        loadingDialog.show();
                                        final String fullAddress = flatText.getText().toString()+","+areaText.getText().toString()+","+landMarkText.getText().toString()+","+cityText.getText().toString()+","+selectedDivision;
                                        Map<String,Object> addAddress = new HashMap<>();
                                        addAddress.put("list_size", (long)AllDBQuery.addressModels.size()+1);
                                        if (TextUtils.isEmpty(anotherMobileText.getText())) {
                                            addAddress.put("name_" + String.valueOf((long) AllDBQuery.addressModels.size() + 1), nameText.getText().toString() + " - " + mobileText.getText().toString());
                                        }else {
                                            addAddress.put("name_" + String.valueOf((long) AllDBQuery.addressModels.size() + 1), nameText.getText().toString() + " - " + mobileText.getText().toString() + " or " +anotherMobileText.getText().toString());
                                        }
                                        addAddress.put("address_"+ String.valueOf((long)AllDBQuery.addressModels.size()+1),fullAddress);
                                        addAddress.put("pincode_"+String.valueOf((long)AllDBQuery.addressModels.size()+1),picodeText.getText().toString());
                                        addAddress.put("selected_"+String.valueOf((long)AllDBQuery.addressModels.size()+1),true);
                                        if (AllDBQuery.addressModels.size() > 0) {
                                            addAddress.put("selected_" + (AllDBQuery.selectedAddress + 1), false);
                                        }

                                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                                                .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    if (AllDBQuery.addressModels.size() > 0) {
                                                        AllDBQuery.addressModels.get(AllDBQuery.selectedAddress).setSelected(false);
                                                    }
                                                    if (TextUtils.isEmpty(anotherMobileText.getText())) {
                                                        AllDBQuery.addressModels.add(new AddressModel(nameText.getText().toString() + " - " + mobileText.getText().toString(), fullAddress, picodeText.getText().toString(), true));
                                                    }else {
                                                        AllDBQuery.addressModels.add(new AddressModel(nameText.getText().toString() + " - " + mobileText.getText().toString() + " or " + anotherMobileText.getText().toString(), fullAddress, picodeText.getText().toString(), true));
                                                    }


                                                    if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                        Intent delivery = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                        startActivity(delivery);
                                                    }else {
                                                        AddressesActivity.refreshItem(AllDBQuery.selectedAddress,AllDBQuery.addressModels.size() - 1);
                                                    }
                                                    AllDBQuery.selectedAddress = AllDBQuery.addressModels.size() - 1;
                                                    finish();
                                                }else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });
                                    } else {
                                        mobileText.requestFocus();
                                        Toast.makeText(AddAddressActivity.this,"Provide a valid phone number",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    nameText.requestFocus();
                                }
                            }else {
                                picodeText.requestFocus();
                                Toast.makeText(AddAddressActivity.this,"Provide a valid pincode",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            flatText.requestFocus();
                        }
                    }else {
                        areaText.requestFocus();
                    }
                }else {
                    cityText.requestFocus();
                }
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