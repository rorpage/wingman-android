package com.rorpage.wingman.modules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DateTimeModule extends BaseWingmanModule {
    public DateTimeModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected ArrayList<WingmanMessage> getMessages() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm aa\nMM/dd/yyyy", Locale.US);

        ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();
        wingmanMessages.add(new WingmanMessage(getMessageType(), simpleDateFormat.format(new Date()),
                getMessagePriority()));

        return wingmanMessages;
    }

    @Override
    public ArrayList<WingmanMessage> getCurrentData() {
        return getMessages();
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.CLOCK;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.LOW;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
