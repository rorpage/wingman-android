package com.rorpage.wingman.modules.receivers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.models.messages.SmsWingmanMessage;
import com.rorpage.wingman.modules.BaseBroadcastReceiverModule;
import com.rorpage.wingman.services.BroadcastIntentService;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

public class SmsModule extends BaseBroadcastReceiverModule {
    public SmsModule() {
        super();
    }

    @Override
    protected ArrayList<WingmanMessage> getMessages() {
        return null;
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.MESSAGE;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.HIGH;
    }

    @Override
    protected void registerReceiver() {
    }

    @Override
    protected void handleReceivedBroadcast(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                    StringBuilder messageBody = new StringBuilder();
                    for (SmsMessage message : messages) {
                        messageBody.append(message.getMessageBody());
                    }

                    final String originatingAddress = messages[0].getOriginatingAddress();
                    final Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                            Uri.encode(originatingAddress));
                    Cursor contactCursor = (context.getContentResolver())
                            .query(uri, new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME},
                                    null, null, null);

                    String messageFromDisplay = originatingAddress;
                    if (contactCursor.moveToFirst()) {
                        final int displayNameIndex = contactCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        messageFromDisplay = contactCursor.getString(displayNameIndex);
                    }

                    SmsWingmanMessage message = new SmsWingmanMessage(messageFromDisplay, messageBody.toString());
                    (new BroadcastIntentService(context)).broadcastNewSms(message);
                } catch(Exception e) {
                    Timber.e(e);
                }
            }
        }
    }
}
