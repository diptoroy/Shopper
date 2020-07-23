package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.R;

import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {

    private List<DealsModel> dealsModelList;

    public DealsAdapter(List<DealsModel> dealsModelList) {
        this.dealsModelList = dealsModelList;
    }

    @NonNull
    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsAdapter.ViewHolder holder, int position) {

        int img = dealsModelList.get(position).getDealsImage();
        String name = dealsModelList.get(position).getDealsName();
        String spec = dealsModelList.get(position).getDealsSpec();
        String price = dealsModelList.get(position).getDealsPrice();

        holder.setDealsImage(img);
        holder.setDealsName(name);
        holder.setDealsSpec(spec);
        holder.setDealsPrice(price);
    }

    @Override
    public int getItemCount() {
        if (dealsModelList.size() > 8){
            return 8;
        }else {
            return dealsModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView dealsImage;
        private TextView dealsName;
        private TextView dealsSpec;
        private TextView dealsPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            dealsImage = itemView.findViewById(R.id.dealsitem_image);
            dealsName = itemView.findViewById(R.id.dealsitem_name);
            dealsSpec = itemView.findViewById(R.id.dealsitem_spec);
            dealsPrice = itemView.findViewById(R.id.dealsitem_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetails = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetails);
                }
            });
        }

        private void setDealsImage(int img){
            dealsImage.setImageResource(img);
        }

        private void setDealsName(String name){
            dealsName.setText(name);
        }

        private void setDealsSpec(String spec){
            dealsSpec.setText(spec);
        }

        private void setDealsPrice(String price){
            dealsPrice.setText(price);
        }
    }
}
