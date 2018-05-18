package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Date {
    @SerializedName("date")
    public String Date;
    @SerializedName("totalItems")
    public int TotalItems;
    @SerializedName("totalEvents")
    public int TotalEvents;
    @SerializedName("totalGames")
    public int TotalGames;
    @SerializedName("totalMatches")
    public int TotalMatches;
    @SerializedName("games")
    public ArrayList<Game> Games;

    public Date() {
        Games = new ArrayList<>();
    }
}
