package com.atcampus.shopper.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.MultipleRecyclerviewModel;

import java.util.List;

public class MultipleRecyclerviewAdapter extends RecyclerView.Adapter {

    private List<MultipleRecyclerviewModel> multipleRecyclerviewModels;

    public MultipleRecyclerviewAdapter(List<MultipleRecyclerviewModel> multipleRecyclerviewModels) {
        this.multipleRecyclerviewModels = multipleRecyclerviewModels;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
