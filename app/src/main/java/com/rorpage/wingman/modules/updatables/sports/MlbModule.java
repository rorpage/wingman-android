package com.rorpage.wingman.modules.updatables.sports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.modules.BaseUpdatableModule;
import com.rorpage.wingman.services.updates.MlbUpdateService;
import com.rorpage.wingman.util.Constants;

import java.util.ArrayList;

public class MlbModule extends BaseUpdatableModule {
    public static final String PREFERENCE_KEY_MODULEDATA_MLBMODULE = "DATA_MlbModule";
    public static final String PREFERENCE_KEY_LASTUPDATEDTIME_MLBMODULE = "LASTUPDATEDTIME_MlbModule";

    public MlbModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    protected boolean isDataStale() {
        final long currentTime = System.currentTimeMillis();
        final long lastUpdatedTime = mSharedPreferences.getLong(PREFERENCE_KEY_LASTUPDATEDTIME_MLBMODULE, -1);

        if (lastUpdatedTime == -1 || lastUpdatedTime < currentTime - Constants.FIFTEEN_MINUTES) {
            mSharedPreferences.edit()
                    .putLong(PREFERENCE_KEY_LASTUPDATEDTIME_MLBMODULE, currentTime)
                    .apply();
            return true;
        }

        return false;
    }

    @Override
    protected Intent getUpdateServiceIntent() {
        return new Intent(mContext, MlbUpdateService.class);
    }

    @Override
    protected ArrayList<WingmanMessage> getMessages() {
        ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();

        wingmanMessages.add(new WingmanMessage(
                getMessageType(),
                mSharedPreferences.getString(PREFERENCE_KEY_MODULEDATA_MLBMODULE, "None"),
                getMessagePriority()
        ));

        return wingmanMessages;
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.SPORTS;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.LOW;
    }
}
