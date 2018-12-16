
package com.drmmx.devmax.exchangerates.model;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate {

    @SerializedName("baseCurrency")
    private String baseCurrency;
    @SerializedName("saleRateNB")
    private Double saleRateNB;
    @SerializedName("purchaseRateNB")
    private Double purchaseRateNB;
    @SerializedName("currency")
    private String currency;
    @SerializedName("saleRate")
    private Double saleRate;
    @SerializedName("purchaseRate")
    private Double purchaseRate;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Double getSaleRateNB() {
        return saleRateNB;
    }

    public void setSaleRateNB(Double saleRateNB) {
        this.saleRateNB = saleRateNB;
    }

    public Double getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public void setPurchaseRateNB(Double purchaseRateNB) {
        this.purchaseRateNB = purchaseRateNB;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }

    public Double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

}
