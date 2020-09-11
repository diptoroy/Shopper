package com.atcampus.shopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.atcampus.shopper.R;

public class AddAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Button addAddressBtn;
    private EditText areaText,flatText,picodeText,cityText,landMarkText,nameText,mobileText,anotherMobileText;
    private Spinner divisionSpinner;

    private String selectedDivision;
    private String[] divisionArray = getResources().getStringArray(R.array.bd_division);

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

                }

                Intent delivery = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                startActivity(delivery);
                finish();
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