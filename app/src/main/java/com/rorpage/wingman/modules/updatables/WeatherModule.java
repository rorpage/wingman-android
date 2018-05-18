package com.rorpage.wingman.modules.updatables;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.modules.BaseUpdatableModule;
import com.rorpage.wingman.services.updates.WeatherUpdateService;
import com.rorpage.wingman.util.Constants;

import java.util.ArrayList;

public class WeatherModule extends BaseUpdatableModule {
    public static final String PREFERENCE_KEY_MODULEDATA_WEATHERMODULE = "DATA_WeatherModule";
    public static final String PREFERENCE_KEY_LASTUPDATEDTIME_WEATHERMODULE = "LASTUPDATEDTIME_WeatherModule";

    public WeatherModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    protected boolean isDataStale() {
        final long currentTime = System.currentTimeMillis();
        final long lastUpdatedTime = mSharedPreferences.getLong(PREFERENCE_KEY_LASTUPDATEDTIME_WEATHERMODULE, -1);

        if (lastUpdatedTime == -1 || lastUpdatedTime < currentTime - Constants.THIRTY_MINUTES) {
            mSharedPreferences.edit()
                    .putLong(PREFERENCE_KEY_LASTUPDATEDTIME_WEATHERMODULE, currentTime)
                    .apply();
            return true;
        }

        return false;
    }

    @Override
    protected Intent getUpdateServiceIntent() {
        return new Intent(mContext, WeatherUpdateService.class);
    }

    @Override
    protected ArrayList<WingmanMessage> getMessages() {
        ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();
        wingmanMessages.add(new WingmanMessage(getMessageType(),
                mSharedPreferences.getString(PREFERENCE_KEY_MODULEDATA_WEATHERMODULE, "None"),
                getMessagePriority()));
        return wingmanMessages;
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.WEATHER;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.LOW;
    }
}
