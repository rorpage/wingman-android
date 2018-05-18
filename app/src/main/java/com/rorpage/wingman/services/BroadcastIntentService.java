package com.rorpage.wingman.services;

import android.content.Context;
import android.content.Intent;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.util.Constants;

import timber.log.Timber;

public class BroadcastIntentService {
    private Context mContext;

    public BroadcastIntentService(Context context) {
        this.mContext = context;
    }

    public void broadcastNewNotification(WingmanMessage wingmanMessage) {
        broadcastIntent(Constants.NEW_NOTIFICATION_INTENT, wingmanMessage);
    }

    public void broadcastNewSms(WingmanMessage wingmanMessage) {
        broadcastIntent(Constants.NEW_SMS_INTENT, wingmanMessage);
    }

    public void broadcastBluetoothConnected() {
        broadcastBluetoothStatus(Constants.BLUETOOTH_CONNECTED_INTENT);
    }

    public void broadcastBluetoothDisconnected() {
        broadcastBluetoothStatus(Constants.BLUETOOTH_DISCONNECTED_INTENT);
    }

    private void broadcastIntent(String action, WingmanMessage wingmanMessage) {
        final Intent intent = createIntentFromMessge(action, wingmanMessage);
        Timber.d("broadcastIntent()");
        mContext.sendBroadcast(intent);
    }

    public static Intent createIntentFromMessge(String action, WingmanMessage wingmanMessage) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_TYPE, wingmanMessage.Type);
        intent.putExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_MESSAGE, wingmanMessage.Message);
        intent.putExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_PRIORITY, wingmanMessage.Priority);
        return intent;
    }

    public static WingmanMessage getMessageFromIntent(Intent intent) {
        final MessageType messageType =
                (MessageType) intent.getSerializableExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_TYPE);
        final String message =
                intent.getStringExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_MESSAGE);
        final MessagePriority messagePriority =
                (MessagePriority) intent.getSerializableExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE_PRIORITY);

        return new WingmanMessage(messageType, message, messagePriority);
    }

//    private void broadcastIntent(final String action, final WingmanMessage message) {
//        final String wingmanMessage = mGson.toJson(message);
//        Timber.d("wingmanMessage: %s", wingmanMessage);
//
//        long currentTime = System.currentTimeMillis();
//        Timber.d("%d", currentTime - mTimeOfLastBroadcast);
//        if (currentTime - mTimeOfLastBroadcast <= 7000) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    broadcastIntent(action, message);
//                }
//            }, 5000);
//        } else {
//            Intent intent = new Intent();
//            intent.setAction(action);
//            intent.putExtra(Constants.INTENT_EXTRA_WINGMANMESSAGE, message);
//            mContext.sendBroadcast(intent);
//            mTimeOfLastBroadcast = currentTime;
//        }
//    }

    private void broadcastBluetoothStatus(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        mContext.sendBroadcast(intent);
    }
}
