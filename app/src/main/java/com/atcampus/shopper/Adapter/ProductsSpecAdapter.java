package com.atcampus.shopper.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    @Override
    public int getItemViewType(int position) {
        switch (productsSpeceficationModels.get(position).getType()) {
            case 0:
                return ProductsSpeceficationModel.SPECIFICATION_TITLE;
            case 1:
                return ProductsSpeceficationModel.SPECIFICATION_BODY;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public ProductsSpecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ProductsSpeceficationModel.SPECIFICATION_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDP(16, parent.getContext()), setDP(16, parent.getContext()), setDP(16, parent.getContext()), setDP(8, parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new ViewHolder(title);
            case ProductsSpeceficationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_spec_item_row, parent, false);
                return new ViewHolder(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ProductsSpecAdapter.ViewHolder holder, int position) {
        switch (productsSpeceficationModels.get(position).getType()) {
            case ProductsSpeceficationModel.SPECIFICATION_TITLE:
                holder.setTitle(productsSpeceficationModels.get(position).getTitle());
                break;
            case ProductsSpeceficationModel.SPECIFICATION_BODY:
                String specName = productsSpeceficationModels.get(position).getProductSpecName();
                String specValue = productsSpeceficationModels.get(position).getProductSpecValue();
                holder.setSpec(specName, specValue);
                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return productsSpeceficationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productsSpec, productsSpecValue;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private void setTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);
        }

        private void setSpec(String productsSpecName, String productsSpecDValue) {
            productsSpec = itemView.findViewById(R.id.products_feature_name);
            productsSpecValue = itemView.findViewById(R.id.products_feature_value);
            productsSpec.setText(productsSpecName);
            productsSpecValue.setText(productsSpecDValue);
        }
    }

    private int setDP(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
