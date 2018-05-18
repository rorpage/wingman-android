package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

public class ScheduleGameStatus {
    @SerializedName("abstractGameState")
    public String AbstractGameState;
    @SerializedName("codedGameState")
    public String CodedGameState;
    @SerializedName("detailedState")
    public String DetailedState;
    @SerializedName("statusCode")
    public String StatusCode;
    @SerializedName("startTimeTBD")
    public boolean StartTimeTBD;
}
