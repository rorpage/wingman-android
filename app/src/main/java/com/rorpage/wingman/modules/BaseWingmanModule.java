package com.rorpage.wingman.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;

import java.util.ArrayList;

public abstract class BaseWingmanModule extends BroadcastReceiver {
    protected Context mContext;
    protected SharedPreferences mSharedPreferences;

    BaseWingmanModule() {
    }

    protected BaseWingmanModule(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPreferences = sharedPreferences;
    }

    public abstract ArrayList<WingmanMessage> getCurrentData();
    protected abstract ArrayList<WingmanMessage> getMessages();
    protected abstract MessageType getMessageType();
    protected abstract MessagePriority getMessagePriority();
}
