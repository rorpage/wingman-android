package com.rorpage.wingman.models.sports.mlb;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("games")
    public Games Games;

    public Data() {
        Games = new Games();
    }
}
