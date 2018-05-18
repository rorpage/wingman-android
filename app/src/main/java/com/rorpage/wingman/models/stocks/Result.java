package com.rorpage.wingman.models.stocks;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("quote")
    public Quote Quote;

    public Result() {
        Quote = new Quote();
    }
}
