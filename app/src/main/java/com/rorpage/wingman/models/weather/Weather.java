package com.rorpage.wingman.models.weather;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Weather {
    @SerializedName("temperature")
    public double TempFahrenheit;
    @SerializedName("summary")
    public String Condition;

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s\n%.0f", this.Condition, this.TempFahrenheit);
    }
}
