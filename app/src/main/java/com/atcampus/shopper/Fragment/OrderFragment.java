package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.OrderAdapter;
import com.atcampus.shopper.Model.OrderItemModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    public OrderFragment() {
        // Required empty public constructor
    }

    private RecyclerView orderRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderRecyclerView.setLayoutManager(linearLayoutManager);

        List<OrderItemModel> orderItemModelList = new ArrayList<>();
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"Iphone 11 pro","Delivered On Sunday,2 July,2020",2));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"Iphone 10 pro","Delivered On Monday,3 July,2020",3));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"Iphone 8","cancelled",2));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"Iphone 12","Delivered On Sunday,2 July,2020",3));

        OrderAdapter orderAdapter = new OrderAdapter(orderItemModelList);
        orderRecyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();


        return view;
    }


}
