package com.rorpage.wingman.models.weather.darksky;

import com.google.gson.annotations.SerializedName;

public class Currently {
    @SerializedName("summary")
    public String Summary;
    @SerializedName("icon")
    public String Icon;
    @SerializedName("nearestStormDistance")
    public double NearestStormDistance;
    @SerializedName("nearestStormBearing")
    public double NearestStormBearing;
    @SerializedName("precipIntensity")
    public double PrecipIntensity;
    @SerializedName("precipProbability")
    public double PrecipProbability;
    @SerializedName("temperature")
    public double Temperature;
    @SerializedName("apparentTemperature")
    public double ApparentTemperature;
    @SerializedName("dewPoint")
    public double DewPoint;
    @SerializedName("humidity")
    public double Humidity;
    @SerializedName("pressure")
    public double Pressure;
    @SerializedName("windSpeed")
    public double WindSpeed;
    @SerializedName("windGust")
    public double WindGust;
    @SerializedName("windBearing")
    public double WindBearing;
    @SerializedName("cloudCover")
    public double CloudCover;
    @SerializedName("uvIndex")
    public double UvIndex;
    @SerializedName("visibility")
    public double Visibility;
    @SerializedName("ozone")
    public double Ozone;
}
