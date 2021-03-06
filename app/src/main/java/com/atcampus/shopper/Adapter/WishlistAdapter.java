package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.Query.AllDBQuery;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModels;
    private Boolean wishList;
    private int lastPosition = -1;

    public WishlistAdapter(List<WishlistModel> wishlistModels, Boolean wishList) {
        this.wishlistModels = wishlistModels;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {

        String productId = wishlistModels.get(position).getProductId();
        String resource = wishlistModels.get(position).getProductImage();
        String title = wishlistModels.get(position).getProductName();
        long freeCueponNo = wishlistModels.get(position).getProductCuepon();
        String rating = wishlistModels.get(position).getProductRating();
        long totalRating = wishlistModels.get(position).getProductTotalRating();
        String price = wishlistModels.get(position).getProductPrice();
        String cuttedPrice = wishlistModels.get(position).getProductCuttedPrice();
        boolean deliSystem = wishlistModels.get(position).isCod();
        boolean instock = wishlistModels.get(position).isInStock();

        holder.setData(productId,resource,title,freeCueponNo,rating,totalRating,price,cuttedPrice,deliSystem,position,instock);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return wishlistModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;
        private ImageView productCueponIcon;
        private TextView productCuepon;
        private TextView productRating;
        private TextView productTotalRating;
        private TextView productPrice;
        private View productDivider;
        private TextView productCuttedPrice;
        private TextView productDeliverySystem;
        private ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_title);
            productCueponIcon = itemView.findViewById(R.id.cuepon_icon);
            productCuepon = itemView.findViewById(R.id.product_cuepon);
            productRating = itemView.findViewById(R.id.products_rating_text);
            productTotalRating = itemView.findViewById(R.id.total_rating);
            productPrice = itemView.findViewById(R.id.product_price);
            productDivider = itemView.findViewById(R.id.price_divider);
            productCuttedPrice = itemView.findViewById(R.id.product_cutted_price);
            productDeliverySystem = itemView.findViewById(R.id.delivery_system);
            deleteBtn = itemView.findViewById(R.id.delete_product);
        }

        private void setData(final String pId, String resource, String title, long freeCueponNo, String rating, long totalRating, String price, String cuttedPrice, final boolean deliSystem, final int index,boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.photo)).into(productImage);
            productName.setText(title);
            if (freeCueponNo != 0 && inStock) {
                productCueponIcon.setVisibility(View.VISIBLE);
                if (freeCueponNo == 1) {
                    productCuepon.setText("Free " + freeCueponNo + " coupen");
                } else {
                    productCuepon.setText("Free " + freeCueponNo + " coupens");
                }
            } else {
                productCueponIcon.setVisibility(View.INVISIBLE);
                productCuepon.setVisibility(View.INVISIBLE);
            }
            LinearLayout linearLayout = (LinearLayout) productRating.getParent();
            if (inStock){
                productRating.setVisibility(View.VISIBLE);
                productTotalRating.setVisibility(View.VISIBLE);
                productPrice.setTextColor(Color.parseColor("#000000"));
                productCuttedPrice.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);

                productRating.setText(rating);
                productTotalRating.setText("("+totalRating+") ratings");
                productPrice.setText("$"+price+"");
                productCuttedPrice.setText("$"+cuttedPrice+"");

                if (deliSystem){
                    productDeliverySystem.setVisibility(View.VISIBLE);
                }else {
                    productDeliverySystem.setVisibility(View.INVISIBLE);
                }
            }else {
                linearLayout.setVisibility(View.INVISIBLE);
                productRating.setVisibility(View.INVISIBLE);
                productTotalRating.setVisibility(View.INVISIBLE);
                productPrice.setText("Out of Stock");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                productCuttedPrice.setVisibility(View.INVISIBLE);
                productDeliverySystem.setVisibility(View.INVISIBLE);
            }

            if (wishList){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_query_list) {
                        ProductDetailsActivity.running_query_list = true;
                        AllDBQuery.removeWishlist(index, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent wishList = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    wishList.putExtra("PRODUCT_ID",pId);
                    itemView.getContext().startActivity(wishList);
                }
            });

        }
    }
}
