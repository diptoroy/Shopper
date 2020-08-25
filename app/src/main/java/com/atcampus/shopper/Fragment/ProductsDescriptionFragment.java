package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atcampus.shopper.R;


public class ProductsDescriptionFragment extends Fragment {



    public ProductsDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView productDescriptionText;
    public String body;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_description, container, false);

        productDescriptionText = view.findViewById(R.id.product_description_text);
        productDescriptionText.setText(body);
//        if (tabSelected == 0) {
//            productDescriptionText.setText(pDesc);
//        }else {
//            productDescriptionText.setText(pOtherDesc);
//        }
        return view;
    }
}