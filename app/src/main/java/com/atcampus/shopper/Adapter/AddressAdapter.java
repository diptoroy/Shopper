package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModels;

    public AddressAdapter(List<AddressModel> addressModels) {
        this.addressModels = addressModels;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {

        String uName = addressModels.get(position).getName();
        String uAddress = addressModels.get(position).getAddress();
        String uPincode = addressModels.get(position).getPincode();

        holder.setData(uName,uAddress,uPincode);

    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName,userAddress,userPinCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.name_text);
            userAddress = itemView.findViewById(R.id.address_text);
            userPinCode = itemView.findViewById(R.id.pincode_text);
        }

        public void setData(String name,String address,String pincode){
            userName.setText(name);
            userAddress.setText(address);
            userPinCode.setText(pincode);
        }
    }
}
