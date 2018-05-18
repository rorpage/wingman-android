package com.rorpage.wingman.services.updates;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rorpage.wingman.services.BaseWingmanService;

public abstract class BaseUpdateService extends BaseWingmanService {
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected int ServiceStartCommandType() {
        return START_NOT_STICKY;
    }
}
