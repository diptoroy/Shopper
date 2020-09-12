package com.atcampus.shopper.Adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Model.CartItemModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    private int lastPosition = -1;
    private TextView cartTotalAmount;
    private boolean showRemoveBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showRemoveBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showRemoveBtn = showRemoveBtn;
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
                String productId = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long cuopenNo = cartItemModelList.get(position).getFreeCoupens();
                String pPrice = cartItemModelList.get(position).getProductPrice();
                String cPrice = cartItemModelList.get(position).getCuttedPrice();
                Long offer = cartItemModelList.get(position).getOffersApplied();
                ((CartItemViewHolder)holder).setItemDetails(productId,resource,title,cuopenNo,pPrice,cPrice,offer,position);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItemText = 0;
                int totalItemPriceText = 0;
                String deliveryPriceText;
                int totalAmountText;
                int savedItemText = 0;

                for (int x = 0; x < cartItemModelList.size(); x++){
                    if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM){
                        totalItemText++;
                        totalItemPriceText = totalItemPriceText + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                    }
                }
                if (totalItemPriceText > 160){
                    deliveryPriceText = "Free";
                    totalAmountText = totalItemPriceText;
                }else {
                    deliveryPriceText = "10";
                    totalAmountText = totalItemPriceText + 10;
                }
                ((TotalAmountViewHolder)holder).setTotalAmount(totalItemText,totalItemPriceText,deliveryPriceText,totalAmountText,savedItemText);
                break;
            default:
                return;
        }

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
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
        private LinearLayout removeItemCart;

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
            removeItemCart = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productID, String resource, String title, Long cuopenNo, String pPrice, String cPrice, Long offer, final int position) {
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.photo)).into(productImage);
            productTitle.setText(title);
            if (cuopenNo > 0) {
                freeCoupens.setVisibility(View.VISIBLE);
                freeCuopenIcon.setVisibility(View.VISIBLE);
                if (cuopenNo == 1) {
                    freeCoupens.setText("free " + cuopenNo + " Coupen");
                } else {
                    freeCoupens.setText("free " + cuopenNo + " Coupens");
                }
            } else {
                freeCoupens.setVisibility(View.INVISIBLE);
                freeCuopenIcon.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(pPrice);
            cuttedPrice.setText(cPrice);
            if (offer > 0) {
                offerApplied.setVisibility(View.VISIBLE);
                offerApplied.setText(offer + " offer applied");
            } else {
                offerApplied.setVisibility(View.INVISIBLE);
            }

            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);

                    final EditText quatityText = quantityDialog.findViewById(R.id.quantity_no_text);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty : "+ quatityText.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });

            if (showRemoveBtn){
                removeItemCart.setVisibility(View.VISIBLE);
            }else {
                removeItemCart.setVisibility(View.GONE);
            }

            removeItemCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_cart_list){
                        ProductDetailsActivity.running_cart_list = true;
                        AllDBQuery.removeCart(position,itemView.getContext());
                    }
                }
            });
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

        private void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPriceText, int totalAmountText, int savedItemText) {
            totalItems.setText("Price ("+totalItemText+" items)");
            totalItemsPrice.setText("$("+totalItemPriceText+")");
            if (deliveryPriceText.equals("Free")) {
                deliveryPrice.setText(deliveryPriceText);
            }else {
                deliveryPrice.setText("$("+deliveryPriceText+")");
            }
            totalAmount.setText("$("+totalAmountText+")");
            cartTotalAmount.setText("$"+totalAmountText+"");
            savedAmount.setText("You saved $("+savedItemText+" on this offer.)");
        }
    }
}
