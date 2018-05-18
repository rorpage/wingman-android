package com.rorpage.wingman.models.stocks;

import com.google.gson.annotations.SerializedName;

public class Quote {
    @SerializedName("symbol")
    public String Symbol;
    @SerializedName("price")
    public double Price;
    @SerializedName("priceChange")
    public String PriceChange;
    @SerializedName("percentChange")
    public String PercentChange;
}
