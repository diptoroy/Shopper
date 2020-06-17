package com.atcampus.shopper.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atcampus.shopper.Model.DealsModel;
import com.atcampus.shopper.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_row,null);
            view.setElevation(0);
//            view.setBackgroundColor(Color.parseColor("#fffffff"));

            ImageView trendingImage = view.findViewById(R.id.dealsitem_image);
            TextView trendingName = view.findViewById(R.id.dealsitem_name);
            TextView trendingSpec = view.findViewById(R.id.dealsitem_spec);
            TextView trendingPrice = view.findViewById(R.id.dealsitem_price);

            trendingImage.setImageResource(trendingList.get(position).getDealsImage());
            trendingName.setText(trendingList.get(position).getDealsName());
            trendingSpec.setText(trendingList.get(position).getDealsSpec());
            trendingPrice.setText(trendingList.get(position).getDealsPrice());
        }else{
            view = convertView;
        }
        return view;
    }
}