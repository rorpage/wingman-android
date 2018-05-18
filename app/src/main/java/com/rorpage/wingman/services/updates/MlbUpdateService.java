package com.rorpage.wingman.services.updates;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rorpage.wingman.models.sports.mlb.Game;
import com.rorpage.wingman.models.sports.mlb.MlbMiniScoreboard;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static com.rorpage.wingman.modules.updatables.sports.MlbModule.PREFERENCE_KEY_MODULEDATA_MLBMODULE;

public class MlbUpdateService extends BaseUpdateService {
    @Override
    public void onCreate() {
        super.onCreate();

        final Date date = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        final String uri = String.format(Locale.US,
                "http://gd2.mlb.com/components/game/mlb/year_%04d/month_%02d/day_%02d/miniscoreboard.json",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        Ion.with(MlbUpdateService.this)
                .load(uri)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Timber.d("onCompleted()");
                        if (e != null) {
                            Timber.e(e);
                        } else {
                            MlbMiniScoreboard mlbMiniScoreboard = (new Gson())
                                    .fromJson(result, MlbMiniScoreboard.class);

                            Game gameToWatch = null;
                            for (Game game : mlbMiniScoreboard.Data.Games.Game) {
                                if (game.HomeNameAbbreviation.equals("ATL") ||
                                        game.AwayNameAbbreviation.equals("ATL")) {
                                    gameToWatch = game;
                                }
                            }

                            String gameOutput = "No game today";
                            if (gameToWatch != null) {
                                gameOutput = gameToWatch.toString();
                            }

                            mSharedPreferences.edit()
                                    .putString(PREFERENCE_KEY_MODULEDATA_MLBMODULE, gameOutput)
                                    .apply();

                            stopSelf();
                        }
                    }
                });
    }
}
