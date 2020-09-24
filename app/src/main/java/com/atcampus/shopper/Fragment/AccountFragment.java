package com.atcampus.shopper.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.atcampus.shopper.Activity.AddressesActivity;
import com.atcampus.shopper.Activity.RegisterActivity;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    public static final int MANAGE_ADDRESS = 1;
    private Button viewAddressesBtn;
    private ImageView signOutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        viewAddressesBtn = view.findViewById(R.id.view_address_btn);
        signOutBtn = view.findViewById(R.id.signOutBtn);

        viewAddressesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delivery = new Intent(getContext(), AddressesActivity.class);
                delivery.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(delivery);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                AllDBQuery.clearData();
                Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(registerIntent);
                getActivity().finish();
            }
        });
//        setHasOptionsMenu(true);

        return view;
    }

//    @Override
//    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        MenuItem menuItem = menu.findItem(R.id.main_search);
//        menuItem.setVisible(false);
//
//    }
}
