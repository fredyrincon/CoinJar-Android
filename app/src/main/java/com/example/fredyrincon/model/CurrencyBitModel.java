package com.example.fredyrincon.model;

/**
 * Created by fredyrincon on 4/10/2014.
 */
public class CurrencyBitModel {

    private String currencyId;
    private double bid;
    private double ask;
    private double spot;

    public String getCurrencyId() {return currencyId;}

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getSpot() {
        return spot;
    }

    public void setSpot(double spot) {
        this.spot = spot;
    }
}
