package com.rorpage.wingman.services.updates;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rorpage.wingman.models.sports.nhl.Game;
import com.rorpage.wingman.models.sports.nhl.Schedule;
import com.rorpage.wingman.models.sports.nhl.Teams;
import com.rorpage.wingman.models.sports.nhl.TeamsTeam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import timber.log.Timber;

import static com.rorpage.wingman.modules.updatables.sports.NhlModule.PREFERENCE_KEY_MODULEDATA_NHLMODULE;

public class NhlUpdateService extends BaseUpdateService {
    @Override
    public void onCreate() {
        super.onCreate();

        final String mTeamsUri = "https://statsapi.web.nhl.com/api/v1/teams";
        Ion.with(NhlUpdateService.this)
                .load(mTeamsUri)
                .asJsonObject()
                .setCallback(getTeamsCallback());
    }

    private FutureCallback<JsonObject> getTeamsCallback() {
        final String scheduleUri = "https://statsapi.web.nhl.com/api/v1/schedule";

        return new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Timber.d("onCompleted() - Teams");
                if (e != null) {
                    Timber.e(e);
                } else {
                    final Teams teams = mGson.fromJson(result, Teams.class);

                    Ion.with(NhlUpdateService.this)
                            .load(scheduleUri)
                            .asJsonObject()
                            .setCallback(getScheduleCallback(teams));
                }
            }
        };
    }

    private FutureCallback<JsonObject> getScheduleCallback(final Teams teams) {
        return new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Timber.d("onCompleted() - Schedule");
                if (e != null) {
                    Timber.e(e);
                } else {
                    String gameOutput = "No games today";

                    final Schedule schedule = mGson.fromJson(result, Schedule.class);

                    if (schedule.Dates.size() > 0) {
                        final ArrayList<Game> games = schedule.Dates.get(0).Games;

                        if (games.size() > 0) {
                            final Game gameToWatch = games.get(0);

                            String awayTeamAbbreviation = "";
                            String homeTeamAbbreviation = "";
                            for (TeamsTeam team : teams.Teams) {
                                if (team.Id == gameToWatch.ScheduleTeam.AwayTeam.Team.Id) {
                                    awayTeamAbbreviation = team.Abbreviation;
                                }

                                if (team.Id == gameToWatch.ScheduleTeam.HomeTeam.Team.Id) {
                                    homeTeamAbbreviation = team.Abbreviation;
                                }
                            }

                            if (gameToWatch.ScheduleGameStatus
                                    .DetailedState
                                    .toLowerCase().equals("scheduled")) {
                                gameOutput = getScheduledGameText(gameToWatch, awayTeamAbbreviation,
                                        homeTeamAbbreviation);
                            } else {
                                gameOutput = getGameText(gameToWatch, awayTeamAbbreviation,
                                        homeTeamAbbreviation);
                            }
                        }
                    }

                    mSharedPreferences.edit()
                            .putString(PREFERENCE_KEY_MODULEDATA_NHLMODULE, gameOutput)
                            .apply();

                    stopSelf();
                }
            }
        };
    }

    private String getScheduledGameText(final Game gameToWatch,
                                        final String awayTeamAbbreviation,
                                        final String homeTeamAbbreviation) {
        try {
            SimpleDateFormat gameDateParseFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            gameDateParseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            final Date gameDate = gameDateParseFormat.parse(gameToWatch.GameDate);
            SimpleDateFormat gameDateOutputFormat =
                    new SimpleDateFormat("h:mm aa", Locale.US);
            gameDateOutputFormat.setTimeZone(TimeZone.getDefault());

            return String.format(
                    Locale.US,
                    "%s @ %s\nTime: %s",
                    awayTeamAbbreviation,
                    homeTeamAbbreviation,
                    gameDateOutputFormat.format(gameDate));
        } catch (ParseException parseException) {
            Timber.e(parseException);
            return null;
        }
    }

    private String getGameText(final Game gameToWatch,
                               final String awayTeamAbbreviation,
                               final String homeTeamAbbreviation) {
        return String.format(
                Locale.US,
                "%s\n%s %s | %s %s",
                gameToWatch.ScheduleGameStatus.DetailedState,
                awayTeamAbbreviation,
                gameToWatch.ScheduleTeam.AwayTeam.Score,
                homeTeamAbbreviation,
                gameToWatch.ScheduleTeam.HomeTeam.Score);
    }
}
