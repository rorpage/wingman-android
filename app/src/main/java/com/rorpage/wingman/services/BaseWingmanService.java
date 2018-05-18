package com.rorpage.wingman.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import timber.log.Timber;

public abstract class BaseWingmanService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Timber.d("onStartCommand()");
        return ServiceStartCommandType();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected int ServiceStartCommandType() {
        return START_STICKY;
    }
}
