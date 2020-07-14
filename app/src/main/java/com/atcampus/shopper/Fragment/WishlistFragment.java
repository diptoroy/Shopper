package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.WishlistAdapter;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {

    public WishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        wishListRecyclerView = view.findViewById(R.id.wishlist_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishListRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelsList = new ArrayList<>();
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 12 Max",1,"4.5",46,"$799","$849","Cash On Delivery Available."));
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 11",2,"4.2",34,"$699","$749","Cash On Delivery Available."));
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 12 Max",0,"3.2",56,"$599","$649","No Cash On Delivery Available."));
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 8",0,"5.0",49,"$799","$849","Cash On Delivery Available."));
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 10",3,"4.0",16,"$799","$849","No Cash On Delivery Available."));
        wishlistModelsList.add(new WishlistModel(R.drawable.phone,"Iphone 12 ",0,"5.0",93,"$799","$849","Cash On Delivery Available."));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelsList);
        wishListRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}
