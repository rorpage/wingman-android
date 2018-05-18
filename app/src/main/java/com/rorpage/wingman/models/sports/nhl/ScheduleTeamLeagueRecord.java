package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class ScheduleTeamLeagueRecord {
    @SerializedName("wins")
    public int Wins;
    @SerializedName("losses")
    public int Losses;
    @SerializedName("type")
    public String Type;
}
