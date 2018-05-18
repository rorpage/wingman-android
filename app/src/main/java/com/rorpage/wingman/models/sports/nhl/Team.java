package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class Team {
    @SerializedName("leagueRecord")
    public ScheduleTeamLeagueRecord LeagueRecord;
    @SerializedName("team")
    public ScheduleTeamTeam Team;
    @SerializedName("score")
    public int Score;
}
