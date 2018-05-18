package com.rorpage.wingman.models.sports.mlb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Games {
    @SerializedName("game")
    public ArrayList<Game> Game;

    public Games() {
        Game = new ArrayList<>();
    }
}
