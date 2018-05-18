package com.rorpage.wingman.modules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;

import java.util.ArrayList;

public abstract class BaseUpdatableModule extends BaseWingmanModule {
    protected abstract boolean isDataStale();
    protected abstract Intent getUpdateServiceIntent();

    protected BaseUpdatableModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    public ArrayList<WingmanMessage> getCurrentData() {
        if (isDataStale()) {
            Intent updateServiceIntent = getUpdateServiceIntent();
            mContext.startService(updateServiceIntent);

            ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();
            wingmanMessages.add(new WingmanMessage(getMessageType(), "Refreshing\ndata...", getMessagePriority()));
            return wingmanMessages;
        }

        return getMessages();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
