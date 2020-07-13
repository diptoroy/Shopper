package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.WishlistModel;
import com.atcampus.shopper.R;

import org.w3c.dom.Text;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModels;

    public WishlistAdapter(List<WishlistModel> wishlistModels) {
        this.wishlistModels = wishlistModels;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {

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
        private ImageButton deleteBtn;

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

        private void setData(){
            
        }
    }
}
