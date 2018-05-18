package com.rorpage.wingman.models.sports.nhl;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Schedule {
    @SerializedName("copyright")
    public String Copyright;
    @SerializedName("dates")
    public ArrayList<Date> Dates;

    public Schedule() {
        Dates = new ArrayList<>();
    }
}
