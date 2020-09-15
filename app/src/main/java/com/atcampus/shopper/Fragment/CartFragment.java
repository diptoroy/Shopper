package com.atcampus.shopper.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atcampus.shopper.Activity.AddAddressActivity;
import com.atcampus.shopper.Activity.DeliveryActivity;
import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Adapter.CartAdapter;
import com.atcampus.shopper.Adapter.WishlistAdapter;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartRecyclerView;
    private Button continueBtn;
    public static CartAdapter cartAdapter;
    private Dialog loadingDialog;
    private TextView totalCartAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalCartAmount = view.findViewById(R.id.total_cart_amount);

        //loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        continueBtn = view.findViewById(R.id.continue_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(linearLayoutManager);

        if (AllDBQuery.cartItemModels.size() == 0){
            AllDBQuery.cartList.clear();
            AllDBQuery.loadCart(getContext(),loadingDialog,true,totalCartAmount);
        }else {
            if (AllDBQuery.cartItemModels.get(AllDBQuery.cartItemModels.size()-1).getType() == CartItemModel.TOTAL_AMOUNT){
                LinearLayout parent = (LinearLayout) totalCartAmount.getParent().getParent();
                parent.setVisibility(View.GONE);
            }
            loadingDialog.dismiss();
        }

        List<CartItemModel> cartItemModels = new ArrayList<>();


        cartAdapter = new CartAdapter(AllDBQuery.cartItemModels,totalCartAmount,true);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                for (int x = 0; x < AllDBQuery.cartItemModels.size(); x++){
                    CartItemModel cartItemModel = AllDBQuery.cartItemModels.get(x);
                    if (cartItemModel.isStock()){
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                loadingDialog.show();
                if (AllDBQuery.cartItemModels.size() == 0){
                    AllDBQuery.loadAddress(getContext(),loadingDialog);
                }else {
                    loadingDialog.dismiss();
                    Intent delivery = new Intent(getContext(), DeliveryActivity.class);
                    startActivity(delivery);
                }

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
