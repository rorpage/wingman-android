package com.rorpage.wingman.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rorpage.wingman.modules.receivers.MusicModule;
import com.rorpage.wingman.modules.updatables.sports.MlbModule;
import com.rorpage.wingman.modules.updatables.StockModule;
import com.rorpage.wingman.modules.updatables.WeatherModule;
import com.rorpage.wingman.modules.updatables.sports.NhlModule;

import java.util.ArrayList;

public class WingmanModuleFactory {
    public static ArrayList<BaseWingmanModule> getWingmanModules(Context context) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        DateTimeModule dateTimeModule = new DateTimeModule(context, sharedPreferences);
        MusicModule musicModule = new MusicModule(context, sharedPreferences);
        WeatherModule weatherModule = new WeatherModule(context, sharedPreferences);
        StockModule stockModule = new StockModule(context, sharedPreferences);
        MlbModule mlbModule = new MlbModule(context, sharedPreferences);
        NhlModule nhlModule = new NhlModule(context, sharedPreferences);

        ArrayList<BaseWingmanModule> modulesList = new ArrayList<>();
        modulesList.add(dateTimeModule);
        modulesList.add(musicModule);
        modulesList.add(weatherModule);
        modulesList.add(stockModule);
        modulesList.add(mlbModule);
        modulesList.add(nhlModule);

        return modulesList;
    }
}
