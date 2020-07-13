package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Activity.OrderDetailsActivity;
import com.atcampus.shopper.Model.OrderItemModel;
import com.atcampus.shopper.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderItemModel> orderItemModels;

    public OrderAdapter(List<OrderItemModel> orderItemModels) {
        this.orderItemModels = orderItemModels;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        int img = orderItemModels.get(position).getProductImage();
        int rating = orderItemModels.get(position).getRating();
        String title = orderItemModels.get(position).getOrderProductTitle();
        String date = orderItemModels.get(position).getOrderDeliveryDate();

        holder.setProductData(img,title,date,rating);

    }

    @Override
    public int getItemCount() {
        return orderItemModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage,deliveryIndicator;
        private TextView productTitle,productDeliveryDate;
        private LinearLayout userratingContainer;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.order_product_image);
            productTitle = itemView.findViewById(R.id.order_product_title);
            productDeliveryDate = itemView.findViewById(R.id.order_delivery_date);
            deliveryIndicator = itemView.findViewById(R.id.order_indicator);
            userratingContainer =itemView.findViewById(R.id.user_rating_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });
        }

        private void setProductData(int img,String title,String date,int rating){
            productImage.setImageResource(img);
            productTitle.setText(title);
            if (date.equals("cancelled")) {
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorAccent)));
            }else{
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }
            productDeliveryDate.setText(date);

            //rating
            setRating(rating);
            for (int x = 0; x < userratingContainer.getChildCount(); x++){
                final int starPosition = x;
                userratingContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
        }
        private void setRating(int starPosition){
            for (int x = 0; x < userratingContainer.getChildCount(); x++){
                ImageView starBtn = (ImageView) userratingContainer.getChildAt(x);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F4BCBC")));
                if (x <= starPosition){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFBF00")));
                }
            }
        }
    }
}
