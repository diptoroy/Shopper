package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;

import org.w3c.dom.Text;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModels;
    private Boolean wishList;

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

        int resource = wishlistModels.get(position).getProductImage();
        String title = wishlistModels.get(position).getProductName();
        int freeCueponNo = wishlistModels.get(position).getProductCuepon();
        String rating = wishlistModels.get(position).getProductRating();
        int totalRating = wishlistModels.get(position).getProductTotalRating();
        String price = wishlistModels.get(position).getProductPrice();
        String cuttedPrice = wishlistModels.get(position).getProductCuttedPrice();
        String deliSystem = wishlistModels.get(position).getProductDeliverySystem();

        holder.setData(resource,title,freeCueponNo,rating,totalRating,price,cuttedPrice,deliSystem);

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

        private void setData(int resource, String title, int freeCueponNo,String rating,int totalRating,String price,String cuttedPrice,String deliSystem) {
            productImage.setImageResource(resource);
            productName.setText(title);
            if (freeCueponNo != 0) {
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
            productRating.setText(rating);
            productTotalRating.setText(totalRating +"(rating)");
            productPrice.setText(price);
            productCuttedPrice.setText(cuttedPrice);
            productDeliverySystem.setText(deliSystem);

            if (wishList){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Delete Item",Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent wishList = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(wishList);
                }
            });

        }
    }
}
