package com.rorpage.wingman.modules.updatables;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.modules.BaseUpdatableModule;
import com.rorpage.wingman.services.updates.StockUpdateService;
import com.rorpage.wingman.util.Constants;

import java.util.ArrayList;
import java.util.Set;

public class StockModule extends BaseUpdatableModule {
    public static final String PREFERENCE_KEY_MODULEDATA_STOCKMODULE = "DATA_StockModule";
    public static final String PREFERENCE_KEY_LASTUPDATEDTIME_STOCKMODULE = "LASTUPDATEDTIME_StockModule";

    public StockModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    protected boolean isDataStale() {
        final long currentTime = System.currentTimeMillis();
        final long lastUpdatedTime = mSharedPreferences.getLong(PREFERENCE_KEY_LASTUPDATEDTIME_STOCKMODULE, -1);

        if (lastUpdatedTime == -1 || lastUpdatedTime < currentTime - Constants.THIRTY_MINUTES) {
            mSharedPreferences.edit()
                    .putLong(PREFERENCE_KEY_LASTUPDATEDTIME_STOCKMODULE, currentTime)
                    .apply();
            return true;
        }

        return false;
    }

    @Override
    protected Intent getUpdateServiceIntent() {
        return new Intent(mContext, StockUpdateService.class);
    }

    @Override
    protected ArrayList<WingmanMessage> getMessages() {
        ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();

        Set<String> strings = mSharedPreferences.getStringSet(PREFERENCE_KEY_MODULEDATA_STOCKMODULE, null);

        if (strings == null) {
            wingmanMessages.add(new WingmanMessage(getMessageType(), "No stocks\nat this time", getMessagePriority()));
            return wingmanMessages;
        }

        for (String stock : strings) {
            wingmanMessages.add(new WingmanMessage(getMessageType(), stock, getMessagePriority()));
        }

        return wingmanMessages;
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.STOCK;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.LOW;
    }
}
