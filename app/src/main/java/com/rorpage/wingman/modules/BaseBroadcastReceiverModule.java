package com.rorpage.wingman.modules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;

import java.util.ArrayList;

import timber.log.Timber;

public abstract class BaseBroadcastReceiverModule extends BaseWingmanModule {
    protected BaseBroadcastReceiverModule() {
    }

    protected BaseBroadcastReceiverModule(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPreferences = sharedPreferences;

        registerReceiver();
    }

    protected abstract void registerReceiver();
    protected abstract void handleReceivedBroadcast(Context context, Intent intent);
    public void unregisterReceiver() {
        try {
            mContext.unregisterReceiver(this);
        } catch (IllegalArgumentException e) {
            Timber.e(e);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        handleReceivedBroadcast(context, intent);
    }

    @Override
    public ArrayList<WingmanMessage> getCurrentData() {
        return getMessages();
    }
}
