package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.ProductsSpeceficationModel;
import com.atcampus.shopper.R;

import java.util.List;

public class ProductsSpecAdapter extends RecyclerView.Adapter<ProductsSpecAdapter.ViewHolder> {

    private List<ProductsSpeceficationModel> productsSpeceficationModels;

    public ProductsSpecAdapter(List<ProductsSpeceficationModel> productsSpeceficationModels) {
        this.productsSpeceficationModels = productsSpeceficationModels;
    }

    @NonNull
    @Override
    public ProductsSpecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_spec_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsSpecAdapter.ViewHolder holder, int position) {
        String specName = productsSpeceficationModels.get(position).getProductSpecName();
        String specValue = productsSpeceficationModels.get(position).getProductSpecValue();
        holder.setSpec(specName,specValue);
    }

    @Override
    public int getItemCount() {
        return productsSpeceficationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productsSpec,productsSpecValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productsSpec = itemView.findViewById(R.id.products_feature_name);
            productsSpecValue = itemView.findViewById(R.id.products_feature_value);
        }

        private void setSpec(String productsSpecName,String productsSpecDValue){
            productsSpec.setText(productsSpecName);
            productsSpecValue.setText(productsSpecDValue);
        }
    }
}
