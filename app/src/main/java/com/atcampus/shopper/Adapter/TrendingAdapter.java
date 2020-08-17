package com.atcampus.shopper.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atcampus.shopper.Activity.ProductDetailsActivity;
import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class TrendingAdapter extends BaseAdapter {

    List<DealsModel> trendingList;

    public TrendingAdapter(List<DealsModel> trendingList) {
        this.trendingList = trendingList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final View view;

        if (convertView == null){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_row,null);
            view.setElevation(0);
//            view.setBackgroundColor(Color.parseColor("#fffffff"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetails = new Intent(parent.getContext(), ProductDetailsActivity.class);
                    parent.getContext().startActivity(productDetails);
                }
            });

            ImageView trendingImage = view.findViewById(R.id.dealsitem_image);
            TextView trendingName = view.findViewById(R.id.dealsitem_name);
            TextView trendingSpec = view.findViewById(R.id.dealsitem_spec);
            TextView trendingPrice = view.findViewById(R.id.dealsitem_price);

            Glide.with(parent.getContext()).load(trendingList.get(position).getDealsImage()).apply(new RequestOptions().placeholder(R.drawable.phone)).into(trendingImage);
            trendingName.setText(trendingList.get(position).getDealsName());
            trendingSpec.setText(trendingList.get(position).getDealsSpec());
            trendingPrice.setText("$"+trendingList.get(position).getDealsPrice()+"");
        }else{
            view = convertView;
        }
        return view;
    }
}
