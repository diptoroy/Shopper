package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.RewardModel;
import com.atcampus.shopper.R;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModels;

    public RewardAdapter(List<RewardModel> rewardModels) {
        this.rewardModels = rewardModels;
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.ViewHolder holder, int position) {

        String title = rewardModels.get(position).getRewardTitle();
        String date = rewardModels.get(position).getRewardDate();
        String desc = rewardModels.get(position).getRewardDesc();

        holder.setData(title,date,desc);
    }

    @Override
    public int getItemCount() {
        return rewardModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,date,desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.reward_title);
            date = itemView.findViewById(R.id.reward_date);
            desc = itemView.findViewById(R.id.reward_details);
        }

        private void setData(String rTitle,String rDate,String rDesc){
            title.setText(rTitle);
            date.setText(rDate);
            desc.setText(rDesc);
        }
    }
}
