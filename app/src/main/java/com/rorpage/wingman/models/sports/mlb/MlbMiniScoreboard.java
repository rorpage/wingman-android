package com.rorpage.wingman.models.sports.mlb;

import com.google.gson.annotations.SerializedName;

public class MlbMiniScoreboard {
    @SerializedName("subject")
    public String Subject;
    @SerializedName("copyright")
    public String Copyright;
    @SerializedName("data")
    public Data Data;

    public MlbMiniScoreboard() {
        Data = new Data();
    }
}
