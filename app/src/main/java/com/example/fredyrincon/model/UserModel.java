package com.example.fredyrincon.model;

/**
 * Created by fredyrincon on 4/10/2014.
 */
public class UserModel {

    public String uuid;
    public String email;
    public String full_name;
    public double available_balance;
    public double unconfirmed_balance;
    public String confirmed;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public double getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(double available_balance) {
        this.available_balance = available_balance;
    }

    public double getUnconfirmed_balance() {
        return unconfirmed_balance;
    }

    public void setUnconfirmed_balance(double unconfirmed_balance) {
        this.unconfirmed_balance = unconfirmed_balance;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }


}
