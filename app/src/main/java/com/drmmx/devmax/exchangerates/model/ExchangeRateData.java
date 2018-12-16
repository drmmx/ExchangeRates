
package com.drmmx.devmax.exchangerates.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ExchangeRateData {

    @SerializedName("date")
    private String date;
    @SerializedName("bank")
    private String bank;
    @SerializedName("baseCurrency")
    private Integer baseCurrency;
    @SerializedName("baseCurrencyLit")
    private String baseCurrencyLit;
    @SerializedName("exchangeRate")
    private List<ExchangeRate> exchangeRate = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Integer baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
