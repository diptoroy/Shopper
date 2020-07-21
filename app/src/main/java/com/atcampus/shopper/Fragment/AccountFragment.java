package com.atcampus.shopper.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atcampus.shopper.Activity.AddressesActivity;
import com.atcampus.shopper.Activity.DeliveryActivity;
import com.atcampus.shopper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    public static final int MANAGE_ADDRESS = 1;
    private Button viewAddressesBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        viewAddressesBtn = view.findViewById(R.id.view_address_btn);
        viewAddressesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delivery = new Intent(getContext(), AddressesActivity.class);
                delivery.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(delivery);
            }
        });
        return view;
    }
}
