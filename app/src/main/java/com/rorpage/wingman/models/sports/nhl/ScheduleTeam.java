package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class ScheduleTeam {
    @SerializedName("away")
    public Team AwayTeam;
    @SerializedName("home")
    public Team HomeTeam;
}
