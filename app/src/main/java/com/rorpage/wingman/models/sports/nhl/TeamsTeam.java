package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class TeamsTeam {
    @SerializedName("id")
    public int Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("link")
    public String Link;
    @SerializedName("abbreviation")
    public String Abbreviation;
    @SerializedName("teamName")
    public String TeamName;
    @SerializedName("locationName")
    public String LocationName;
    @SerializedName("firstYearOfPlay")
    public String FirstYearOfPlay;
    @SerializedName("shortName")
    public String ShortName;
    @SerializedName("officialSiteUrl")
    public String OfficialSiteUrl;
    @SerializedName("franchiseId")
    public int FranchiseId;
    @SerializedName("active")
    public boolean Active;
}
