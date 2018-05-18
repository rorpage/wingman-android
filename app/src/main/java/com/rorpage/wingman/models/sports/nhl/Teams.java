package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Teams {
    @SerializedName("copyright")
    public String Copyright;
    @SerializedName("teams")
    public ArrayList<TeamsTeam> Teams;

    public Teams() {
        Teams = new ArrayList<>();
    }
}