package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class NhlTeam {
    @SerializedName("name")
    public String Name;
    @SerializedName("id")
    public int Id;
    @SerializedName("abbreviation")
    public String Abbreviation;
}
