package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Activity.CategoryActivity;
import com.atcampus.shopper.Model.CategoryModel;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    int lastPosition = -1;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String image = categoryModelList.get(position).getCategoryImage();
        String name = categoryModelList.get(position).getCategory_Name();
        holder.setCategoryName(name,position);
        holder.setCategoryImage(image);

        if (lastPosition < position){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);

        }
        private void setCategoryImage(String cImageUrl){
            if (!cImageUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(cImageUrl).apply(new RequestOptions().placeholder(R.drawable.photo)).into(categoryImage);
            }else {
                categoryImage.setImageResource(R.drawable.all);
            }
        }
        private void setCategoryName(final String name,final int position){
            categoryName.setText(name);

            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent intentCategory = new Intent(itemView.getContext(), CategoryActivity.class);
                            intentCategory.putExtra("CategoryName", name);
                            itemView.getContext().startActivity(intentCategory);
                        }
                    }
                });
            }
        }
    }
}
