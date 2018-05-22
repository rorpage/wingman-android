package com.rorpage.wingman.models.weather.darksky;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class DarkSkyWeather {
    @SerializedName("currently")
    public Currently Currently;

    public DarkSkyWeather() {
        Currently = new Currently();
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s\n%.0fF | %.2f%% | %.2f%%",
                this.Currently.Summary,
                this.Currently.Temperature,
                this.Currently.PrecipProbability * 100,
                this.Currently.Humidity * 100);
    }
}
