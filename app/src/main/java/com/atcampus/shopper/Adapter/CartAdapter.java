package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.R;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row, parent, false);
                return new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View amountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_amount, parent, false);
                return new TotalAmountViewHolder(amountView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                int resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                int cuopenNo = cartItemModelList.get(position).getFreeCoupens();
                String pPrice = cartItemModelList.get(position).getProductPrice();
                String cPrice = cartItemModelList.get(position).getCuttedPrice();
                int offer = cartItemModelList.get(position).getOffersApplied();
                ((CartItemViewHolder)holder).setItemDetails(resource,title,cuopenNo,pPrice,cPrice,offer);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItemText = cartItemModelList.get(position).getTotalItems();
                String totalItemPriceText = cartItemModelList.get(position).getTotalItemPrice();
                String deliveryPriceText = cartItemModelList.get(position).getDeliveryPrice();
                String totalAmountText = cartItemModelList.get(position).getTotalAmount();
                String savedItemText = cartItemModelList.get(position).getSavedItems();
                ((TotalAmountViewHolder)holder).setTotalAmount(totalItemText,totalItemPriceText,deliveryPriceText,totalAmountText,savedItemText);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView freeCuopenIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offerApplied;
        private TextView cuopenApplied;
        private TextView productQuantity;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cart_product_image);
            productTitle = itemView.findViewById(R.id.cart_product_name);
            freeCoupens = itemView.findViewById(R.id.cupon_text);
            freeCuopenIcon = itemView.findViewById(R.id.cupon_icon);
            productPrice = itemView.findViewById(R.id.main_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offerApplied = itemView.findViewById(R.id.ofers_applied);
            cuopenApplied = itemView.findViewById(R.id.cuopens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }

        private void setItemDetails(int resource, String title, int cuopenNo, String pPrice, String cPrice, int offer) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (cuopenNo > 0) {
                freeCoupens.setVisibility(View.VISIBLE);
                freeCuopenIcon.setVisibility(View.VISIBLE);
                if (cuopenNo == 1) {
                    freeCoupens.setText("free" + cuopenNo + "Cupoen");
                } else {
                    freeCoupens.setText("free" + cuopenNo + "Cupoens");
                }
            } else {
                freeCoupens.setVisibility(View.INVISIBLE);
                freeCuopenIcon.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(pPrice);
            cuttedPrice.setText(cPrice);
            if (offer > 0) {
                offerApplied.setVisibility(View.VISIBLE);
                offerApplied.setText(offer + "offer applied");
            } else {
                offerApplied.setVisibility(View.INVISIBLE);
            }
        }
    }

    class TotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public TotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItems = itemView.findViewById(R.id.total_item);
            totalItemsPrice = itemView.findViewById(R.id.total_item_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);

        }

        private void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedItemText) {
            totalItems.setText(totalItemText);
            totalItemsPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedItemText);
        }
    }
}
