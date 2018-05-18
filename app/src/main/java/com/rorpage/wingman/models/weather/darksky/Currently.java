package com.rorpage.wingman.models.weather.darksky;

import com.google.gson.annotations.SerializedName;

public class Currently {
    @SerializedName("temperature")
    public double Temperature;
    @SerializedName("summary")
    public String Summary;
}
