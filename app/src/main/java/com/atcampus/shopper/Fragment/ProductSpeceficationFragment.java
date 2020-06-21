package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.ProductsSpecAdapter;
import com.atcampus.shopper.Model.ProductsSpeceficationModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;


public class ProductSpeceficationFragment extends Fragment {



    public ProductSpeceficationFragment() {
        // Required empty public constructor
    }

    private RecyclerView specRecyclerview;
    private ProductsSpecAdapter productsSpecAdapter;
    private List<ProductsSpeceficationModel> productsSpeceficationModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specefication, container, false);

        specRecyclerview = view.findViewById(R.id.products_spec_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        specRecyclerview.setLayoutManager(linearLayoutManager);

        productsSpeceficationModelList = new ArrayList<>();
        productsSpeceficationModelList.add(new ProductsSpeceficationModel("Color","Black"));
        productsSpeceficationModelList.add(new ProductsSpeceficationModel("Size","4XL"));
        productsSpeceficationModelList.add(new ProductsSpeceficationModel("Brand","Armani"));
        productsSpeceficationModelList.add(new ProductsSpeceficationModel("Cotton","100%"));
        productsSpeceficationModelList.add(new ProductsSpeceficationModel("Value","$135"));

        productsSpecAdapter = new ProductsSpecAdapter(productsSpeceficationModelList);
        specRecyclerview.setAdapter(productsSpecAdapter);
        productsSpecAdapter.notifyDataSetChanged();
        return view;
    }
}