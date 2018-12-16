package com.drmmx.devmax.exchangerates.retrofit;

import com.drmmx.devmax.exchangerates.model.ExchangeRateData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PbAPI {

    @GET("exchange_rates")
    Observable<ExchangeRateData> getExchangeRate(@Query("json") String json,
                                                 @Query("date") String date);

}
