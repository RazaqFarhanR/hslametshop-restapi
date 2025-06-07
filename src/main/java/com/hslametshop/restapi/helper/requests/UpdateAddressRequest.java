package com.hslametshop.restapi.helper.requests;

public class UpdateAddressRequest {
    private String address;

    public UpdateAddressRequest() {
    }

    public UpdateAddressRequest(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
