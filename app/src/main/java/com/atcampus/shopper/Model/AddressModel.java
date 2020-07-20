package com.atcampus.shopper.Model;

public class AddressModel {

    private String name;
    private String address;
    private String pincode;

    public AddressModel(String name, String address, String pincode) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
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
}
