package com.rorpage.wingman.models.stocks;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Stock {
    @SerializedName("result")
    public Result Result;

    public Stock () {
        Result = new Result();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s $%.2f\n$%s (%s%%)",
                Result.Quote.Symbol,
                Result.Quote.Price,
                Result.Quote.PriceChange,
                Result.Quote.PercentChange);
    }
}
