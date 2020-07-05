package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.CartAdapter;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModels = new ArrayList<>();
        cartItemModels.add(new CartItemModel(0,R.drawable.phone,"Iphone 11",2,"$149","$169",1,0,0));
        cartItemModels.add(new CartItemModel(1,"2","$175","$0","2","$350"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModels);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
