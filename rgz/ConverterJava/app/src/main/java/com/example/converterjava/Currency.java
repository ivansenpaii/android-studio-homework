package com.example.converterjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Currency {

    @SerializedName("rates")
    @Expose
    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }
}
