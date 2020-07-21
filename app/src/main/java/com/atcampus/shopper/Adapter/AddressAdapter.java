package com.atcampus.shopper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcampus.shopper.Model.AddressModel;
import com.atcampus.shopper.R;

import java.util.List;

import static com.atcampus.shopper.Activity.AddressesActivity.refreshItem;
import static com.atcampus.shopper.Activity.DeliveryActivity.SELECT_ADDRESS;
import static com.atcampus.shopper.Fragment.AccountFragment.MANAGE_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModels;
    private int MODE;
    private int preSelected;

    public AddressAdapter(List<AddressModel> addressModels, int MODE) {
        this.addressModels = addressModels;
        this.MODE = MODE;
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
        Boolean isSelect = addressModels.get(position).getSelected();

        holder.setData(uName,uAddress,uPincode,isSelect,position);

    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName,userAddress,userPinCode;
        private ImageView saveIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.name_text);
            userAddress = itemView.findViewById(R.id.address_text);
            userPinCode = itemView.findViewById(R.id.pincode_text);
            saveIcon = itemView.findViewById(R.id.save_icon);
        }

        public void setData(String name, String address, String pincode, Boolean isSelect, final int position){
            userName.setText(name);
            userAddress.setText(address);
            userPinCode.setText(pincode);

            if (MODE == SELECT_ADDRESS){
                saveIcon.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                if (isSelect){
                    saveIcon.setVisibility(View.VISIBLE);
                    preSelected = position;
                }else {
                    saveIcon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelected != position) {
                            addressModels.get(position).setSelected(true);
                            addressModels.get(preSelected).setSelected(false);
                            refreshItem(preSelected, position);
                            preSelected = position;
                        }
                    }
                });

            }else if (MODE == MANAGE_ADDRESS){

            }
        }
    }
}