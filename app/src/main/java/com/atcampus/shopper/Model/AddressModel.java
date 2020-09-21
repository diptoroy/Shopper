package com.atcampus.shopper.Model;

public class AddressModel {

    private String name;
    private String address;
    private String mobileNo;
    private String pincode;
    private Boolean isSelected;

    public AddressModel(String name, String address, String pincode, Boolean isSelected,String mobileNo) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.isSelected = isSelected;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
