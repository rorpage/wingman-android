package com.rorpage.wingman.models.stocks;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Stock {
    @SerializedName("result")
    public Result Result;

    public Stock () {
        Result = new Result();
    }
}
