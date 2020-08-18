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

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelsList,true);
        wishListRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}
