package com.example.fredyrincon.model;

/**
 * Created by fredyrincon on 4/10/2014.
 */
public class AddressModel {

    public String address;
    public String label;
    public double total_received;
    public double total_confirmed;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getTotal_received() {
        return total_received;
    }

    public void setTotal_received(double total_received) {
        this.total_received = total_received;
    }

    public double getTotal_confirmed() {
        return total_confirmed;
    }

    public void setTotal_confirmed(double total_confirmed) {
        this.total_confirmed = total_confirmed;
    }

}
