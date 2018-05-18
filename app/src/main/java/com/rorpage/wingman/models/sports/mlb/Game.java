package com.rorpage.wingman.models.sports.mlb;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Game {
    @SerializedName("ampm")
    public String AmPm;

    @SerializedName("day")
    public String Day;
    @SerializedName("game_nbr")
    public String GameNumber;
    @SerializedName("gameday_link")
    public String GamedayLink;
    @SerializedName("inning")
    public String Inning;
    @SerializedName("location")
    public String Location;
    @SerializedName("status")
    public String Status;
    @SerializedName("time")
    public String Time;
    @SerializedName("venue")
    public String Venue;
    @SerializedName("venue_id")
    public String VenueId;

    @SerializedName("away_code")
    public String AwayCode;
    @SerializedName("away_file_code")
    public String AwayFileCode;
    @SerializedName("away_league_id")
    public String AwayLeagueId;
    @SerializedName("away_loss")
    public String AwayLoss;
    @SerializedName("away_name_abbrev")
    public String AwayNameAbbreviation;
    @SerializedName("away_team_city")
    public String AwayTeamCity;
    @SerializedName("away_team_id")
    public String AwayTeamId;
    @SerializedName("away_team_name")
    public String AwayTeamName;
    @SerializedName("away_team_runs")
    public String AwayTeamRuns;
    @SerializedName("away_win")
    public String AwayWin;

    @SerializedName("home_code")
    public String HomeCode;
    @SerializedName("home_file_code")
    public String HomeFileCode;
    @SerializedName("home_league_id")
    public String HomeLeagueId;
    @SerializedName("home_loss")
    public String HomeLoss;
    @SerializedName("home_name_abbrev")
    public String HomeNameAbbreviation;
    @SerializedName("home_team_city")
    public String HomeTeamCity;
    @SerializedName("home_team_id")
    public String HomeTeamId;
    @SerializedName("home_team_name")
    public String HomeTeamName;
    @SerializedName("home_team_runs")
    public String HomeTeamRuns;
    @SerializedName("home_win")
    public String HomeWin;

    public boolean isGameInProgress() {
        return Status.equals("In Progress");
    }

    public boolean isGameOver() {
        return Status.equals("Final");
    }

    @Override
    public String toString() {
        if (isGameOver()) {
            return String.format(Locale.US, "Final\n%s %s | %s %s",
                    AwayNameAbbreviation,
                    AwayTeamRuns,
                    HomeNameAbbreviation,
                    HomeTeamRuns);
        }

        if (isGameInProgress()) {
            return String.format(Locale.US, "In progress\n%s %s | %s %s",
                    AwayNameAbbreviation,
                    AwayTeamRuns,
                    HomeNameAbbreviation,
                    HomeTeamRuns);
        }

        return String.format(Locale.US, "%s @ %s\nTime: %s %s",
                AwayNameAbbreviation,
                HomeNameAbbreviation,
                Time,
                AmPm);
    }
}
