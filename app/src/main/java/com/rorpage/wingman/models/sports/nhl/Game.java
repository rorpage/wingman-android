package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class Game {
    @SerializedName("gamePk")
    public int GamePk;
    @SerializedName("link")
    public String Link;
    @SerializedName("gameType")
    public String GameType;
    @SerializedName("gameDate")
    public String GameDate;
    @SerializedName("status")
    public ScheduleGameStatus ScheduleGameStatus;
    @SerializedName("teams")
    public ScheduleTeam ScheduleTeam;
}
