package com.rorpage.wingman.services.updates;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.rorpage.wingman.services.BaseWingmanService;

public abstract class BaseUpdateService extends BaseWingmanService {
    protected Gson mGson;
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mGson = new Gson();
    }

    @Override
    protected int ServiceStartCommandType() {
        return START_NOT_STICKY;
    }
}
