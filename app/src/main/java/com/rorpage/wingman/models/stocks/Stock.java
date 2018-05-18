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
        String priceChange = Result.Quote.PriceChange;
        if (priceChange.contains("-")) {
            priceChange = priceChange.replace("-", "(").concat(")");
        }

        return String.format(Locale.US, "%s $%.2f\n$%s (%s%%)",
                Result.Quote.Symbol,
                Result.Quote.Price,
                priceChange,
                Result.Quote.PercentChange);
    }
}
