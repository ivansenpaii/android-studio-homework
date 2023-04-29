package com.example.converterjava;

import com.google.gson.JsonObject;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class CurrencyAPI {

    public static final String API_KEY = "877e0a7d2ac74399b95d3ba6957f70fc";
    private static Retrofit retrofit = null;

    public interface CurrencyService {
        @GET("latest.json")
        Call<Currency> getExchangeRates(@Query("app_id") String app_id);
    }

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://openexchangerates.org/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

